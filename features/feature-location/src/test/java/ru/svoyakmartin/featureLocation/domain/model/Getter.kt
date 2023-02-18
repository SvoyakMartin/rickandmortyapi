package ru.svoyakmartin.featureLocation.domain.model

import kotlinx.coroutines.flow.flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap
import ru.svoyakmartin.featureLocation.data.model.LocationDTO

object Getter {
    fun getLocation() = Location(
        id = 1,
        name = "Earth",
        type = "Planet",
        dimension = "Dimension C-137"
    )

    fun getLocationList() = listOf(
        Location(
            id = 1,
            name = "Earth",
            type = "Planet",
            dimension = "Dimension C-137"
        ), Location(
            id = 2,
            name = "Abadango",
            type = "Cluster",
            dimension = "unknown"
        )
    )

    fun getEntityMapList() = listOf(
        EntityMap(1, "Name 1"),
        EntityMap(2, "Name 2"),
        EntityMap(3, "Name 3")
    )

    fun getLocationDTOList() = listOf(
        LocationDTO(1," Earth", "Planet", "Dimension C-137", listOf()),
        LocationDTO(2," Abadango", "Cluster", "unknown", listOf())
    )

}