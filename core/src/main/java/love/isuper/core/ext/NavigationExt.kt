package love.isuper.core.ext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import love.isuper.core.R

/**
 * Created by shichao on 2022/5/18.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun nav(view: View): NavController {
    return Navigation.findNavController(view)
}


private val defaultNavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.page_enter_anim)
    .setExitAnim(R.anim.page_exit_anim)
    .setPopEnterAnim(R.anim.page_pop_enter_anim)
    .setPopExitAnim(R.anim.page_pop_exit_anim)
    .setLaunchSingleTop(true)
    .build()
/**
 * 跳转Fragment
 * @param resId 跳转的action Id
 * @param bundle 传递的参数
 * @param navOptions 动画
 */
fun NavController.navigateAction(resId: Int, bundle: Bundle? = null, navOptions: NavOptions = defaultNavOptions) {
    navigate(resId, bundle, navOptions)
}


var lastNavTime = 0L
/**
 * 防止短时间内多次快速跳转Fragment出现的bug
 * @param resId 跳转的action Id
 * @param bundle 传递的参数
 * @param interval 多少毫秒内不可重复点击 默认0.5秒
 */
fun NavController.navigateSingleAction(resId: Int, bundle: Bundle? = null, interval: Long = 500) {
    val currentTime = System.currentTimeMillis()
    if (currentTime >= lastNavTime + interval) {
        lastNavTime = currentTime
        try {
            navigate(resId, bundle)
        }catch (ignore:Exception){
            ignore.printStackTrace()
            //防止出现 当 fragment 中 action 的 duration设置为 0 时，连续点击两个不同的跳转会导致如下崩溃 #issue53
        }
    }
}

