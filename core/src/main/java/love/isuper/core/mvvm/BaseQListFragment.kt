package love.isuper.core.mvvm

import android.text.TextUtils
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import love.isuper.core.R
import love.isuper.core.databinding.FragmentBaseListBinding
import love.isuper.core.ext.nav
import love.isuper.core.ext.visible
import love.isuper.core.utils.ToastUtils

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
abstract class BaseQListFragment<VM : BaseQListViewModel<M>, M : Any> : BaseFragment<VM, FragmentBaseListBinding>() {

    abstract val listConfig: ListConfig
    abstract val adapter: BaseQuickAdapter<M, out BaseViewHolder>

    override fun onResume() {
        super.onResume()
        if (!_firstLoad && onResumeNeedRefresh()) {
            mViewBinding.refreshLayout.autoRefresh()
        }
    }

    @CallSuper
    override fun init() {
        mViewBinding.apply {
            titleBar.setIvLeftOnClickListener {
                nav().navigateUp()
            }
            titleBar.setCenterText(listConfig.title ?: requireContext().getString(listConfig.titleRes ?: R.string.core_empty))
            titleBar.visible(!TextUtils.isEmpty(titleBar.tvCenter.text))

            searchView.visible(listConfig.canSearch)
            if (listConfig.canSearch) {
                searchView.setSearchListener {
                    onRefresh()
                }
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            if (adapter is LoadMoreModule && listConfig.canLoadMore) {
                adapter.loadMoreModule.setOnLoadMoreListener {
                    onLoadMore()
                }
            }
            recyclerView.adapter = adapter

            refreshLayout.setEnableRefresh(listConfig.canRefresh)
            refreshLayout.setEnableLoadMore(false)
            if (listConfig.canRefresh) {
                refreshLayout.setOnRefreshListener {
                    onRefresh()
                }
                refreshLayout.autoRefresh()
            } else {
                onRefresh()
            }

            if (listConfig.fabRes != null) {
                fab.setImageResource(listConfig.fabRes!!)
                fab.visibility = View.VISIBLE
                fab.setOnClickListener {
                    onFabClick(it)
                }
            } else {
                fab.visibility = View.GONE
            }
        }
    }

    protected open fun onFabClick(fab: View) {

    }

    protected open fun onResumeNeedRefresh(): Boolean {
        return false
    }

    private fun onRefresh() {
        mViewModel.refresh(mViewBinding.searchView.keyWord) { success, dataList, hasMore, msg->
            if (success) {
                adapter.setNewInstance(dataList)
                mViewBinding.refreshLayout.finishRefresh()
                if (!hasMore) {
                    adapter.loadMoreModule.loadMoreEnd()
                }
            } else {
                ToastUtils.showToast(msg)
            }
        }
    }

    private fun onLoadMore() {
        mViewModel.loadMore(mViewBinding.searchView.keyWord) { success, dataList, hasMore, msg->
            if (success) {
                adapter.addData(dataList)
                if (adapter is LoadMoreModule) {
                    adapter.loadMoreModule.loadMoreComplete()
                }
                if (!hasMore) {
                    adapter.loadMoreModule.loadMoreEnd()
                }
            } else {
                ToastUtils.showToast(msg)
                if (adapter is LoadMoreModule) {
                    adapter.loadMoreModule.loadMoreFail()
                }
            }
        }
    }

}