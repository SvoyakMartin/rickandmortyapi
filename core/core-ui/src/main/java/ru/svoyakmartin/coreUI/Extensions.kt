package ru.svoyakmartin.coreUI

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreUI.fragment.ErrorDialogFragment
import ru.svoyakmartin.coreUI.viewModel.BaseLoadingErrorViewModel

inline fun <reified VM : ViewModel> Fragment.viewModel(): VM {
    return ViewModelProvider(this).get()
}

fun View.setVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun RecyclerView.isVisibleLastItemWithMaximum(maxCount: Int): Boolean {
    val adapterItemCount = adapter?.itemCount ?: 0

    return adapterItemCount < maxCount && (layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == (adapterItemCount) - 1
}

fun RecyclerView.init(adapter: RecyclerView.Adapter<*>, onScrolledListener: () -> Unit) {
    this.adapter = adapter

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScrolledListener.invoke()
        }
    }

    this.addOnScrollListener(scrollListener)
}

fun Fragment.launch(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { block.invoke(this) }
        }
    }
}

fun Fragment.showErrorDialog(error: Map<String, Any>) {
    val title = error["title"]
    val message = error["message"]
    val listener = error["listener"]

    val fragment = ErrorDialogFragment().getInstance(
        title.toString(),
        message.toString(),
        listener as (() -> Unit)?
    )

    fragment.show(childFragmentManager, "errorDialog")
}

fun Fragment.initError(viewModel: BaseLoadingErrorViewModel) {
    launch {
        viewModel.error
            .filter { !viewModel.isErrorShowing && it.isNotEmpty() }
            .conflate()
            .collect {
                showErrorDialog(it)
                viewModel.isErrorShowing = true
            }
    }
}
