package ru.svoyakmartin.featureEpisode.ui

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureEpisode.databinding.EpisodeItemBinding
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeListFragmentDirections

class EpisodesViewHolder(private val binding: EpisodeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Episode) {
        with(binding) {
            episodeName.text = item.name
            episodeCode.text = item.episode
            episodeData.text = item.airDate

            itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeDetailsFragment(
                        item.id
                    )
                )
            }
        }
    }
}