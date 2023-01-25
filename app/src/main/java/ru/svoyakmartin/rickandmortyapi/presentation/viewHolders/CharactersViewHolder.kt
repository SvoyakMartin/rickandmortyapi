package ru.svoyakmartin.rickandmortyapi.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.screens.main.characters.CharactersClickListener

class CharactersViewHolder(private val binding: CharacterItemViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Character, clickListener: CharactersClickListener){
        with(binding){
            characterImage.setImageResource(R.drawable.ic_launcher_foreground)
            characterName.text = item.name
            episodeName.text = item.id.toString()

            itemView.setOnClickListener{
                clickListener.onCharacterClick(item)
            }
        }
    }
}