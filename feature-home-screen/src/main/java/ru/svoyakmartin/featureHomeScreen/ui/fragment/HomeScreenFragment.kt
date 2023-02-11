package ru.svoyakmartin.featureHomeScreen.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import ru.svoyakmartin.core.shortClassName
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.navigator.NavigatorHolder
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureHomeScreen.R
import ru.svoyakmartin.featureHomeScreen.databinding.FragmentHomeBinding
import ru.svoyakmartin.featureHomeScreen.ui.viewModel.HomeScreenComponentViewModel
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject
import kotlin.system.exitProcess

class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var backPressed: Long = 0
//    private val callback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            backPressed()
//        }
//    }

    @Inject
    lateinit var flowRouter: FlowRouter

    @Inject
    lateinit var characterFeatureApi: CharacterFeatureApi

    private val characterFragment by lazy {
        characterFeatureApi.getFlowFragment()
    }

    @Inject
    lateinit var locationFeatureApi: LocationFeatureApi

    private val locationFragment by lazy {
        locationFeatureApi.getFlowFragment()
    }

    @Inject
    lateinit var episodeFeatureApi: EpisodeFeatureApi

    private val episodeFragment by lazy {
        episodeFeatureApi.getFlowFragment()
    }

    @Inject
    lateinit var settingsFeatureApi: SettingsFeatureApi
    private val settingsFragment by lazy {
        settingsFeatureApi.getFlowFragment()
    }

//    override fun onResume() {
//        super.onResume()
//        callback.isEnabled = true
//    }
//
//    override fun onPause() {
//        super.onPause()
//        callback.isEnabled = false
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel<HomeScreenComponentViewModel>().component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        if (childFragmentManager.fragments.isEmpty()) {
            setFragment(characterFragment, CharacterFeatureApi.BACKSTACK_KEY)
        } else {
            setFragmentFromBottomMenu(binding.startFragmentBottomNavigation.selectedItemId)
        }
    }

    private fun initViews() {
//        with(requireActivity()) {
//            onBackPressedDispatcher.addCallback(this, callback)
//        }

        with(binding) {
            startFragmentBottomNavigation.apply {
                setOnItemSelectedListener { menuItem ->
                    setFragmentFromBottomMenu(menuItem.itemId)

                    true
                }
            }
        }
    }

    private fun setFragmentFromBottomMenu(menuItemId: Int?) {
        if (binding.startFragmentBottomNavigation.selectedItemId != menuItemId) {

            when (menuItemId) {
                R.id.locations_item -> setFragment(locationFragment, LocationFeatureApi.BACKSTACK_KEY)
                R.id.episodes_item -> setFragment(episodeFragment, EpisodeFeatureApi.BACKSTACK_KEY)
                R.id.settings_item -> setFragment(settingsFragment, SettingsFeatureApi.BACKSTACK_KEY)
                //R.id.characters_item ->
                else -> setFragment(characterFragment, CharacterFeatureApi.BACKSTACK_KEY)
            }
        }
    }

    private fun setFragment(fragment: Fragment, backStack: String) {
        val fragmentTag = fragment.shortClassName

        with(childFragmentManager) {
            var existingFragment = findFragmentByTag(fragmentTag)

            commit {
                setReorderingAllowed(true)
                fragments.forEach { hide(it) }

                if (existingFragment == null) {
                    add(R.id.start_fragment_container, fragment, fragmentTag)
                    setPrimaryNavigationFragment(fragment)
                    existingFragment = fragment
                }

                show(existingFragment!!)
            }
        }
    }

    private fun backPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            requireActivity().apply {
                finishAffinity()
                exitProcess(0)
            }
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.back_pressed_toast_text),
                Toast.LENGTH_SHORT
            ).show()

            backPressed = System.currentTimeMillis()
        }
    }
}