package ru.svoyakmartin.featureCore.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoDTO(
    @SerialName("count")
    var count: Int,
    @SerialName("pages")
    var pages: Int,
    @SerialName("next")
    var next: String?,
    @SerialName("prev")
    var prev: String?
)