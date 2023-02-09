package ru.svoyakmartin.coreNavigation.router.global

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import ru.svoyakmartin.core.shortClassName
import ru.svoyakmartin.coreUi.R
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import javax.inject.Inject

class GlobalRouterImpl @Inject constructor(private val navigator: NavigatorHolder<FragmentActivity>) : GlobalRouter {

    override fun newRootFlow(fragment: Fragment) {
        navigator.executeCommand {
            supportFragmentManager.apply {
                popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                commit {
                    replace(R.id.base_fragment_container, fragment)
                    setReorderingAllowed(true)
                    setPrimaryNavigationFragment(fragment)
                }
            }
        }
    }

    override fun startFlow(fragment: Fragment) {
        navigator.executeCommand {
            supportFragmentManager.commit {
                replace(R.id.base_fragment_container, fragment)
                addToBackStack(fragment.shortClassName)
                setReorderingAllowed(true)
                setPrimaryNavigationFragment(fragment)
            }
        }
    }

    override fun back() {
        navigator.executeCommand {
            supportFragmentManager.popBackStack()
        }
    }

    override fun backToRoot() {
        navigator.executeCommand {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}