package ru.svoyakmartin.featureLocation.ui

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.featureLocation.databinding.LocationItemBinding
import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.ui.fragment.LocationListFragmentDirections

class LocationsViewHolder(private val binding: LocationItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Location) {
        with(binding) {
            locationName.text = item.name
            locationType.text = item.type
            locationDimension.text = item.dimension

            itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    LocationListFragmentDirections.actionLocationListFragmentToLocationDetailsFragment(
                        item.id
                    )
                )
            }
        }
    }
}