package ru.svoyakmartin.featureCore.util

fun getIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

fun getIdsListFromUrlList(urlList: List<String>): List<Int> {
    val ids = arrayListOf<Int>()
    urlList.forEach {
        ids.add(getIdFromUrl(it))
    }

    return ids
}