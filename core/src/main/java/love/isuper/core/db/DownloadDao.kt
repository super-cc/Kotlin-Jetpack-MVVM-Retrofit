package love.isuper.core.db

import androidx.room.*

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
@Dao
interface DownloadDao {

    /**
     * 获取所有
     */
    @Query("select * from DownloadInfo")
    suspend fun queryAll(): MutableList<DownloadInfo>

    /**
     * 通过状态查询任务
     */
    @Query("select * from DownloadInfo where status =:status")
    suspend fun queryByStatus(status: Int): MutableList<DownloadInfo>

    /**
     * 查询正在下载的任务
     */
    @Query("select * from DownloadInfo where status != ${DownloadInfo.DONE} and status != ${DownloadInfo.NONE}")
    suspend fun queryLoading(): MutableList<DownloadInfo>

    /**
     * 查询正在下载的任务的url
     */
    @Query("select url from DownloadInfo where status != ${DownloadInfo.DONE}  and status != ${DownloadInfo.NONE}")
    suspend fun queryLoadingUrls(): MutableList<String>

    /**
     * 查询下载完成的任务
     */
    @Query("select * from DownloadInfo where status == ${DownloadInfo.DONE}")
    suspend fun queryDone(): MutableList<DownloadInfo>

    /**
     * 查询下载完成的任务的url
     */
    @Query("select url from DownloadInfo where status == ${DownloadInfo.DONE}")
    suspend fun queryDoneUrls(): MutableList<String>

    /**
     * 通过url查询,每一个任务他们唯一的标志就是url
     */
    @Query("select * from DownloadInfo where url like:url")
    suspend fun queryByUrl(url: String): DownloadInfo?

    /**
     * 插入或替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(vararg downloadData: DownloadInfo): List<Long>

    /**
     * 删除
     */
    @Delete
    suspend fun delete(downloadDao: DownloadInfo)

}