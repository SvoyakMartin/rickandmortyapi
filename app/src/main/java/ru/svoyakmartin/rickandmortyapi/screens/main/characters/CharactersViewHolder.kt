package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemViewBinding
import ru.svoyakmartin.rickandmortyapi.models.Character

class CharactersViewHolder(private val binding: CharacterItemViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Character, clickListener: CharactersClickListener){
        with(binding){
            characterImage.setImageResource(R.drawable.ic_launcher_foreground)
            characterName.text = item.name
            episodeName.text = item.episode[0]

            itemView.setOnClickListener{
                clickListener.onCharacterClick(item)
            }
        }
    }
}