package ru.svoyakmartin.featureCharacter.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.svoyakmartin.featureCharacter.databinding.CharacterItemBinding
import ru.svoyakmartin.featureCharacter.domain.model.Character

class CharacterItemViewHolder(private val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Character, clickListener: CharacterClickListener){
        with(binding){
            characterName.text = item.name
            characterGender.text = item.gender.name
            characterSpecies.text = item.species

            Glide.with(characterImage.context)
                .load(item.image)
                .into(characterImage)

            itemView.setOnClickListener{
                clickListener.onCharacterClick(item.id)
            }
        }
    }
}