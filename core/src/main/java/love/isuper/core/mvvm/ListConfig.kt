package love.isuper.core.mvvm

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by shichao on 2022/5/20.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
data class ListConfig (
    val title: CharSequence? = null,
    @StringRes val titleRes: Int? = null,
    val canSearch: Boolean = false,
    val canRefresh: Boolean = true,
    val canLoadMore: Boolean = true,
    @DrawableRes val fabRes: Int? = null,
)