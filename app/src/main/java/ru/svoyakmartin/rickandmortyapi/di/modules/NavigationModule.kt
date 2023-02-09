package ru.svoyakmartin.rickandmortyapi.di.modules

import androidx.fragment.app.FragmentActivity
import dagger.Binds
import dagger.Module
import ru.svoyakmartin.coreNavigation.navigator.ActivityNavigatorHolder
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouterImpl

@Module
interface NavigationModule {

    @Binds
    fun bindActivityNavigatorHolder(navigator: ActivityNavigatorHolder): NavigatorHolder<FragmentActivity>

    @Binds
    fun bindGlobalRouter(globalRouter: GlobalRouterImpl): GlobalRouter
}