package love.isuper.core.network

import android.util.Patterns

/**
 * Created by shichao on 2022/6/1.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
object HttpUtils {

    fun isHttpUrl(url: String?): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

}