package ru.svoyakmartin.featureStatistic.data.dataSource

import ru.svoyakmartin.featureStatistic.data.model.Root

fun Root.toMap(): Map<String, Int> {
    with(data.stats) {
        return mapOf(
            "characters" to characters.info.count,
            "locations" to locations.info.count,
            "episodes" to episodes.info.count
        )
    }
}
