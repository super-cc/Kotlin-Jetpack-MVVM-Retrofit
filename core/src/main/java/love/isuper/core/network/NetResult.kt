package love.isuper.core.network

import love.isuper.core.network.exception.ResultException

sealed class NetResult<out T : SuperResponse> {

    data class Success<out T : SuperResponse>(val data: T) : NetResult<T>()

    data class Failure<out T : SuperResponse>(val data: T) : NetResult<T>()

    data class Error(val exception: ResultException) : NetResult<Nothing>()

}