package love.isuper.core.network.download

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
interface DownloadService {

    @Streaming
    @GET
    suspend fun download(@Header("RANGE") start: String? = "0", @Url url: String?): Response<ResponseBody>

}