package ru.svoyakmartin.featureLocation.ui

import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureLocation.databinding.LocationItemBinding
import ru.svoyakmartin.featureLocation.domain.model.Location

class LocationsViewHolder(private val binding: LocationItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Location, clickListener: LocationClickListener){
        with(binding){
            locationName.text = item.name
            locationType.text = item.type
            locationDimension.text = item.dimension

            itemView.setOnClickListener{
                clickListener.onLocationClick(item.id)
            }
        }
    }
}