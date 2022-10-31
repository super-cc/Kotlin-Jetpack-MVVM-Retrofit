package love.isuper.core.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * Created by guoshichao on 2022/5/18
*/
@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> AppCompatActivity.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Fragment.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, layoutInflater, parent, attachToParent) as VB
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> RecyclerView.Adapter<*>.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, layoutInflater, parent, attachToParent) as VB
    }

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    var genericSuperclass = any.javaClass.genericSuperclass
    var superclass = any.javaClass.superclass
    while (superclass != null) {
        if (genericSuperclass is ParameterizedType) {
                try {
                    return block.invoke(genericSuperclass.actualTypeArguments[1] as Class<VB>)
                } catch (e: NoSuchMethodException) {
                } catch (e: ClassCastException) {
                } catch (e: InvocationTargetException) {
                    throw e.targetException
                }
        }
        genericSuperclass = superclass.genericSuperclass
        superclass = superclass.superclass
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}