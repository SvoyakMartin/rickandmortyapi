package ru.svoyakmartin.rickandmortyapi.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.svoyakmartin.rickandmortyapi.di.annotations.ViewModelKey
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharacterDetailsViewModel
import ru.svoyakmartin.rickandmortyapi.presentation.viewModels.CharactersViewModel

@Module
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    @Suppress("UNUSED")
    fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    @Suppress("UNUSED")
    fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}