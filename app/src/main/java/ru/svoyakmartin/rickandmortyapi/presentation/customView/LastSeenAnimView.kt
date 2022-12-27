package ru.svoyakmartin.rickandmortyapi.presentation.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.os.ParcelCompat
import ru.svoyakmartin.rickandmortyapi.R
import java.util.*
import kotlin.math.max
import kotlin.random.Random

class LastSeenAnimView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRS: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRS) {
    private val circleRadius = context.toDp(12f)
    private val circlePadding = context.toDp(8f)
    private val statusTextSize = context.toDp(20f)
    private val lastSeenTextSize = context.toDp(14f)
    private val locationTextSize = context.toDp(16f)
    private val iconPaint: Paint by lazy {
        getPaint(PaintStyle.ICON)
    }
    private val statusTextPaint: Paint by lazy {
        getPaint(PaintStyle.STATUS)
    }
    private val headerTextPaint: Paint by lazy {
        getPaint(PaintStyle.HEADER)
    }
    private val locationTextPaint: Paint by lazy {
        getPaint(PaintStyle.LOCATION)
    }

    private val reference = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 "
    private var isLastSeenVisible = false
    private var generatedText = ""
    private var currentIteration = 0

    //attrs
    private var aliveStatus = AliveStatus.ALIVE
    private var searchIterations = 5
    private var location = ""
    private var locationColor = 0
    private var locationFindColor = 0

    private val random = Random(Date().time)

    init {
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
                getString(R.styleable.LastSeenAnimView_LSA_location)?.let {
                    location = it
                }
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

                searchIterations =
                    getInt(R.styleable.LastSeenAnimView_LSA_search_iterations, 5)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val wSize = MeasureSpec.getSize(widthMeasureSpec)
//        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        val wStatus = statusTextPaint.measureText(getStatusText(aliveStatus))
        val wHeader = headerTextPaint.measureText(context.getString(R.string.last_seen_text))
        val wLocation = locationTextPaint.measureText(location)

        val width = circlePadding * 2 + circleRadius * 2 + max(wStatus, max(wHeader, wLocation))
        val height = circlePadding + statusTextSize + lastSeenTextSize + locationTextSize

//        when (MeasureSpec.getMode(widthMeasureSpec)){
//            MeasureSpec.UNSPECIFIED ->{}
//            MeasureSpec.AT_MOST ->{}
//            MeasureSpec.EXACTLY ->{}
//        }

        setMeasuredDimension(width.toInt() + 1, height.toInt() + 1)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawStatus(it)
            if (isLastSeenVisible) {
                drawHeader(canvas)
                drawLocation(canvas)

                if (generatedText.length <= location.length) {
                    showGenerateLocation()
                }
            }
        }
    }

    fun setSearchIterations(value: Int) {
        searchIterations = if (value < 1) 1 else value
    }

    fun setLocation(value: String) {
        location = value
    }

    fun setLocationColor(color: Int) {
        locationColor = color
    }

    fun setLocationFindColor(color: Int) {
        locationFindColor = color
    }

    fun setAliveStatus(status: AliveStatus) {
        aliveStatus = status
    }

    private fun getPaint(paintStyle: PaintStyle): Paint {
        return Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true

            when (paintStyle) {
                PaintStyle.ICON -> color = getStatusColor()
                PaintStyle.STATUS -> {
                    color = context.getColor(R.color.black)
                    textSize = statusTextSize
                }
                PaintStyle.HEADER -> {
                    color = context.getColor(R.color.light_gray)
                    textSize = lastSeenTextSize
                }
                PaintStyle.LOCATION -> {
                    color = locationFindColor
                    textSize = locationTextSize
                }
            }
        }
    }

    private fun getStatusText(status: AliveStatus = aliveStatus): String {
        return context.getString(
            when (status) {
                AliveStatus.ALIVE -> R.string.status_alive
                AliveStatus.DEAD -> R.string.status_dead
                AliveStatus.UNKNOWN -> R.string.status_unknown
            }
        )
    }

    private fun getStatusColor(status: AliveStatus = aliveStatus): Int {
        return context.getColor(
            when (status) {
                AliveStatus.ALIVE -> R.color.dark_green
                AliveStatus.DEAD -> R.color.dark_red
                AliveStatus.UNKNOWN -> R.color.dark_gray
            }
        )
    }

    private fun drawStatus(canvas: Canvas) {
        drawStatusIcon(canvas)
        drawStatusText(canvas)
    }

    private fun drawStatusIcon(canvas: Canvas) {
        canvas.drawCircle(
            circlePadding + circleRadius,
            circlePadding + circleRadius,
            circleRadius,
            iconPaint
        )
    }

    private fun drawStatusText(canvas: Canvas) {
        canvas.drawText(
            getStatusText(),
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding,
            statusTextPaint
        )
    }

    private fun drawHeader(canvas: Canvas) {
        canvas.drawText(
            context.getString(R.string.last_seen_text),
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding + lastSeenTextSize,
            headerTextPaint
        )
    }

    private fun drawLocation(canvas: Canvas) {
        canvas.drawText(
            generatedText,
            circleRadius * 2 + circlePadding * 2,
            statusTextSize + circlePadding + lastSeenTextSize + locationTextSize,
            locationTextPaint
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

    private fun showGenerateLocation() {
        if (location.isNotEmpty() && generatedText.length <= location.length) {
            generateLocation()
            invalidate()
        }
    }

    private fun generateLocation() {
        currentIteration++
        var currentIndex = if (generatedText.isEmpty()) 0 else generatedText.length - 1

        if (generatedText.length > currentIndex &&
            location[currentIndex] == generatedText[currentIndex]
        ) {
            currentIndex++

            if (currentIndex == location.length) {
                locationTextPaint.color = locationColor
                return
            }
        }

        val randomChar = reference[random.nextInt(reference.length - 1)]

        if (currentIteration == searchIterations) {
            currentIteration = 0
            generatedText = location.substring(0, currentIndex + 1)
        } else {
            generatedText = location.substring(0, currentIndex) + randomChar
        }
    }

    private fun Context.toDp(value: Float): Float {
        return resources.displayMetrics.density * value
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

    enum class AliveStatus {
        ALIVE,
        DEAD,
        UNKNOWN
    }

    enum class PaintStyle {
        ICON,
        STATUS,
        HEADER,
        LOCATION
    }
}