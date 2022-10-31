package love.isuper.core.network

import love.isuper.core.network.exception.DealException
import love.isuper.core.utils.ToastUtils

open class BaseRepository {

    suspend fun <T: SuperResponse> catchException(function: suspend () -> T): NetResult<T> {
        try {
            val response = function()
            if (response.code == 0) {
                return NetResult.Success(response)
            } else {
                return NetResult.Failure(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val exception = DealException.handlerException(e)
            ToastUtils.showToast(exception.msg)
            return NetResult.Error(exception)
        }
    }

}