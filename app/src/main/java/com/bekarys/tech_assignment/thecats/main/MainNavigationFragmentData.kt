package com.bekarys.tech_assignment.thecats.main

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class MainNavigationFragmentData(private val lazyFragment: Lazy<Fragment>) {

    private var instantiatedFragment: WeakReference<Fragment>? =
        null

    private fun getInstantiatedFragment(): Fragment? = instantiatedFragment?.get()

    fun getFragment(): Fragment {
        val instantiatedFragment = getInstantiatedFragment()
        if (instantiatedFragment != null)
            return instantiatedFragment

        val fragment = lazyFragment.value
        this.instantiatedFragment = WeakReference(fragment)
        return fragment
    }

}
