package ru.svoyakmartin.featureLocation.data.dataSource

import ru.svoyakmartin.featureCore.util.getIdsListFromUrlList
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.data.model.LocationDTO

fun LocationDTO.toLocation() = Location(
    id,
    name,
    type,
    dimension
)

fun LocationDTO.getCharactersIds(): Set<Int> {
    return getIdsListFromUrlList(residents)
}