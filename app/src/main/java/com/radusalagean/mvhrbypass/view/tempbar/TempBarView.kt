package com.radusalagean.mvhrbypass.view.tempbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.radusalagean.mvhrbypass.R

class TempBarView : View {

    // Static values
    private var rangeLow: Int = 0
    private var rangeHigh: Int = 0
    private var plusInfiniteValue: Boolean = false
        set(value) {
            if (value)
                valueHigh = Integer.MAX_VALUE
            field = value
        }
    @ColorInt private var barColor: Int = 0
    @ColorInt private var currentTempColor: Int = 0
    @ColorInt private var hysteresisColor: Int = 0
    @Dimension private var currentTempWidth = 0.0f

    // Dynamic values
    var valueLow: Int = 0
        set(value) {
            field = value
//            assertSpecs()
            invalidate()
        }
    var valueHigh: Int = 0
        set(value) {
            field = value
//            assertSpecs()
            invalidate()
        }
    var hysteresis: Float = 0.0f
        set(value) {
            field = value
            assertSpecs()
            invalidate()
        }
    var currentTemp: Float = 0.0f
        set(value) {
            field = value
            assertSpecs()
            invalidate()
        }

    // Paint
    private lateinit var barPaint: Paint
    private val barPlusInfinitePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                    (width - paddingEnd).toFloat(),
                    0.0f,
                    width.toFloat(),
                    0.0f,
                    barColor,
                    Color.TRANSPARENT,
                    Shader.TileMode.CLAMP
            )
        }
    }
    private lateinit var hysteresisPaint: Paint
    private lateinit var currentTempPaint: Paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context as Context
        val typedArr = context.obtainStyledAttributes(
                attrs,
                R.styleable.TempBarView,
                0,
                R.style.TempBarViewDefStyle
        )
        rangeLow = typedArr.getInt(R.styleable.TempBarView_rangeLow, 0)
        rangeHigh = typedArr.getInt(R.styleable.TempBarView_rangeHigh, 0)
        barColor = typedArr.getColor(R.styleable.TempBarView_barColor, 0)
        currentTempColor = typedArr.getColor(R.styleable.TempBarView_currentTempColor, 0)
        hysteresisColor = typedArr.getColor(R.styleable.TempBarView_hysteresisColor, 0)
        currentTempWidth = typedArr.getDimension(R.styleable.TempBarView_currentTempWidth, 0.0f)
        valueLow = typedArr.getInt(R.styleable.TempBarView_valueLow, 0)
        valueHigh = typedArr.getInt(R.styleable.TempBarView_valueHigh, 0)
        plusInfiniteValue = typedArr.getBoolean(R.styleable.TempBarView_plusInfiniteValue, false)
        hysteresis = typedArr.getFloat(R.styleable.TempBarView_hysteresis, 0.0f)
        currentTemp = typedArr.getFloat(R.styleable.TempBarView_currentTemp, 0.0f)
        typedArr.recycle()
        barPaint = buildPaint(barColor)
        hysteresisPaint = buildPaint(hysteresisColor)
        currentTempPaint = buildPaint(currentTempColor)
        assertSpecs()
    }

    private fun buildPaint(@ColorInt color: Int): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(color)
        }
    }

    private fun assertSpecs() {
        if (rangeLow >= rangeHigh)
            error("rangeLow has to be lower than rangeHigh")
        if (rangeLow + MIN_UNITS >= rangeHigh)
            error("there has to be a range of at least $MIN_UNITS units between rangeLow and rangeHigh")
        if (valueLow >= valueHigh)
            error("valueLow has to be lower than valueHigh")
        if (valueLow + MIN_UNITS >= valueHigh)
            error("there has to be a range of at least $MIN_UNITS units between valueLow and valueHigh")
        if (valueLow < rangeLow || valueLow >= rangeHigh)
            error("valueLow is out of range")
        if (!plusInfiniteValue && (valueHigh > rangeHigh || valueHigh <= rangeLow))
            error("valueHigh is out of range")
        if (HYSTERESIS_LOW > hysteresis || HYSTERESIS_HIGH < hysteresis)
            error("hysteresis is out of range")
    }

    fun getEffectiveWidth(): Int {
        return width - paddingStart - paddingEnd
    }

    fun getTempRatio(temp: Float): Float {
        return (temp - rangeLow) / ((rangeHigh - rangeLow).toFloat())
    }

    private fun drawBar(canvas: Canvas) {
        val xStart = getEffectiveWidth() * getTempRatio(valueLow.toFloat()) + paddingStart
        val xEnd = if (plusInfiniteValue) {
            getEffectiveWidth().toFloat()
        } else {
            getEffectiveWidth() * getTempRatio(valueHigh.toFloat())
        } + paddingStart
        canvas.drawRect(
                xStart, 0.0f, xEnd, height.toFloat(), barPaint
        )
        if (plusInfiniteValue && paddingEnd > 0) {
            canvas.drawRect(
                    (width - paddingEnd).toFloat(),
                    0.0f,
                    width.toFloat(),
                    height.toFloat(),
                    barPlusInfinitePaint
            )
        }
    }

    private fun drawHysteresis(canvas: Canvas) {
        if (hysteresis >= 0.1f) {
            var xStart: Float
            var xEnd: Float
            // low end
            xStart = getEffectiveWidth() * getTempRatio(valueLow - hysteresis) + paddingStart
            xEnd = getEffectiveWidth() * getTempRatio(valueLow + hysteresis) + paddingStart
            canvas.drawRect(
                    xStart, 0.0f, xEnd, height.toFloat(), hysteresisPaint
            )
            // high end
            if (!plusInfiniteValue) {
                xStart = getEffectiveWidth() * getTempRatio(valueHigh - hysteresis) + paddingStart
                xEnd = getEffectiveWidth() * getTempRatio(valueHigh + hysteresis) + paddingStart
                canvas.drawRect(
                        xStart, 0.0f, xEnd, height.toFloat(), hysteresisPaint
                )
            }
        }
    }

    private fun drawCurrentTemp(canvas: Canvas) {
        if (currentTemp >= rangeLow && currentTemp <= rangeHigh) {
            val x = getEffectiveWidth() * getTempRatio(currentTemp) + paddingStart
            canvas.drawRect(
                    x,
                    0.0f,
                    x + currentTempWidth,
                    height.toFloat(),
                    currentTempPaint
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawBar(it)
            drawHysteresis(it)
            drawCurrentTemp(it)
        }
    }

    companion object {
        const val MIN_UNITS = 3
        const val HYSTERESIS_LOW = 0.0f
        const val HYSTERESIS_HIGH = 1.0f
    }
}