package ru.svoyakmartin.rickandmortyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesContainer
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesProvider
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.global.GlobalRouter
import ru.svoyakmartin.coreUi.R
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureHomeScreenApi.HomeScreenFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.activity_main), FeatureExternalDependenciesProvider {

    @Inject
    override lateinit var dependencies: FeatureExternalDependenciesContainer

    @Inject
    lateinit var navigatorHolder: NavigatorHolder<FragmentActivity>

    @Inject
    lateinit var homeScreenFeatureApi: HomeScreenFeatureApi

    @Inject
    lateinit var globalRouter: GlobalRouter

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.bind(this)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.unbind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        globalRouter.newRootFlow(homeScreenFeatureApi.getFlowFragment())
    }
}
