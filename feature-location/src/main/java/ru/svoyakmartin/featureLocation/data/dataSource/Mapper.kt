package ru.svoyakmartin.featureLocation.data.dataSource

import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.data.model.LocationDTO

fun LocationDTO.toLocation() = Location(
    id,
    name,
    type,
    dimension
)

fun LocationDTO.getCharactersIds(): List<Int> {
    return getIdsListFromUrlList(residents)
}

fun getIdsListFromUrlList(urlList: List<String>): List<Int> {
    val ids = arrayListOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}