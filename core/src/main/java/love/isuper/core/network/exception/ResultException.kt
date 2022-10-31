package love.isuper.core.network.exception


class ResultException(var errCode: String?, var msg: String?) : Exception(msg)
