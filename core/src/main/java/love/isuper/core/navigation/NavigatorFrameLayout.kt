package love.isuper.core.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

/**
 * Created by guoshichao on 2022/5/18
 */
class NavigatorFrameLayout(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
    FrameLayout(context, attributeSet, defStyle) {

    override fun addView(child: View?) {
        super.addView(child)
        requestApplyInsets()
    }

    override fun dispatchApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        var windowInsets = super.dispatchApplyWindowInsets(insets)
        if (!insets!!.isConsumed) {
            val count = childCount
            for (i in 0 until count) {
                windowInsets = getChildAt(i).dispatchApplyWindowInsets(insets)
            }
        }
        return windowInsets

    }

}