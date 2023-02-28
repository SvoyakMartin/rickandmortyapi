package ru.svoyakmartin.featureEpisodeApi

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap

interface EpisodeFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(episodeId: Int): Fragment
    fun getEpisodeMapByIds(episodeIdsList: Set<Int>): Flow<Any>
}