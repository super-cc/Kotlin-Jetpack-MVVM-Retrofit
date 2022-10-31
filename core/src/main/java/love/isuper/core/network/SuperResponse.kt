package love.isuper.core.network

import com.google.gson.annotations.SerializedName

/**
 * Created by shichao on 2022/6/24.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
open class SuperResponse {
    @SerializedName("RetCode") val code: Int = -1
    @SerializedName("Message") val msg: String = ""
    @SerializedName("RequestId") val requestId: String = ""
    @SerializedName("Action") val action: String = ""
    @SerializedName("TotalCount") val totalCount: Int = 0
}