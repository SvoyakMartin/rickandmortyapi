package ru.svoyakmartin.featureCharacter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.Lazy
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.viewModel.ViewModelFactory
import ru.svoyakmartin.coreUI.*
import ru.svoyakmartin.featureCharacter.databinding.FragmentCharactersBinding
import ru.svoyakmartin.featureCharacter.ui.CharactersAdapter
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentViewModel
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterListViewModel
import javax.inject.Inject

class CharacterListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    private val viewModel: CharacterListViewModel by viewModels { viewModelFactory.get() }
    private lateinit var binding: FragmentCharactersBinding
    private val adapter = CharactersAdapter()

    override fun onAttach(context: Context) {
        CharacterFeatureComponentDependenciesProvider.featureDependencies =
            findFeatureExternalDependencies()
        viewModel<CharacterFeatureComponentViewModel>().component.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initError(viewModel)
        initSearch()
        initRecyclerView()
        initRepeatOnLifecycle()
    }

    private fun initSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText ?: "")
                return false
            }

        })
    }

    private fun initRecyclerView() {
        with(binding) {
            charactersRecyclerView.init(adapter) {
                if (charactersRecyclerView.isVisibleLastItemWithMaximum(viewModel.charactersCount)) {
                    viewModel.fetchNextCharactersPartFromWeb()
                }
            }
        }
    }

    private fun initRepeatOnLifecycle() {
        launch {
            viewModel.allCharacters
                .collect { characterList ->
                    adapter.submitList(characterList)

                    if (characterList.isEmpty()) {
                        viewModel.fetchNextCharactersPartFromWeb()
                    }
                }
        }

        launch {
            viewModel.isLoading.collect { binding.loadingProgressBar.setVisibility(it) }
        }
    }
}