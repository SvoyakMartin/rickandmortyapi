package ru.svoyakmartin.rickandmortyapi.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.databinding.LocationItemBinding
import ru.svoyakmartin.rickandmortyapi.presentation.viewHolders.LocationsViewHolder

class LocationsAdapter(
    private val clickListener: LocationClickListener
) : RecyclerView.Adapter<LocationsViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Location>() {
        /* Called to check whether two objects represent the same item.
        For example, if your items have unique ids, this method should check their id equality. */
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean =
            oldItem.id == newItem.id

        /* Called to check whether two items have the same data.
        This information is used to detect if the contents of an item have changed. */
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(layoutInflater, parent, false)

        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newItems: List<Location>?) {
        differ.submitList(newItems)
    }
}

interface LocationClickListener {
    fun onLocationClick(location: Location)
}