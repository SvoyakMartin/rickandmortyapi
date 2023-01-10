package ru.svoyakmartin.rickandmortyapi.presentation.customView

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.databinding.ViewLastSeenBinding

class LastSeenView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRS: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRS) {

    private var binding = ViewLastSeenBinding.inflate(LayoutInflater.from(context), this)

    init {
        with(binding) {
            //TODO: debag!->
            statusText.text = "Alive"
            lastSeenHeader.text = "Last seen on:"
            lastSeenLocation.text = "Citadel of Ricks"
            statusIcon.setTint(R.color.dark_green)
            //<-debag!

            setOnClickListener {
                lastSeenGroup.visibility = if (lastSeenGroup.visibility == View.VISIBLE) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
            }
        }
    }

    private fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
        )
    }
}