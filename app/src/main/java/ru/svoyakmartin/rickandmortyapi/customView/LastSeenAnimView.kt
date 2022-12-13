package ru.svoyakmartin.rickandmortyapi.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ru.svoyakmartin.rickandmortyapi.R

private enum class AliveStatus(val label: Int) {
    ALIVE(R.string.status_alive),
    DEAD(R.string.status_dead),
    UNKNOWN(R.string.status_unknown)
}

class LastSeenAnimView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRS: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRS) {

    private val circleRadius = 25F
    private val circlePadding = 20F
    private val statusTextSize = 50F
    private val lastSeenTextSize = 40F
    private val locationTextSize = 45F
    private var aliveStatus = AliveStatus.ALIVE
    private val reference = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 "
    private var isLastSeenVisible = false
    private var generatedText = ""
//    private val random = Random(Date().time)

    private var location = "Citadel of Ricks"
    private var alive = ""

    init {
        isClickable = true

        setOnClickListener {
            if (!isLastSeenVisible) {
                isLastSeenVisible = true
                showGenerateLocation()
            }
        }

        attrs?.let {
//            initAttrs(it, defStyleAttr, defStyleRS)
        }
    }

    private val paint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

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

    private fun initAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRS: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LastSeenAnimView,
            defStyleAttr,
            defStyleRS
        ).apply {
            try {
                location = getString(R.styleable.LastSeenAnimView_location)!!
                alive = getString(R.styleable.LastSeenAnimView_aliveStatus).toString()
            } finally {
                recycle()
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
            color = ContextCompat.getColor(
                context,
                if (location == generatedText) R.color.dark_green else R.color.dark_red
            )
            textSize = locationTextSize
        }

        canvas.drawText(
            generatedText,
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding + lastSeenTextSize + locationTextSize,
            paint
        )
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