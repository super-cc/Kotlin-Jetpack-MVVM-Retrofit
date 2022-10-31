package love.isuper.core.mvvm

import android.text.TextUtils
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import love.isuper.core.R
import love.isuper.core.databinding.FragmentBaseListBinding
import love.isuper.core.ext.nav
import love.isuper.core.ext.visible
import love.isuper.core.utils.ToastUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by shichao on 2022/5/19.
 */
abstract class BaseListFragment<VM : BaseListViewModel<M>, M : Any> : BaseFragment<VM, FragmentBaseListBinding>() {

    abstract val listConfig: ListConfig
    abstract val adapter: BasePagingAdapter<M, out ViewBinding>

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
            if (listConfig.canLoadMore) {
                recyclerView.adapter = adapter.withLoadStateFooter(FooterAdapter { adapter.retry() })
            } else {
                recyclerView.adapter = adapter
            }

            adapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.NotLoading -> {
                        onLoadEnd()
                    }
                    is LoadState.Loading -> {

                    }
                    is LoadState.Error -> {
                        onLoadEnd()
                        val state = it.refresh as LoadState.Error
                        ToastUtils.showToast("Load Error: ${state.error.message}")
                    }
                }
            }

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
        lifecycleScope.launch {
            mViewModel.getPagingData(mViewBinding.searchView.keyWord).collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun onLoadEnd() {
        mViewBinding.apply {
            refreshLayout.finishRefresh()
            refreshLayout.finishLoadMore()
        }
    }

}