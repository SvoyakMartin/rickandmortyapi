package ru.svoyakmartin.rickandmortyapi

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import ru.svoyakmartin.rickandmortyapi.databinding.FragmentCharacterBinding

class CharactersFragment : Fragment() {
    companion object {
        const val ANIM_START = 0f
        const val ANIM_STEP = 100f
        const val ONE_ANIMATOR_DURATION = 2000L
        const val ONE_ANIMATOR_REPEATS = 3
        const val ROTATE_ANIMATOR_DURATION = 5000L
        const val TRANSLATION_ANIMATOR_DURATION = 3000L
    }

    private lateinit var binding: FragmentCharacterBinding

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
        val anim = AnimationUtils.loadAnimation(context, R.anim.button_start_anim).apply {
            interpolator = BounceInterpolator()
        }

        val animator = ValueAnimator.ofFloat().apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = ONE_ANIMATOR_DURATION
            repeatCount = ONE_ANIMATOR_REPEATS
        }

        val animatorSet = AnimatorSet()

        with(binding) {
            val animator_r =
                ObjectAnimator.ofFloat(buttonAnim, View.ROTATION)
                    .apply {
                        setFloatValues(ANIM_START, 120f, 80f, 95f)
                        interpolator = AccelerateDecelerateInterpolator()
                        duration = ROTATE_ANIMATOR_DURATION
                    }

            val animator_t =
                ObjectAnimator.ofFloat(buttonAnim, View.TRANSLATION_Y).apply {
                    setFloatValues(ANIM_START, 140f)
                    interpolator = BounceInterpolator()
                    duration = TRANSLATION_ANIMATOR_DURATION
                }

            buttonAnimTwo.setOnClickListener {
                if (!animatorSet.isRunning) {
                    animatorSet.apply {
                        playTogether(animator_r, animator_t)
                        doOnEnd {
                            buttonAnim.apply {
                                translationY = ANIM_START
                                rotation = ANIM_START
                            }
                        }
                        start()
                    }
                }
            }

            buttonAnim.setOnClickListener {
                if (!animator.isRunning) {
                    val currentY = it.y
                    animator.apply {
                        setFloatValues(ANIM_START, ANIM_STEP, ANIM_START, -ANIM_STEP, ANIM_START)
                        addUpdateListener {
                            buttonAnim.y = currentY + animatedValue as Float
                        }
                        doOnEnd { buttonAnimTwo.visibility = View.VISIBLE }
                    }.start()
                }
            }

            buttonCreateUser.apply {
                startAnimation(anim)
                setOnClickListener {
                    parentFragmentManager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("UserStack")
                        .replace(R.id.fragmentContainerView, LocationsFragment())
                        .commit()
                }
            }
            buttonLightTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            buttonDarkTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            buttonAutoTheme.setOnClickListener {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    private fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

}