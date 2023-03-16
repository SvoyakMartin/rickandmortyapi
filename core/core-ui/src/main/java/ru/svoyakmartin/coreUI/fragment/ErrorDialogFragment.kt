package ru.svoyakmartin.coreUI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.svoyakmartin.coreUI.R
import ru.svoyakmartin.coreUI.databinding.FragmentErrorDialogBinding
import ru.svoyakmartin.coreUI.viewModel.ErrorDialogViewModel

class ErrorDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentErrorDialogBinding
    private val viewModel by viewModels<ErrorDialogViewModel>()
    private var clickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorDialogBinding.inflate(inflater, container, false)

        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val title = arguments?.getString("title")
            errorTitle.text = when (title) {
                null -> getString(R.string.default_error_title)
                else -> title
            }

            val message = arguments?.getString("message")
            errorMessage.text = when (message) {
                null -> getString(R.string.default_error_message)
                else -> message
            }


            if (savedInstanceState == null) {
                viewModel.clickListener = clickListener
            }
            closeErrorButton.setOnClickListener{
                viewModel.clickListener?.invoke()
                dismiss()
            }
        }
    }

    fun getInstance(
        title: String?, message: String?, listener: (() -> Unit)?
    ): ErrorDialogFragment {
        val fragment = ErrorDialogFragment().apply {
            arguments = bundleOf("title" to title, "message" to message)
        }
        fragment.clickListener = listener

        return fragment
    }
}