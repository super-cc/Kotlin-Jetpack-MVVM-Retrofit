package love.isuper.core.mvvm

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
abstract class BaseQListViewModel<M: Any> : BaseViewModel() {

    var offset: Int = 0
    abstract var limit: Int

    /**
     * keyWord 参数代代表  搜索的关键字
     * results 参数分别为  请求是否成功，列表数据，是否还有更多数据，错误信息
     */
    abstract fun refresh(keyWord: String, results: (Boolean, MutableList<M>, Boolean, String) -> Unit)

    /**
     * keyWord 参数代代表  搜索的关键字
     * results 参数分别为  请求是否成功，列表数据，是否还有更多数据，错误信息
     */
    abstract fun loadMore(keyWord: String, results: (Boolean, MutableList<M>, Boolean, String) -> Unit)

}