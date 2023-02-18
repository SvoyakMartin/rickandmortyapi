package ru.svoyakmartin.coreDi.di.dependency

import androidx.fragment.app.Fragment

typealias FeatureExternalDependenciesContainer = Map<Class<out FeatureExternalDependencies>, @JvmSuppressWildcards FeatureExternalDependencies>

interface FeatureExternalDependenciesProvider {
    val dependencies: FeatureExternalDependenciesContainer
}

inline fun <reified T : FeatureExternalDependencies> FeatureExternalDependenciesProvider.get(): T =
    dependencies.getValue(T::class.java) as T

inline fun <reified T : FeatureExternalDependencies> Fragment.findFeatureExternalDependencies(): T =
    (requireActivity() as? FeatureExternalDependenciesProvider)?.get() ?: error("Feature external deps not found")