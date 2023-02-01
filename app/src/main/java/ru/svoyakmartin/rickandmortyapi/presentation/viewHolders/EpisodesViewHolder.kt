package ru.svoyakmartin.rickandmortyapi.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.databinding.EpisodeItemBinding
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.EpisodeClickListener

class EpisodesViewHolder(private val binding: EpisodeItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Episode, clickListener: EpisodeClickListener){
        with(binding){
            episodeName.text = item.name
            episodeCode.text = item.episode
            episodeData.text = item.air_date

            itemView.setOnClickListener{
                clickListener.onEpisodeClick(item)
            }
        }
    }
}