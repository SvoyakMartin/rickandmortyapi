package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.models.Character

class CharactersAdapter(
    private val clickListener: CharactersClickListener
) : RecyclerView.Adapter<CharactersViewHolder>() {
    private var items = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemViewBinding.inflate(layoutInflater, parent, false)

        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(items: ArrayList<Character>) {
        this.items = items

        notifyDataSetChanged()
    }

    fun addNewItems(repeats: Int){
        repeat(repeats){
            items.add(Character(0, "ADD", "", "", "", "", "", "", "", listOf("EPISODE"), "", ""))
        }
        notifyItemRangeChanged(items.size + 1, items.size + repeats)
    }
}

interface CharactersClickListener {
    fun onCharacterClick(character: Character)
}