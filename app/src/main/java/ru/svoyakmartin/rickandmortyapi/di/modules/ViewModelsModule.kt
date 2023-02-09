package ru.svoyakmartin.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelKey
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterDetailsViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterListViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.*

@Module
interface ViewModelsModule {
        @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    @Suppress("UNUSED")
    fun bindEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    @Suppress("UNUSED")
    fun bindEpisodeDetailsViewModel(viewModel: EpisodeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    @Suppress("UNUSED")
    fun bindLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    @Suppress("UNUSED")
    fun bindLocationsViewModel(viewModel: LocationsViewModel): ViewModel
}