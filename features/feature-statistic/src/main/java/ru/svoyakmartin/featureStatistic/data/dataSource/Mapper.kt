package ru.svoyakmartin.featureStatistic.data.dataSource

import ru.svoyakmartin.featureStatistic.CHARACTERS_FIELD
import ru.svoyakmartin.featureStatistic.EPISODES_FIELD
import ru.svoyakmartin.featureStatistic.LOCATIONS_FIELD
import ru.svoyakmartin.featureStatistic.data.model.Root

fun Root.toMap(): Map<String, Int> {
    with(data.stats) {
        return mapOf(
            CHARACTERS_FIELD to characters.info.count,
            LOCATIONS_FIELD to locations.info.count,
            EPISODES_FIELD to episodes.info.count
        )
    }
}
