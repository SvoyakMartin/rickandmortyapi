package ru.svoyakmartin.coreNavigation.router.flow

import androidx.fragment.app.Fragment
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter

interface FlowRouter : GlobalRouter {
    fun newFlowRootScreen(fragment: Fragment)
    fun navigateTo(fragment: Fragment)
}