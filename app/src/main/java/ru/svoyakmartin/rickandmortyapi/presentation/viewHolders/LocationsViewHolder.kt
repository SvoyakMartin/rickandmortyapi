package ru.svoyakmartin.rickandmortyapi.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location
import ru.svoyakmartin.rickandmortyapi.databinding.LocationItemBinding
import ru.svoyakmartin.rickandmortyapi.presentation.adapters.LocationClickListener

class LocationsViewHolder(private val binding: LocationItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Location, clickListener: LocationClickListener){
        with(binding){
            locationName.text = item.name
            locationType.text = item.type
            locationDimension.text = item.dimension

            itemView.setOnClickListener{
                clickListener.onLocationClick(item)
            }
        }
    }
}