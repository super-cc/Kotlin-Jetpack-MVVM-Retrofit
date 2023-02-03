package love.isuper.core.utils.singleclick

import android.view.View

/**
 *  author : guoshichao
 *  date : 2023/2/3 11:19
 *  description :
 */
fun View.setOnSingleClickListener(onClickListener: View.OnClickListener) {
    setOnClickListener(SingleClickProxy(onClickListener))
}