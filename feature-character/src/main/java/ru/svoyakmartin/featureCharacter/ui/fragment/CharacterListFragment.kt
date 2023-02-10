package ru.svoyakmartin.featureCharacter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureCharacter.databinding.FragmentCharactersBinding
import ru.svoyakmartin.featureCharacter.ui.CharactersAdapter
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterListViewModel
import javax.inject.Inject


class CharacterListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharacterListViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharactersBinding
    private val adapter by lazy { CharactersAdapter(viewModel) }

    override fun onAttach(context: Context) {
        viewModel<CharacterFeatureComponentViewModel>().component.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.allCharacters.collect {
                            if (it.isNullOrEmpty()) {
                                loadNextPart()
                            } else {
                                adapter.submitList(it)
                                viewModel.setIsLoading(false)
                            }
                        }
                    }

                    launch {
                        viewModel.isLoading.collect { showHideLoadingView(it) }
                    }
                }
            }

            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition() == adapter.itemCount - 1
                    ) {
                        loadNextPart()
                    }
                }
            })
        }
    }

    private fun showHideLoadingView(visibility: Boolean) {
        binding.loadingProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun loadNextPart() {
        with(viewModel) {
            if (!isLoading.value) {
                apply {
                    setIsLoading(true)
                    fetchNextCharactersPartFromWeb()
                }
            }
        }
    }
}