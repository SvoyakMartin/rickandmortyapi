package ru.svoyakmartin.coreMvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

inline fun <reified VM : ViewModel> Fragment.viewModel(): VM {
    return ViewModelProvider(this).get()
}
