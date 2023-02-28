package ru.svoyakmartin.featureCore.util

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel
import ru.svoyakmartin.featureCore.domain.model.EntityMap

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun getIdsListFromUrlList(urlList: List<String>): Set<Int> {
    val ids = mutableSetOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}

suspend fun BaseLoadingErrorViewModel.getEntityMapListStateFlow(flow: Flow<Any>) = flow {
    flow.flowOn(Dispatchers.IO)
        .conflate()
        .collect { response ->
            getDataOrSetError(response)
                .filter { it is List<*> }
                .collect { emit(it as List<EntityMap>) }
        }
}
    .stateIn(viewModelScope)
    .stateFlowWithDelay()