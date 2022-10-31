package love.isuper.core.network.download

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import love.isuper.core.db.DownloadInfo
import love.isuper.core.db.RoomClient
import love.isuper.core.utils.DownloadUtils
import java.io.*
import java.util.concurrent.CancellationException
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 代表一个下载任务
 * url将做为下载任务的唯一标识
 * 不要直接在外部直接创建此对象,那样就可能无法同一管理下载任务,请通过[AppDownload.request]获取此对象
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class DownloadScope(
    var url: String,
    var path: String? = null,
    private val data: Serializable? = null
) : CoroutineScope by CoroutineScope(EmptyCoroutineContext) {

    private var downloadJob: Job? = null
    private val downloadData = MutableLiveData<DownloadInfo>()

    init {
        launch(Dispatchers.Main) {
            val downloadInfoDeferred = async(Dispatchers.IO) {
                RoomClient.dataBase.downloadDao().queryByUrl(url)
            }
            var downloadInfo = downloadInfoDeferred.await()
            //数据库中并没有任务,这是一个新的下载任务
            if (downloadInfo == null)
                downloadInfo = DownloadInfo(url = url, path = path, data = data)
            //将原本正在下载中的任务恢复到暂停状态,防止意外退出出现的状态错误
            if (downloadInfo.status == DownloadInfo.LOADING)
                downloadInfo.status = DownloadInfo.PAUSE
            downloadData.value = downloadInfo
        }
    }

    /**
     * 获取[DownloadInfo]
     */
    fun downloadInfo(): DownloadInfo? {
        return downloadData.value
    }

    /**
     * 是否有下载任务观察者
     */
    fun hasObserver(): Boolean {
        return downloadData.hasObservers()
    }

    /**
     * 添加下载任务观察者
     */
    fun observer(lifecycleOwner: LifecycleOwner, observer: Observer<DownloadInfo>) {
        downloadData.observe(lifecycleOwner, observer)
    }

    /**
     * 删除下载任务观察者
     */
    fun removeObserver(lifecycleOwner: LifecycleOwner) {
        downloadData.removeObservers(lifecycleOwner)
    }

    /**
     * 开始任务的下载
     * [DownloadInfo]是在协程中进行创建的,它的创建会优先从数据库中获取,但这种操作是异步的,详情请看init代码块
     * 我们需要通过观察者观察[DownloadInfo]来得知它是否已经创建完成,只有当他创建完成且不为空(如果创建完成,它一定不为空)
     * 才可以交由[AppDownload]进行下载任务的启动
     * 任务的开始可能并不是立即的,任务会受到[AppDownload]的管理
     */
    fun start() {
        var observer: Observer<DownloadInfo>? = null
        observer = Observer { downloadInfo ->
            downloadInfo?.let {
                observer?.let { downloadData.removeObserver(it) }
                when (downloadInfo.status) {
                    DownloadInfo.PAUSE, DownloadInfo.ERROR, DownloadInfo.NONE -> {
                        change(DownloadInfo.WAITING)
                        AppDownload.launchScope(this@DownloadScope)
                    }
                }
            }
        }
        downloadData.observeForever(observer)
    }

    /**
     * 启动协程进行下载
     * 请不要尝试在外部调用此方法,那样会脱离[AppDownload]的管理
     */
    fun launch() = launch {
        try {
            download()
            change(DownloadInfo.DONE)
        } catch (e: Throwable) {
            Log.w("DownloadScope", "error:${e.message}")
            when (e) {
                !is CancellationException -> change(DownloadInfo.ERROR)
            }
        } finally {
            AppDownload.launchNext(url)
        }
    }.also { downloadJob = it }

    private suspend fun download() = withContext(context = Dispatchers.IO, block = {
        change(DownloadInfo.LOADING)
        val downloadInfo = downloadData.value
        downloadInfo ?: throw IOException("Download info is null")
        val startPosition = downloadInfo.currentLength
        //验证断点有效性
        if (startPosition < 0) throw IOException("Start position less than zero")
        //下载的文件是否已经被删除
        if (startPosition > 0 && !TextUtils.isEmpty(downloadInfo.path))
            if (!File(downloadInfo.path).exists()) throw IOException("File does not exist")
        val response = RetrofitDownload.downloadService.download(
            start = "bytes=$startPosition-",
            url = downloadInfo.url
        )
        val responseBody = response.body()
        responseBody ?: throw IOException("ResponseBody is null")
        //文件长度
        if (downloadInfo.contentLength < 0)
            downloadInfo.contentLength = responseBody.contentLength()
        //保存的文件名称
        if (TextUtils.isEmpty(downloadInfo.fileName))
            downloadInfo.fileName = DownloadUtils.getUrlFileName(downloadInfo.url)
        //创建File,如果已经指定文件path,将会使用指定的path,如果没有指定将会使用默认的下载目录
        val file: File
        if (TextUtils.isEmpty(downloadInfo.path)) {
            file = File(AppDownload.downloadFolder, downloadInfo.fileName)
            downloadInfo.path = file.absolutePath
        } else file = File(downloadInfo.path)
        //再次验证下载的文件是否已经被删除
        if (startPosition > 0 && !file.exists())
            throw IOException("File does not exist")
        //再次验证断点有效性
        if (startPosition > downloadInfo.contentLength)
            throw IOException("Start position greater than content length")
        //验证下载完成的任务与实际文件的匹配度
        if (startPosition == downloadInfo.contentLength && startPosition > 0)
            if (file.exists() && startPosition == file.length()) {
                change(DownloadInfo.DONE)
                return@withContext
            } else throw IOException("The content length is not the same as the file length")
        //写入文件
        val randomAccessFile = RandomAccessFile(file, "rw")
        randomAccessFile.seek(startPosition)
        downloadInfo.currentLength = startPosition
        val inputStream = responseBody.byteStream()
        val bufferSize = 1024 * 8
        val buffer = ByteArray(bufferSize)
        val bufferedInputStream = BufferedInputStream(inputStream, bufferSize)
        var readLength: Int
        try {
            while (bufferedInputStream.read(
                    buffer, 0, bufferSize
                ).also {
                    readLength = it
                } != -1 && downloadInfo.status == DownloadInfo.LOADING && isActive//isActive保证任务能被及时取消
            ) {
                randomAccessFile.write(buffer, 0, readLength)
                downloadInfo.currentLength += readLength
                val currentTime = System.currentTimeMillis()
                if (currentTime - downloadInfo.lastRefreshTime > 300) {
                    change(DownloadInfo.LOADING)
                    downloadInfo.lastRefreshTime = currentTime
                }
            }
        } finally {
            inputStream.close()
            randomAccessFile.close()
            bufferedInputStream.close()
        }
    })

    /**
     * 更新任务
     * @param status [DownloadInfo.Status]
     */
    private fun change(status: Int) = launch(Dispatchers.Main) {
        val downloadInfo = downloadData.value
        downloadInfo ?: return@launch
        downloadInfo.status = status
        withContext(Dispatchers.IO) {
            RoomClient.dataBase.downloadDao().insertOrReplace(downloadInfo)
        }
        downloadData.value = downloadInfo
    }

    /**
     * 暂停任务
     * 只有等待中的任务和正在下载中的任务才可以进行暂停操作
     */
    fun pause() {
        cancel(CancellationException("pause"))
        val downloadInfo = downloadData.value
        downloadInfo?.let {
            if (it.status == DownloadInfo.LOADING || it.status == DownloadInfo.WAITING)
                change(DownloadInfo.PAUSE)
        }
    }

    /**
     * 删除任务,删除任务会同时删除已经在数据库中保存的下载信息
     */
    fun remove() = launch(Dispatchers.Main) {
        this@DownloadScope.cancel(CancellationException("remove"))
        val downloadInfo = downloadData.value
        downloadInfo?.reset()
        downloadData.value = downloadInfo
        withContext(Dispatchers.IO) {
            downloadInfo?.let {
                RoomClient.dataBase.downloadDao().delete(it)
                //同时删除已下载的文件
                it.path?.let { path ->
                    val file = File(path)
                    if (file.exists()) file.delete()
                }
            }
        }
    }

    /**
     * 取消[downloadJob],将会中断正在进行的下载任务
     */
    private fun cancel(cause: CancellationException) {
        downloadJob?.cancel(cause)
    }

    /**
     * 是否是等待任务
     */
    fun isWaiting(): Boolean {
        val downloadInfo = downloadData.value
        downloadInfo ?: return false
        return downloadInfo.status == DownloadInfo.WAITING
    }

    /**
     * 是否是正在下载的任务
     */
    fun isLoading(): Boolean {
        val downloadInfo = downloadData.value
        downloadInfo ?: return false
        return downloadInfo.status == DownloadInfo.LOADING
    }
}
