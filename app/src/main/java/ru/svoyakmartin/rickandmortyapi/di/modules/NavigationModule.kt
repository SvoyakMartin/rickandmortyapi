package ru.svoyakmartin.rickandmortyapi.di.modules

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenNavSource
import ru.svoyakmartin.featureCharacter.R as characterR
import ru.svoyakmartin.featureEpisode.R as episodeR
import ru.svoyakmartin.featureLocation.R as locationR
import ru.svoyakmartin.featureSettings.R as settingsR

@Module
object NavigationModule {

    @Provides
    fun provideHomeScreenNavSource(): HomeScreenNavSource {
        return object : HomeScreenNavSource {
            @get:NavigationRes
            override val characterGraph = characterR.navigation.character_graph

            @get:NavigationRes
            override val locationGraph = locationR.navigation.location_graph

            @get:NavigationRes
            override val episodeGraph = episodeR.navigation.episode_graph

            @get:NavigationRes
            override val settingsGraph = settingsR.navigation.settings_graph

            @get:IdRes
            override val characterGraphId = characterR.id.characters_item
        }
    }
}