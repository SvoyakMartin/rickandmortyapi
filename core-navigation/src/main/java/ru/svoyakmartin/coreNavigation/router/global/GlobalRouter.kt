package ru.svoyakmartin.coreNavigation.router.global

import androidx.fragment.app.Fragment

interface GlobalRouter {
    fun newRootFlow(fragment: Fragment)
    fun startFlow(fragment: Fragment)
    fun back()
    fun backToRoot()
}