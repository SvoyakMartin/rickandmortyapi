package ru.svoyakmartin.featureEpisode.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelKey
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDB
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeDetailsViewModel
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeListViewModel

@Module
class EpisodeProvidesModule {
    @Provides
    fun provideLocationApi(retrofit: Retrofit): EpisodesApi = retrofit.create()

    @Provides
    fun provideLocationRoomDAO(context: Context): EpisodeRoomDAO {
        return EpisodeRoomDB.getDatabase(context).getEpisodeDao()
    }
}

@Module
interface EpisodeBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    @Suppress("UNUSED")
    fun bindEpisodeDetailsViewModel(viewModel: EpisodeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeListViewModel::class)
    @Suppress("UNUSED")
    fun bindEpisodeListViewModel(viewModel: EpisodeListViewModel): ViewModel
}