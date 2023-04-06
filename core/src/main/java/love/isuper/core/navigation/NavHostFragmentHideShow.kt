package love.isuper.core.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by guoshichao on 2022/5/18
 */
class NavHostFragmentHideShow : NavHostFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frameLayout = NavigatorFrameLayout(inflater.context)
        frameLayout.id = id
        return frameLayout
    }

    @Deprecated("Use {@link #onCreateNavController(NavController)}")
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return FragmentHideShowNavigator(requireContext(), childFragmentManager, containerId)
    }

    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
                // Fallback to using our own ID if this Fragment wasn't added via
                // add(containerViewId, Fragment)
            } else androidx.navigation.fragment.R.id.nav_host_fragment_container
        }

}