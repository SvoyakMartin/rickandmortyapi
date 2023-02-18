package ru.svoyakmartin.featureSettings.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.svoyakmartin.featureSettings.di.DaggerSettingsComponent
import ru.svoyakmartin.featureSettings.di.SettingsExternalDependencies

class SettingsComponentViewModel : ViewModel(){
    val component by lazy {
        DaggerSettingsComponent.factory()
            .create(checkNotNull(SettingsComponentDependenciesProvider.dependencies))
    }

    override fun onCleared() {
        super.onCleared()
        SettingsComponentDependenciesProvider.dependencies = null
    }
}

object SettingsComponentDependenciesProvider{
    var dependencies: SettingsExternalDependencies? = null
}