package love.isuper.core.ext

import androidx.lifecycle.LifecycleOwner
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Created by shichao on 2022/7/8.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
fun <T> LifecycleOwner.eventObserve(eventKey: String, observer: (t: T) -> Unit) {
    LiveEventBus.get<T>(eventKey).observe(this) {
        observer(it)
    }
}

fun <T> Any.eventPost(eventKey: String, t: T) {
    LiveEventBus.get<T>(eventKey).post(t)
}