package ru.svoyakmartin.featureEpisode.ui.viewModel

import android.net.Uri
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureCore.util.getEntityMapListStateFlow
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import ru.svoyakmartin.featureEpisode.data.dataSource.toEpisode
import ru.svoyakmartin.featureEpisode.data.model.EpisodeDTO
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(private val repository: EpisodeRepositoryImpl) :
    BaseLoadingErrorViewModel() {
    private val _isCharactersVisible = MutableStateFlow(false)
    val isCharactersVisible = _isCharactersVisible.stateFlowWithDelay()
    fun changeCharactersVisible() {
        _isCharactersVisible.value = !_isCharactersVisible.value
    }

    private val _episode = MutableStateFlow<Episode?>(null)
    val episode = _episode.stateFlowWithDelay().filterNotNull()
    private fun setEpisode(newEpisode: Episode) {
        _episode.value = newEpisode
    }

    suspend fun getEpisodeById(id: Int) = repository.getEpisodeById(id)
        .flowOn(Dispatchers.IO)
        .conflate()
        .collect { response ->
            getDataOrSetError(response).collect {
                when (it) {
                    is Episode -> {
                        setEpisode(it)
                    }
                    is EpisodeDTO -> {
                        setEpisode(it.toEpisode())
                    }
                }
            }
        }

    suspend fun getCharacterMapByEpisodeId(id: Int) =
        getEntityMapListStateFlow(repository.getCharacterMapByEpisodeId(id))

    fun navigateToCharacter(view: View, characterId: Int) {
        val uri = Uri.parse("RickAndMortyApi://character/$characterId")
        view.findNavController().navigate(uri)
    }
}