package ru.svoyakmartin.featureHomeScreen.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.svoyakmartin.featureHomeScreen.di.DaggerHomeScreenComponent
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenExternalDependencies

class HomeScreenComponentViewModel : ViewModel(){
    val component by lazy {
        DaggerHomeScreenComponent.factory()
            .create(checkNotNull(HomeScreenComponentDependenciesProvider.dependencies))
    }

    override fun onCleared() {
        super.onCleared()
        HomeScreenComponentDependenciesProvider.dependencies = null
    }
}

object HomeScreenComponentDependenciesProvider{
    var dependencies: HomeScreenExternalDependencies? = null
}