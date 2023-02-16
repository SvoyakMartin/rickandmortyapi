package ru.svoyakmartin.featureCharacter.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureCharacter.databinding.CharacterItemBinding
import ru.svoyakmartin.featureCharacter.domain.model.Character

class CharactersAdapter : RecyclerView.Adapter<CharacterItemViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(layoutInflater, parent, false)

        return CharacterItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newItems: List<Character>?) {
        differ.submitList(newItems)
    }
}