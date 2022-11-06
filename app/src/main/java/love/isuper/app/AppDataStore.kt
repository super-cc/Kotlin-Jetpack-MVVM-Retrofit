package love.isuper.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import love.isuper.core.ext.clear
import love.isuper.core.ext.getData
import love.isuper.core.ext.putData

/**
 * Created by 郭士超 on 2022/11/6 16:38
 * Describe：将同模块下的数据统一管理
 */
object AppDataStore {

    // 创建DataStore
    private val MyApplication.appDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "App"
    )

    // DataStore变量
    private val dataStore = MyApplication.instance.appDataStore

    private fun <T> putData(key: String, value: T) {
        dataStore.putData(key, value)
    }

    private fun <T> gutData(key: String, value: T): T {
        return dataStore.getData(key, value)
    }

    fun clear() {
        dataStore.clear()
    }

    private const val NUMBER = "number"
    fun putNumber(number: Int) {
        dataStore.putData(NUMBER, number)
    }
    fun getNumber(): Int {
        return dataStore.getData(NUMBER, 0)
    }



}