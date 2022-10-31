package love.isuper.app

import love.isuper.core.mvvm.BaseQListViewModel

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class ListViewModel: BaseQListViewModel<ListModel>() {

    override var limit: Int = 20
    var index: Int = 1

    override fun refresh(keyWord: String, results: (Boolean, MutableList<ListModel>, Boolean, String) -> Unit) {
        index = 0
        val list = mutableListOf<ListModel>()
        for (i in 1 .. 20) {
            list.add(ListModel(id = index.toString()))
            index++
        }
        results(true, list, true, "")
    }

    override fun loadMore(keyWord: String, results: (Boolean, MutableList<ListModel>, Boolean, String) -> Unit) {
        val list = mutableListOf<ListModel>()
        for (i in 1 .. 20) {
            list.add(ListModel(id = index.toString()))
            index++
        }
        if (index < 120) {
            results(true, list, true, "")
        } else {
            results(true, list, false, "")
        }
    }

}