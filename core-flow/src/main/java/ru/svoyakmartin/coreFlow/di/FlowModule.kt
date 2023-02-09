package ru.svoyakmartin.coreFlow.di

import ru.svoyakmartin.coreNavigation.navigator.FlowNavigatorHolder
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouterImpl
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter

@Module
class FlowModule {

    @Provides
    @FeatureScope
    fun provideFlowRouter(
        globalRouter: GlobalRouter,
        navigatorHolder: NavigatorHolder<FragmentManager>
    ): FlowRouter = FlowRouterImpl(globalRouter, navigatorHolder)

    @Provides
    @FeatureScope
    fun provideFlowNavigatorHolder(): NavigatorHolder<FragmentManager> = FlowNavigatorHolder()
}