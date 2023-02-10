package ru.svoyakmartin.featureEpisode

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureEpisode.data.EpisodeRepositoryImpl
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeDetailsFragment
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeFeatureFlowFragment
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import javax.inject.Inject

class EpisodeFeatureApiImpl @Inject constructor(private val repository: EpisodeRepositoryImpl) :
    EpisodeFeatureApi {
    override fun getFlowFragment(): Fragment = EpisodeFeatureFlowFragment()

//    override fun getDetailFragment(characterId: Int): Fragment =
//        EpisodeDetailsFragment.newInstance(characterId)

//    override fun getLocationMapById(locationId: Int): Flow<Map<String, Int>> =
//    repository.getLocationMapById(locationId)

}