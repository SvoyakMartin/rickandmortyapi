package ru.svoyakmartin.rickandmortyapi.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentEpisodeBinding

class EpisodesFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nameEdit.setText(arguments?.getString("name"))
            ageEdit.setText(arguments?.getString("age"))
            addressEdit.setText(arguments?.getString("address"))
            emailEdit.setText(arguments?.getString("email"))


            buttonSendData.setOnClickListener {
                val i = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "${nameEdit.text}\n" +
                                "${ageEdit.text}\n" +
                                "${addressEdit.text}\n" +
                                "${emailEdit.text}\n"
                    )
                }

                if (i.resolveActivity(requireActivity().packageManager) != null) {
                    val shareIntent = Intent.createChooser(i, null)
                    startActivity(shareIntent)
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.no_send_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}