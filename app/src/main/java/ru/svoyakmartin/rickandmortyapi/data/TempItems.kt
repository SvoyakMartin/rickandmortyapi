package ru.svoyakmartin.rickandmortyapi.data

import ru.svoyakmartin.rickandmortyapi.models.Character

var items: List<Character> = getExampleDataList()

private fun getExampleDataList(): List<Character> {
    return listOf(
        Character(0, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
        Character(1, "Sleepy Gary", "", "", "", "", "", "", "", listOf("Total Rickall"), "", ""),
        Character(2, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", ""),
        Character(3, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
        Character(4, "Sleepy Gary", "", "", "", "", "", "", "", listOf("Total Rickall"), "", ""),
        Character(5, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", ""),
        Character(6, "Keara", "", "", "", "", "", "", "", listOf("The ABC's of Beth"), "", ""),
        Character(7, "Sleepy Gary", "", "", "", "", "", "", "", listOf("Total Rickall"), "", ""),
        Character(8, "Pussifer", "", "", "", "", "", "", "", listOf("Rickmurai Jack"), "", "")
    )
}

fun addNewItems(repeats: Int) {
    val tempItems = items.toMutableList()

    repeat(repeats) {
        tempItems.add(
            Character(
                items.size,
                "ADD_${items.size}",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                listOf("EPISODE_${items.size}"),
                "",
                ""
            )
        )
    }

    items = tempItems.toList()
}