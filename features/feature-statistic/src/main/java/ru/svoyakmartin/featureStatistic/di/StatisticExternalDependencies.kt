package ru.svoyakmartin.featureStatistic.di

import retrofit2.Retrofit
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies

interface StatisticExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
}