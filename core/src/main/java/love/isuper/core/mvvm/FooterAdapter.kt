package love.isuper.core.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import love.isuper.core.R
import love.isuper.core.ext.logD

/**
 * Created by shichao on 2022/5/20.
 */
class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val tvRetryLoad: TextView = itemView.findViewById(R.id.tv_retry_load)
        val tvNoMore: TextView = itemView.findViewById(R.id.tv_no_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_load_footer, parent, false)
        val holder = ViewHolder(view)
        holder.tvRetryLoad.setOnClickListener {
            retry()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.tvRetryLoad.isVisible = loadState is LoadState.Error
        holder.tvNoMore.isVisible = loadState.endOfPaginationReached

        "loadState = ${loadState}".logD(this.javaClass.simpleName)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState)
//                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }

}