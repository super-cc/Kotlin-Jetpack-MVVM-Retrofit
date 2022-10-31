package love.isuper.core.db

import androidx.room.TypeConverter
import java.io.*

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class Converters {

    @TypeConverter
    fun toByteArray(serializable: Serializable?): ByteArray? {
        serializable ?: return null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(serializable)

            objectOutputStream.flush()
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.close()
            objectOutputStream?.close()
        }
        return null
    }

    @TypeConverter
    fun toSerializable(byteArray: ByteArray?): Serializable? {
        byteArray ?: return null
        var byteArrayOutputStream: ByteArrayInputStream? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            byteArrayOutputStream = ByteArrayInputStream(byteArray)
            objectInputStream = ObjectInputStream(byteArrayOutputStream)
            return objectInputStream.readObject() as Serializable
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.close()
            objectInputStream?.close()
        }
        return null
    }
}