package love.isuper.core.ext

import java.security.MessageDigest


/**
 * Created by shichao on 2022/6/1.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
fun String.encodeMD5(): String {

    val hash = MessageDigest.getInstance("MD5").digest(toByteArray())
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        var str = Integer.toHexString(b.toInt())
        if (b < 0x10) {
            str = "0$str"
        }
        hex.append(str.substring(str.length -2))
    }
    return hex.toString()

}

fun String.decodeMD5(): String {
    return ""
}