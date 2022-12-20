package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.data.items
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.models.Character

class CharactersAdapter(
    private val clickListener: CharactersClickListener
) : RecyclerView.Adapter<CharactersViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    init{
        differ.submitList(items)
    }

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

    fun setList(newItems: List<Character>) {
        items = newItems
        differ.submitList(items)
    }

    fun currentList(): List<Character> {
        return differ.currentList
    }
}

interface CharactersClickListener {
    fun onCharacterClick(character: Character)
}