package love.isuper.app

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class ListAdapter: BaseQuickAdapter<ListModel, BaseViewHolder>(R.layout.item_list), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ListModel) {
        holder.setText(R.id.tv_name, item.name + item.id)
    }

}