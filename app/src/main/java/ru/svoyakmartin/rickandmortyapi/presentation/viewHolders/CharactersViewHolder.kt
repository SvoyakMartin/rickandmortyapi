package ru.svoyakmartin.rickandmortyapi.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.databinding.CharacterItemBinding
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.CharacterClickListener

class CharactersViewHolder(private val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Character, clickListener: CharacterClickListener){
        with(binding){
            characterName.text = item.name
            characterGender.text = item.gender.name
            characterSpecies.text = item.species

            Glide.with(characterImage.context)
                .load(item.image)
                .into(characterImage)

            itemView.setOnClickListener{
                clickListener.onCharacterClick(item)
            }
        }
    }
}