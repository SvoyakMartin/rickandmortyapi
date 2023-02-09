package ru.svoyakmartin.coreDi.di.dependency

import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureExternalDependenciesKey(val value: KClass<out FeatureExternalDependencies>)