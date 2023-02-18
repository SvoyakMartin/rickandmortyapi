package ru.svoyakmartin.featureCore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InfoDTO(
    var count: Int,
    var pages: Int,
    var next: String?,
    var prev: String?
)