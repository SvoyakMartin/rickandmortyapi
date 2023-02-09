package ru.svoyakmartin.featureCharacter.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelKey
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDB
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterDetailsViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterListViewModel

@Module
class CharacterProvidesModule {
    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharactersApi = retrofit.create()

    @Provides
    fun provideCharacterRoomDAO(context: Context): CharacterRoomDAO {
        return CharacterRoomDB.getDatabase(context).getCharacterDao()
    }
}

@Module
interface CharacterBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    fun bindCharacterListViewModel(viewModel: CharacterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}