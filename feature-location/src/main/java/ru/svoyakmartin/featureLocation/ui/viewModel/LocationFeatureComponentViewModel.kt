package ru.svoyakmartin.featureLocation.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.svoyakmartin.featureLocation.di.DaggerLocationComponent
import ru.svoyakmartin.featureLocation.di.LocationExternalDependencies

class LocationFeatureComponentViewModel : ViewModel() {
    val component by lazy {
        DaggerLocationComponent.factory()
            .create(checkNotNull(LocationFeatureComponentDependenciesProvider.featureDependencies))
    }

    override fun onCleared() {
        super.onCleared()
        LocationFeatureComponentDependenciesProvider.featureDependencies = null
    }
}

object LocationFeatureComponentDependenciesProvider {
    var featureDependencies: LocationExternalDependencies? = null
}