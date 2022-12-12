package ru.svoyakmartin.rickandmortyapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentLocationBinding

class LocationsFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonSaveData.setOnClickListener {
                var isEmpty = false
                val nameEditText = nameEdit.text.toString()
                val ageEditText = ageEdit.text.toString()
                val addressEditText = addressEdit.text.toString()
                val emailEditText = emailEdit.text.toString()

                if (nameEditText.isEmpty()) {
                    isEmpty = true
                    name.error = getString(R.string.name_empty_error)
                }
                if (ageEditText.isEmpty()) {
                    age.error = getString(R.string.age_empty_error)
                    isEmpty = true
                }
                if (addressEditText.isEmpty()) {
                    address.error = getString(R.string.address_empty_error)
                    isEmpty = true
                }
                if (emailEditText.isEmpty()) {
                    email.error = getString(R.string.email_empty_error)
                    isEmpty = true
                }

                if (!isEmpty) {
                    val bundle = bundleOf(
                        "name" to nameEditText,
                        "age" to ageEditText,
                        "address" to addressEditText,
                        "email" to emailEditText
                    )
                    val episodesFragment = EpisodesFragment().apply {
                        arguments = bundle
                    }
                    parentFragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("UserStack")
                        .replace(R.id.fragmentContainerView, episodesFragment)
                        .commit()
                }
            }
        }
    }
}