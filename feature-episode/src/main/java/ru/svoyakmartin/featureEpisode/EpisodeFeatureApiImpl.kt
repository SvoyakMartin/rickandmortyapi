package ru.svoyakmartin.featureEpisode

import ru.svoyakmartin.featureEpisode.data.ExportRepositoryImpl
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeDetailsFragment
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeFeatureFlowFragment
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import javax.inject.Inject

class EpisodeFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    EpisodeFeatureApi {
    override fun getFlowFragment() = EpisodeFeatureFlowFragment()

    override fun getDetailFragment(episodeId: Int) =
        EpisodeDetailsFragment.newInstance(episodeId)

    override fun getEpisodeMapByIds(episodeIdsList: List<Int>) =
        repository.getEpisodeMapByIds(episodeIdsList)
}