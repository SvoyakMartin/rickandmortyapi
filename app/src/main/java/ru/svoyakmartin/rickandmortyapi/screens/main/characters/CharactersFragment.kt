package ru.svoyakmartin.rickandmortyapi.screens.main.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.data.addNewItems
import ru.svoyakmartin.rickandmortyapi.data.items
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterBinding
import ru.svoyakmartin.rickandmortyapi.models.Character
import ru.svoyakmartin.rickandmortyapi.screens.main.locations.LocationsFragment


class CharactersFragment : Fragment(), CharactersClickListener {
//    companion object {
//        const val ANIM_START = 0f
//        const val ANIM_STEP = 100f
//        const val ONE_ANIMATOR_DURATION = 2000L
//        const val ONE_ANIMATOR_REPEATS = 3
//        const val ROTATE_ANIMATOR_DURATION = 5000L
//        const val TRANSLATION_ANIMATOR_DURATION = 3000L
//    }

    private lateinit var binding: FragmentCharacterBinding
    private val adapter = CharactersAdapter(this).apply {
        submitList(items)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            charactersRecyclerView.adapter = adapter
            charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if ((charactersRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == adapter.itemCount - 1) {
                        addNewItems(3)
                        adapter.submitList(items)
                    }
                }
            })

            buttonLightTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            buttonDarkTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            buttonAutoTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            buttonShuffle.setOnClickListener {
                items = adapter.currentList().toMutableList().apply {
                    shuffle()
                }.toList()
                adapter.submitList(items)
            }

//            val anim = AnimationUtils.loadAnimation(context, R.anim.button_start_anim).apply {
//                interpolator = BounceInterpolator()
//            }
//
//            val animator = ValueAnimator.ofFloat().apply {
//                interpolator = AccelerateDecelerateInterpolator()
//                duration = CharactersFragment.ONE_ANIMATOR_DURATION
//                repeatCount = CharactersFragment.ONE_ANIMATOR_REPEATS
//            }
//
//            val animatorSet = AnimatorSet()
//
//            with(binding) {
//                val animator_r =
//                    ObjectAnimator.ofFloat(buttonAnim, View.ROTATION,
//                        CharactersFragment.ANIM_START, 120f, 80f, 90f)
//                        .apply {
//                            interpolator = AccelerateDecelerateInterpolator()
//                            duration = CharactersFragment.ROTATE_ANIMATOR_DURATION
//                        }
//
//                val animator_t =
//                    ObjectAnimator.ofFloat(buttonAnim, View.TRANSLATION_Y,
//                        CharactersFragment.ANIM_START, 140f).apply {
//                        interpolator = BounceInterpolator()
//                        duration = CharactersFragment.TRANSLATION_ANIMATOR_DURATION
//                    }
//
//                buttonAnimTwo.setOnClickListener {
//                    if (!animatorSet.isRunning) {
//                        animatorSet.apply {
//                            playTogether(animator_r, animator_t)
//                            doOnEnd {
//                                buttonAnim.apply {
//                                    translationY = CharactersFragment.ANIM_START
//                                    rotation = CharactersFragment.ANIM_START
//                                }
//                            }
//                            start()
//                        }
//                    }
//                }
//
//                buttonAnim.setOnClickListener {
//                    if (!animator.isRunning) {
//                        val currentY = it.y
//                        animator.apply {
//                            setFloatValues(
//                                CharactersFragment.ANIM_START,
//                                CharactersFragment.ANIM_STEP,
//                                CharactersFragment.ANIM_START, -CharactersFragment.ANIM_STEP,
//                                CharactersFragment.ANIM_START
//                            )
//                            addUpdateListener {
//                                buttonAnim.y = currentY + animatedValue as Float
//                            }
//                            doOnEnd { buttonAnimTwo.visibility = View.VISIBLE }
//                        }.start()
//                    }
//            lastSeenAnimView.apply {
//                setLocation("Citadel of Ricks")
//                setAliveStatus(LastSeenAnimView.AliveStatus.DEAD)
//            }
        }
    }

    private fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onCharacterClick(character: Character) {
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("UserStack")
            .replace(R.id.fragmentContainerView, LocationsFragment())
            .commit()
    }
}