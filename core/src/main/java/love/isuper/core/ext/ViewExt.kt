package love.isuper.core.ext

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by shichao on 2022/5/18.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */


fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.textColor(@ColorRes res: Int) {
    setTextColor(context.resources.getColor(res))
}

/**
 * 拦截BottomNavigation长按事件 防止长按时出现Toast ---- 追求完美的大屌群友提的bug
 * @receiver BottomNavigationViewEx
 * @param ids IntArray
 */
fun BottomNavigationView.interceptLongClick(vararg ids:Int) {
    val bottomNavigationMenuView: ViewGroup = (this.getChildAt(0) as ViewGroup)
    for (index in ids.indices){
        bottomNavigationMenuView.getChildAt(index).findViewById<View>(ids[index]).setOnLongClickListener {
            true
        }
    }
}