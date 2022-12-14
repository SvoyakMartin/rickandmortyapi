package ru.svoyakmartin.rickandmortyapi.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.ParcelCompat
import ru.svoyakmartin.rickandmortyapi.R

class LastSeenAnimView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRS: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRS) {

    enum class AliveStatus {
        ALIVE,
        DEAD,
        UNKNOWN
    }

    private val circleRadius = 25F
    private val circlePadding = 20F
    private val statusTextSize = 50F
    private val lastSeenTextSize = 40F
    private val locationTextSize = 45F
    private val paint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    private val reference = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 "
    private var isLastSeenVisible = false
    private var generatedText = ""

    //attrs
    private var aliveStatus = AliveStatus.ALIVE
    private var location = ""
    private var locationColor = 0
    private var locationFindColor = 0

//    private val random = Random(Date().time)

    init {
        isClickable = true

        setOnClickListener {
            if (!isLastSeenVisible) {
                isLastSeenVisible = true
                showGenerateLocation()
            }
        }

        attrs?.let {
            initAttrs(it, defStyleAttr, defStyleRS)
        }
    }

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRS: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LastSeenAnimView,
            defStyleAttr,
            defStyleRS
        ).apply {
            try {
                location = getString(R.styleable.LastSeenAnimView_LSA_location)!!
                locationColor = getColor(
                    R.styleable.LastSeenAnimView_LSA_location_color,
                    context.getColor(R.color.dark_green)
                )
                locationFindColor = getInt(
                    R.styleable.LastSeenAnimView_LSA_location_find_color,
                    context.getColor(R.color.dark_red)
                )
                val status = getInt(
                    R.styleable.LastSeenAnimView_LSA_aliveStatus,
                    AliveStatus.ALIVE.ordinal
                )
                aliveStatus = AliveStatus.values()[status]

            } finally {
                recycle()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(wSize, hSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawStatus(it)
            if (isLastSeenVisible) {
                drawLastSeen(canvas)
                drawLocation(canvas)

                if (generatedText != location) {
                    showGenerateLocation()
                }
            }
        }
    }

    private fun drawStatus(canvas: Canvas) {
        val colorId: Int
        val stringId: Int

        when (aliveStatus) {
            AliveStatus.ALIVE -> {
                colorId = R.color.dark_green
                stringId = R.string.status_alive
            }
            AliveStatus.DEAD -> {
                colorId = R.color.dark_red
                stringId = R.string.status_dead
            }
            AliveStatus.UNKNOWN -> {
                colorId = R.color.dark_gray
                stringId = R.string.status_unknown
            }
        }

        drawStatusCircle(canvas, colorId)
        drawStatusText(canvas, stringId)
    }

    private fun drawStatusCircle(canvas: Canvas, colorId: Int) {
        paint.color = context.getColor(colorId)

        canvas.drawCircle(
            circlePadding + circleRadius,
            circlePadding + circleRadius,
            circleRadius,
            paint
        )
    }

    private fun drawStatusText(canvas: Canvas, stringId: Int) {
        paint.apply {
            color = ContextCompat.getColor(context, R.color.black)
            textSize = statusTextSize
        }

        canvas.drawText(
            context.getString(stringId),
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding,
            paint
        )
    }

    private fun drawLastSeen(canvas: Canvas) {
        paint.apply {
            color = ContextCompat.getColor(context, R.color.light_gray)
            textSize = lastSeenTextSize
        }

        canvas.drawText(
            context.getString(R.string.last_seen_text),
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding + lastSeenTextSize,
            paint
        )
    }

    private fun drawLocation(canvas: Canvas) {
        paint.apply {
            color = if (location == generatedText) locationColor else locationFindColor
            textSize = locationTextSize
        }

        canvas.drawText(
            generatedText,
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding + lastSeenTextSize + locationTextSize,
            paint
        )
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState()).apply {
            isLastSeenVisibleState = isLastSeenVisible
            generatedTextState = generatedText
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            isLastSeenVisible = state.isLastSeenVisibleState
            generatedText = state.generatedTextState
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {

        var isLastSeenVisibleState: Boolean = false
        var generatedTextState: String = ""

        //save
        constructor(superState: Parcelable?) : super(superState)

        //for restore
        constructor(source: Parcel) : super(source) {
            source.apply {
                isLastSeenVisibleState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q/*29*/) {
                    readBoolean()
                } else {
                    ParcelCompat.readBoolean(source)
                }
                readString()?.let { generatedTextState = it }
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q/*29*/) {
                out.writeBoolean(isLastSeenVisibleState)
            } else {
                ParcelCompat.writeBoolean(out, isLastSeenVisibleState)
            }
            out.writeString(generatedTextState)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    private fun showGenerateLocation() {
        if (location.isNotEmpty() && generatedText != location) {
            val currentIndex = if (generatedText.isEmpty()) 0 else generatedText.length - 1
            val currentChar: Char

            //RANDOM - слишком долго перебирает, переделываем
//            val randomChar = reference[random.nextInt(location.length)]
//
//            if (generatedText.isEmpty() || generatedText[currentIndex] == location[currentIndex]) {
//                generatedText += randomChar
//            } else if (currentIndex == 0) {
//                generatedText = "" + randomChar
//            } else {
//                generatedText = location.substring(0, currentIndex) + randomChar
//            }

            if (generatedText.isEmpty() || generatedText[currentIndex] == location[currentIndex]) {
                currentChar = reference[0]
                generatedText += currentChar
            } else {
                currentChar = reference[reference.indexOf(generatedText.last()) + 1]
                generatedText = if (currentIndex == 0) {
                    "" + currentChar
                } else {
                    location.substring(0, currentIndex) + currentChar
                }
            }

            invalidate()
        }
    }
}