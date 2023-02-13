package ru.svoyakmartin.featureHomeScreen.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.svoyakmartin.core.shortClassName
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import ru.svoyakmartin.featureEpisodeApi.EpisodeFeatureApi
import ru.svoyakmartin.featureHomeScreen.R
import ru.svoyakmartin.featureHomeScreen.databinding.FragmentHomeBinding
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenNavSource
import ru.svoyakmartin.featureHomeScreen.ui.viewModel.HomeScreenComponentDependenciesProvider
import ru.svoyakmartin.featureHomeScreen.ui.viewModel.HomeScreenComponentViewModel
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import ru.svoyakmartin.featureSettingsApi.SettingsFeatureApi
import javax.inject.Inject
import kotlin.system.exitProcess

class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    @Inject
    lateinit var homeScreenNavSource: HomeScreenNavSource
//    private var backPressed: Long = 0
//    private val callback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            backPressed()
//        }
//    }

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
        HomeScreenComponentDependenciesProvider.dependencies = findFeatureExternalDependencies()
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

        navController =
            (childFragmentManager.findFragmentById(R.id.home_fragment_container) as NavHostFragment).navController
        binding.homeFragmentBottomNavigation.setupWithNavController(navController)

//        if (childFragmentManager.fragments.isEmpty()) {
//            setFragment(characterFragment, CharacterFeatureApi.BACKSTACK_KEY)
//        } else {
//            setFragmentFromBottomMenu(binding.startFragmentBottomNavigation.selectedItemId)
//        }
    }

    private fun initViews() {
//        with(requireActivity()) {
//            onBackPressedDispatcher.addCallback(this, callback)
//        }
    }

//    private fun backPressed() {
//        if (backPressed + 2000 > System.currentTimeMillis()) {
//            requireActivity().apply {
//                finishAffinity()
//                exitProcess(0)
//            }
//        } else {
//            Toast.makeText(
//                requireActivity(),
//                getString(R.string.back_pressed_toast_text),
//                Toast.LENGTH_SHORT
//            ).show()
//
//            backPressed = System.currentTimeMillis()
//        }
//    }
}