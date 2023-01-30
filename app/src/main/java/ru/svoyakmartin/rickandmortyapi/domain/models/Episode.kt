package ru.svoyakmartin.rickandmortyapi.domain.models

data class Episode(
    val id: Int,// The id of the episode.
    val name: String,// The name of the episode.
    val air_date: String,//	The air date of the episode.
    val episode: String,//	The code of the episode.
    val characters: List<String>,//	List of characters who have been seen in the episode.
    val url: String,// Link to the episode's own endpoint.
    val created: String// Time at which the episode was created in the database.
)