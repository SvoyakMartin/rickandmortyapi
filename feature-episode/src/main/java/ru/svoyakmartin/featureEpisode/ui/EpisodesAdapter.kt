package ru.svoyakmartin.featureEpisode.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureEpisode.databinding.EpisodeItemBinding
import ru.svoyakmartin.featureEpisode.domain.model.Episode

class EpisodesAdapter(
    private val clickListener: EpisodeClickListener
) : RecyclerView.Adapter<EpisodesViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
        /* Called to check whether two objects represent the same item.
        For example, if your items have unique ids, this method should check their id equality. */
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
            oldItem.id == newItem.id

        /* Called to check whether two items have the same data.
        This information is used to detect if the contents of an item have changed. */
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = EpisodeItemBinding.inflate(layoutInflater, parent, false)

        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newItems: List<Episode>?) {
        differ.submitList(newItems)
    }
}

interface EpisodeClickListener {
    fun onEpisodeClick(episode: Episode)
}