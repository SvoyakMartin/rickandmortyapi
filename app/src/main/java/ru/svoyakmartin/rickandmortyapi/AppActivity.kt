package ru.svoyakmartin.rickandmortyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesContainer
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesProvider
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.activity_main), FeatureExternalDependenciesProvider {

    @Inject
    override lateinit var dependencies: FeatureExternalDependenciesContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
}
