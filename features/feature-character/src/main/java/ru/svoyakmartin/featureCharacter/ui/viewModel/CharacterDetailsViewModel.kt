package ru.svoyakmartin.featureCharacter.ui.viewModel

import android.net.Uri
import android.view.View
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.featureCharacter.data.CharacterRepositoryImpl
import ru.svoyakmartin.featureCharacter.data.dataSource.toCharacter
import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureCore.util.getEntityMapListStateFlow
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val repository: CharacterRepositoryImpl) :
    BaseLoadingErrorViewModel() {
    private val _isEpisodesVisible = MutableStateFlow(false)
    val isEpisodesVisible = _isEpisodesVisible.stateFlowWithDelay()
    fun changeEpisodesVisible() {
        _isEpisodesVisible.value = !_isEpisodesVisible.value
    }

    private val _character = MutableStateFlow<Character?>(null)
    val character = _character.stateFlowWithDelay().filterNotNull()
    private fun setCharacter(newCharacter: Character) {
        _character.value = newCharacter
    }

    suspend fun getCharacterById(id: Int) = repository.getCharacterById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .collect { response ->
            getDataOrSetError(response).collect {
                when (it) {
                    is Character -> {
                        setCharacter(it)
                    }
                    is CharacterDTO -> {
                        setCharacter(it.toCharacter())
                    }
                }
            }
        }

    suspend fun getLocationsMapByIds(locationsIdsList: Set<Int>) =
        getEntityMapListStateFlow(repository.getLocationsMapByIds(locationsIdsList))

    suspend fun getEpisodesMapByCharacterId(characterId: Int) =
        getEntityMapListStateFlow(repository.getEpisodesMapByCharacterId(characterId))

    fun navigateToLocation(view: View, locationId: Int) {
        val uri = Uri.parse("RickAndMortyApi://location/$locationId")
        view.findNavController().navigate(uri)
    }

    fun navigateToEpisode(view: View, episodeId: Int) {
        val uri = Uri.parse("RickAndMortyApi://episode/$episodeId")
        view.findNavController().navigate(uri)
    }
}