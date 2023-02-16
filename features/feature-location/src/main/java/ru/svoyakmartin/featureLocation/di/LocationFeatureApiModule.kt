package ru.svoyakmartin.featureLocation.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureLocation.LocationFeatureApiImpl
import ru.svoyakmartin.featureLocation.data.ExportRepository
import ru.svoyakmartin.featureLocation.data.ExportRepositoryImpl
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi

@Module
interface LocationFeatureApiModule {

    @Binds
    fun bindLocationFeatureApi(api: LocationFeatureApiImpl): LocationFeatureApi

    @Binds
    fun bindExportRepository(repository: ExportRepositoryImpl): ExportRepository
}