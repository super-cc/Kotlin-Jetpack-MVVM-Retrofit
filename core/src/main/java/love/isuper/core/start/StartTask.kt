package love.isuper.core.start

import kotlinx.coroutines.Runnable

/**
 *  author : guoshichao
 *  date : 2024/1/25 11:45
 *  description :
 */
data class StartTask(
    val name: String,
    val task: suspend () -> Unit,
    val inMain: Boolean = true,
)