package ru.svoyakmartin.rickandmortyapi.data

import ru.svoyakmartin.rickandmortyapi.models.Character

fun getExampleDataList(): ArrayList<Character> {
    return arrayListOf(
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

fun getNewItem(index: Int): Character {
    return Character(index, "ADD_$index", "", "", "", "", "", "", "", listOf("EPISODE_$index"), "", "")
}