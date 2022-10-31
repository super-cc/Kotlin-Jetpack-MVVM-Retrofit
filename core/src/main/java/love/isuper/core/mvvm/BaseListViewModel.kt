package love.isuper.core.mvvm

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by shichao on 2022/5/19.
 */
abstract class BaseListViewModel<M: Any> : BaseViewModel() {

    /**
     * keyWord 参数代代表  搜索的关键字
     */
    abstract fun getPagingData(keyWord: String): Flow<PagingData<M>>

}