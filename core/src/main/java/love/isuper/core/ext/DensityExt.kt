package love.isuper.core.ext

import love.isuper.core.utils.AppInfo

/**
 * Created by shichao on 2022/7/8.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
//使用示例
// 10.dp
inline val Double.dp: Int get() = run {
    return toFloat().dp
}
inline val Int.dp: Int get() = run {
    return toFloat().dp
}
inline val Float.dp: Int get() = run {
    val scale: Float = AppInfo.getApplication().resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

inline val Double.px: Int get() = run {
    return toFloat().px
}
inline val Int.px: Int get() = run {
    return toFloat().px
}
inline val Float.px: Int get() = run {
    val scale: Float = AppInfo.getApplication().resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

