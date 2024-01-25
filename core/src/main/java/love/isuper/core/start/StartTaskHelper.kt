package love.isuper.core.start

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import love.isuper.core.utils.LogUtils
import love.isuper.core.utils.TimeRecorder

/**
 *  author : guoshichao
 *  date : 2024/1/25 11:03
 *  description : 两个线程同时执行App初始化任务，并且在全部执行完毕后，通知给业务层
 */
object StartTaskHelper {

    private const val TAG = "StartTaskHelper"

    // 创建一个协程作用域
    private val mainScope = MainScope()

    private val taskList = mutableListOf<StartTask>()

    fun addTask(task: StartTask) {
        taskList.add(task)
    }

    fun start(callback: ()->Unit) {
        // 开始执行任务
        startTasks(taskList) { results->
            // 任务执行完成后的回调
            LogUtils.i(TAG, "All tasks completed: $results")
            // 清空任务列表
            taskList.clear()
            // 通知业务层任务执行完成，进行其它活动
            callback.invoke()
        }
    }

    /**
     * 开始执行任务
     * @param tasks List<StartTask> 任务列表
     * @param results (List<String>) -> Unit 执行完成回调
     */
    private fun startTasks(tasks: List<StartTask>, results: (List<String>)->Unit) {
        mainScope.launch {
            // 同时在主线程和子线程执行任务
            val resultsFromMainThread = executeTasksOnMainThread(tasks)
            val resultsFromChildThread = executeTasksOnChildThread(tasks)

            // 等待两个线程的任务都完成
            TimeRecorder.start("startTasks")
            val combinedResults = resultsFromMainThread.await() + resultsFromChildThread.await()
            LogUtils.i(TAG, "------all tasks, run time = ${TimeRecorder.stop("startTasks")}")

            // 通知业务层任务执行完成
            results.invoke(combinedResults)
        }
    }

    /**
     * 在主线程执行任务
     * @param tasks List<StartTask> 任务列表
     * @return Deferred<List<String>> 任务执行结果
     */
    private suspend fun executeTasksOnMainThread(tasks: List<StartTask>): Deferred<List<String>> = mainScope.async {
        // 模拟主线程执行任务
        LogUtils.i(TAG, "executeTasksOnMainThread: ${Thread.currentThread()}")
        TimeRecorder.start("executeTasksOnMainThread")
        val resultList = mutableListOf<String>()
        tasks.forEach {
            if (it.inMain) {
                TimeRecorder.start(it.name)
                it.task.invoke()
                resultList.add("Result main thread: ${it.name}")
                LogUtils.i(TAG, "task name = ${it.name}, run time = ${TimeRecorder.stop(it.name)}, main thread")
            }
        }
        LogUtils.i(TAG, "------main tasks, run time = ${TimeRecorder.stop("executeTasksOnMainThread")}")
        resultList
    }

    /**
     * 在子线程执行任务
     * @param tasks List<StartTask> 任务列表
     * @return Deferred<List<String>> 任务执行结果
     */
    private suspend fun executeTasksOnChildThread(tasks: List<StartTask>): Deferred<List<String>> = mainScope.async(Dispatchers.Default) {
        // 模拟子线程执行任务
        LogUtils.i(TAG, "executeTasksOnChildThread: ${Thread.currentThread()}")
        TimeRecorder.start("executeTasksOnChildThread")
        val resultList = mutableListOf<String>()
        tasks.forEach {
            if (!it.inMain) {
                TimeRecorder.start(it.name)
                it.task.invoke()
                resultList.add("Result child thread: ${it.name}")
                LogUtils.i(TAG, "task name = ${it.name}, run time = ${TimeRecorder.stop(it.name)}, child thread")
            }
        }
        LogUtils.i(TAG, "------child tasks, run time = ${TimeRecorder.stop("executeTasksOnChildThread")}")
        resultList
    }

    // 定义一个回调接口
    interface TaskCallback {
        fun onTasksCompleted()
    }

}