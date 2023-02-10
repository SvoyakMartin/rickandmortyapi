package ru.svoyakmartin.featureEpisode.ui

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureEpisode.databinding.EpisodeItemBinding
import ru.svoyakmartin.featureEpisode.domain.model.Episode

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