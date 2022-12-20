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
            duration = 2000
            repeatCount = 3
        }

        val animatorSet = AnimatorSet()

        with(binding) {
            val animator_r =
                ObjectAnimator.ofFloat(buttonAnim, View.ROTATION, 0f, 120f, 80f, 95f)
                    .apply {
                        interpolator = AccelerateDecelerateInterpolator()
                        duration = 5000
                    }

            val animator_t =
                ObjectAnimator.ofFloat(buttonAnim, View.TRANSLATION_Y, 0f, 140f).apply {
                    interpolator = BounceInterpolator()
                    duration = 3000
                }

            buttonAnimTwo.setOnClickListener {
                if (!animatorSet.isRunning) {
                    animatorSet.apply {
                        playTogether(animator_r, animator_t)
                        doOnEnd {
                                                    buttonAnim.apply {
                                                        translationY = 0f
                                                        rotation = 0f
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
                        setFloatValues(0f, 100f, 0f, -100f, 0f)
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