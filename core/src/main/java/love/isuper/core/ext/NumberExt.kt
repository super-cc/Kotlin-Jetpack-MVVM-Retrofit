package love.isuper.core.ext

import java.text.DecimalFormat

/**
 * Created by shichao on 2022/8/2.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
fun Double.Decimal2(): String {
    val df = DecimalFormat("######0.00")
    return df.format(this)
}

fun Float.Decimal2(): String {
    val df = DecimalFormat("######0.00")
    return df.format(this)
}

fun Long.Decimal2(): String {
    val df = DecimalFormat("######0.00")
    return df.format(this)
}

fun Int.Decimal2(): String {
    val df = DecimalFormat("######0.00")
    return df.format(this)
}