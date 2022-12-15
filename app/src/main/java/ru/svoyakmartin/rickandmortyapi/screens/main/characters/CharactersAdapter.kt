package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.models.Character

class CharactersAdapter(
    private val clickListener: CharactersClickListener
) : RecyclerView.Adapter<CharactersViewHolder>() {
    private lateinit var items: MutableList<Character>

    private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem.id == newItem.id
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemViewBinding.inflate(layoutInflater, parent, false)

        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setList(newItems: MutableList<Character>) {
        items = newItems
        differ.submitList(items)
    }

    fun addNewItems(repeats: Int) {
        repeat(repeats) {
            items.add(
                Character(
                    items.size,
                    "ADD_" + items.size,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    listOf("EPISODE_" + items.size),
                    "",
                    ""
                )
            )
        }
        differ.submitList(items)
        notifyItemRangeChanged(items.size + 1, items.size + repeats)
    }

    fun currentList(): List<Character> {
        return differ.currentList
    }

    fun shuffleData(items: MutableList<Character>) {
        differ.submitList(items)
    }
}

interface CharactersClickListener {
    fun onCharacterClick(character: Character)
}