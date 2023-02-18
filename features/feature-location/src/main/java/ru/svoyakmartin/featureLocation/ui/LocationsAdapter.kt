package ru.svoyakmartin.featureLocation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureLocation.databinding.LocationItemBinding
import ru.svoyakmartin.featureLocation.domain.model.Location

class LocationsAdapter : RecyclerView.Adapter<LocationsViewHolder>() {
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
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newItems: List<Location>?) {
        differ.submitList(newItems)
    }
}