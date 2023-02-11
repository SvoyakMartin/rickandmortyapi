package ru.svoyakmartin.featureEpisodeApi

import androidx.fragment.app.Fragment

interface EpisodeFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(episodeId: Int): Fragment

    companion object {
        const val BACKSTACK_KEY = "episodes"
    }
}