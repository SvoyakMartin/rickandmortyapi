package ru.svoyakmartin.featureHomeScreen.di

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes

interface HomeScreenNavSource {
    @get:NavigationRes
    val characterGraph: Int

    @get:NavigationRes
    val locationGraph: Int

    @get:NavigationRes
    val episodeGraph: Int

    @get:NavigationRes
    val settingsGraph: Int

    @get:IdRes
    val characterGraphId: Int
}