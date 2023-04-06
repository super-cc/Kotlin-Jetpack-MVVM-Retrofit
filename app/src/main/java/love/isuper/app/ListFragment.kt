package love.isuper.app

import love.isuper.core.mvvm.BaseQListFragment
import love.isuper.core.mvvm.ListConfig
import love.isuper.core.utils.ToastUtils
import love.isuper.core.utils.singleclick.setOnSingleClickListener

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

    override fun init() {
        super.init()

        var n = 0
        mViewBinding.fab.setOnSingleClickListener {
            n++
            ToastUtils.showShortToast("点击${n}次")
        }
    }

    override fun liveDataObserver() {

    }

}