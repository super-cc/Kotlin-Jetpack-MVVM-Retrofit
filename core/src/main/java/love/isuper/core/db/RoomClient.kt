package love.isuper.core.db

import androidx.room.Room
import androidx.room.migration.Migration
import love.isuper.core.utils.AppInfo

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
object RoomClient {

    private const val DATA_BASE_NAME = "download.db"

    val dataBase: AppDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Room
            .databaseBuilder(
                AppInfo.getApplication().applicationContext,
                AppDataBase::class.java,
                DATA_BASE_NAME
            )
            .build()
    }

    private fun createMigrations(): Array<Migration> {
        return arrayOf()
    }

}