package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.svoyakmartin.rickandmortyapi.data.getExampleDataList
import ru.svoyakmartin.rickandmortyapi.data.getNewItem
import ru.svoyakmartin.rickandmortyapi.models.Character

class CharactersViewModel : ViewModel() {
    private val _items = MutableLiveData<ArrayList<Character>>().apply {
        value = getExampleDataList()
    }
    val items: LiveData<List<Character>> = Transformations.map(_items) {
        it as List<Character>
    }

    fun addNewItems(count: Int) {
        _items.value = _items.value?.apply {
            repeat(count) {
                add(getNewItem(size))
            }
        }
    }

    fun shuffleItems() {
        _items.value = _items.value?.apply { shuffle() }
    }
}