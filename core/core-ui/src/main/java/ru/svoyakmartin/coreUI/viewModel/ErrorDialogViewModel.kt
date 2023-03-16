package ru.svoyakmartin.coreUI.viewModel

import androidx.lifecycle.ViewModel

class ErrorDialogViewModel: ViewModel() {
    var clickListener: (() -> Unit)? = null
}