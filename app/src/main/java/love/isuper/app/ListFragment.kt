package love.isuper.app

import androidx.lifecycle.lifecycleScope
import love.isuper.core.mvvm.BaseQListFragment
import love.isuper.core.mvvm.ListConfig

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class ListFragment: BaseQListFragment<ListViewModel, ListModel>() {

    override val listConfig: ListConfig = ListConfig(
        canSearch = true,
        fabRes = R.drawable.sub_account_add_icon,
    )
    override val adapter = ListAdapter()

    override fun liveDataObserver() {

    }

    override fun init() {
        super.init()
    }

}