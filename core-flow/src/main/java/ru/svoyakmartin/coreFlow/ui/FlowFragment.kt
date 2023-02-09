package ru.svoyakmartin.coreFlow.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.svoyakmartin.coreUi.R
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import javax.inject.Inject

open class FlowFragment: Fragment(R.layout.fragment_flow) {
    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder<FragmentManager>

    override fun onResume() {
        super.onResume()
        navigatorHolder.bind(childFragmentManager)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.unbind()
    }
}