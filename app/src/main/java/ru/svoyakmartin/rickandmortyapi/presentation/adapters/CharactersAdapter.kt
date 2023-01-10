package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.domain.models.Character
import ru.svoyakmartin.rickandmortyapi.presentation.viewHolders.CharactersViewHolder

class CharactersAdapter(
    private val clickListener: CharactersClickListener
) : RecyclerView.Adapter<CharactersViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
        /* Called to check whether two objects represent the same item.
        For example, if your items have unique ids, this method should check their id equality. */
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem.id == newItem.id

        /* Called to check whether two items have the same data.
        This information is used to detect if the contents of an item have changed. */
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem == newItem
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

    fun submitList(newItems: List<Character>?) {
        differ.submitList(newItems)
    }
}

interface CharactersClickListener {
    fun onCharacterClick(character: Character)
}