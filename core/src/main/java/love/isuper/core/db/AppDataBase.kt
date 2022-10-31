package love.isuper.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
@Database(entities = [DownloadInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun downloadDao(): DownloadDao
}