package love.isuper.core.network

import java.util.HashMap

/**
 * Created by shichao on 2022/7/22.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
interface INetwork {
    fun getBaseUrl(): String
    fun getHeaders(): HashMap<String, String>
}