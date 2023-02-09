package ru.svoyakmartin.coreNavigation.router.flow

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import ru.svoyakmartin.core.shortClassName
import ru.svoyakmartin.coreUi.R
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter

class FlowRouterImpl constructor(
    private val globalRouter: GlobalRouter,
    private val navigatorHolder: NavigatorHolder<FragmentManager>
) : FlowRouter {

    override fun newFlowRootScreen(fragment: Fragment) {
        navigatorHolder.executeCommand {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            commit {
                replace(R.id.flow_container, fragment)
                setReorderingAllowed(true)
            }
        }
    }

    override fun navigateTo(fragment: Fragment) {
        navigatorHolder.executeCommand {
            commit {
                setReorderingAllowed(true)
                add(R.id.flow_container, fragment)
                addToBackStack(fragment.shortClassName)
            }
        }
    }

    override fun newRootFlow(fragment: Fragment) {
        globalRouter.newRootFlow(fragment)
    }

    override fun startFlow(fragment: Fragment) {
        globalRouter.startFlow(fragment)
    }

    override fun back() {
        navigatorHolder.executeCommand {
            popBackStack()
        }
    }

    override fun backToRoot() {
        navigatorHolder.executeCommand {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}