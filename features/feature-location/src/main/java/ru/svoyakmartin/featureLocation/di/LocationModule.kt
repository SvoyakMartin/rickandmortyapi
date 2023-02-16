package ru.svoyakmartin.featureLocation.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelKey
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDB
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationDetailsViewModel
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationListViewModel

@Module
class LocationProvidesModule {
    @Provides
    fun provideLocationApi(retrofit: Retrofit): LocationsApi = retrofit.create()

    @Provides
    fun provideLocationRoomDAO(context: Context): LocationRoomDAO {
        return LocationRoomDB.getDatabase(context).getLocationDao()
    }
}

@Module
interface LocationBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    @Suppress("UNUSED")
    fun bindLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel::class)
    @Suppress("UNUSED")
    fun bindLocationsViewModel(viewModel: LocationListViewModel): ViewModel
}