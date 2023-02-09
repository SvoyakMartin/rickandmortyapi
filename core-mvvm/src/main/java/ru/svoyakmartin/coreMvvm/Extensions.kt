package ru.svoyakmartin.coreMvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import ru.svoyakmartin.coreFlow.ui.FlowFragment

inline fun <reified VM : ViewModel> Fragment.viewModel(): VM {
    val storeOwner: ViewModelStoreOwner = when {
        this is FlowFragment -> this
        this.parentFragment is FlowFragment -> this.parentFragment as ViewModelStoreOwner
        else -> error("View model store owner not found")
    }
    return ViewModelProvider(storeOwner).get()
}