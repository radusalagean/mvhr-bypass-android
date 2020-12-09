package com.radusalagean.mvhrbypass.view.tempbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import com.radusalagean.mvhrbypass.R

class TempBarView : View {

    // Static values
    private var rangeLow: Int = 0
    private var rangeHigh: Int = 0
    private var minusInfiniteValue: Boolean = false
        set(value) {
            if (value)
                valueLow = Integer.MIN_VALUE
            field = value
        }
    private var plusInfiniteValue: Boolean = false
        set(value) {
            if (value)
                valueHigh = Integer.MAX_VALUE
            field = value
        }
    @ColorInt private var barColor: Int = 0
    @ColorInt private var currentTempColor: Int = 0
    @ColorInt private var hysteresisColor: Int = 0

    // Dynamic values
    private var valueLow: Int = 0
    private var valueHigh: Int = 0
    private var hysteresis: Float = 0.0f
    private var currentTemp: Float = 0.0f

    // Paint
    private lateinit var barPaint: Paint
    private lateinit var hysteresisPaint: Paint
    private lateinit var currentTempPaint: Paint

    constructor(context: Context?) : super(context) // TODO
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
        valueLow = typedArr.getInt(R.styleable.TempBarView_valueLow, 0)
        valueHigh = typedArr.getInt(R.styleable.TempBarView_valueHigh, 0)
        minusInfiniteValue = typedArr.getBoolean(R.styleable.TempBarView_minusInfiniteValue, false)
        plusInfiniteValue = typedArr.getBoolean(R.styleable.TempBarView_plusInfiniteValue, false)
        hysteresis = typedArr.getFloat(R.styleable.TempBarView_hysteresis, 0.0f)
        currentTemp = typedArr.getFloat(R.styleable.TempBarView_currentTemp, 0.0f)
        typedArr.recycle()
        barPaint = buildPaint(barColor)
        hysteresisPaint = buildPaint(hysteresisColor, Paint.Style.STROKE)
        currentTempPaint = buildPaint(currentTempColor)
        assertSpecs()
    }

    private fun buildPaint(@ColorInt color: Int, paintStyle: Paint.Style? = null): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(color)
            paintStyle?.let {
                style = paintStyle
            }
        }
    }

    private fun assertSpecs() {
        if (minusInfiniteValue && plusInfiniteValue)
            error("can't have both minusInfiniteValue and plusInfiniteValue set to true")
        if (rangeLow >= rangeHigh)
            error("rangeLow has to be lower than rangeHigh")
        if (rangeLow + MIN_UNITS >= rangeHigh)
            error("there has to be a range of at least $MIN_UNITS units between rangeLow and rangeHigh")
        if (valueLow >= valueHigh)
            error("valueLow has to be lower than valueHigh")
        if (valueLow + MIN_UNITS >= valueHigh)
            error("there has to be a range of at least $MIN_UNITS units between valueLow and valueHigh")
        if (!minusInfiniteValue && (valueLow < rangeLow || valueLow >= rangeHigh))
            error("valueLow is out of range")
        if (!plusInfiniteValue && (valueHigh > rangeHigh || valueHigh <= rangeLow))
            error("valueHigh is out of range")
        if (HYSTERESIS_LOW > hysteresis || HYSTERESIS_HIGH < hysteresis)
            error("hysteresis is out of range")
    }

    fun getTempRatio(temp: Float): Float {
        return (temp - rangeLow) / ((rangeHigh - rangeLow).toFloat())
    }

    private fun drawBar(canvas: Canvas) {
        val xStart = if (minusInfiniteValue) {
            0.0f
        } else {
            width * getTempRatio(valueLow.toFloat())
        }
        val xEnd = if (plusInfiniteValue) {
            width.toFloat()
        } else {
            width * getTempRatio(valueHigh.toFloat())
        }
        canvas.drawRect(
                xStart, 0.0f, xEnd, height.toFloat(), barPaint
        )
    }

    private fun drawHysteresis(canvas: Canvas) {
        if (hysteresis >= 0.1f) {
            var xStart: Float
            var xEnd: Float
            // low end
            if (!minusInfiniteValue) {
                xStart = width * getTempRatio(valueLow - hysteresis)
                xEnd = width * getTempRatio(valueLow + hysteresis)
                canvas.drawRect(
                        xStart, 0.0f, xEnd, height.toFloat(), hysteresisPaint
                )
            }
            // high end
            if (!plusInfiniteValue) {
                xStart = width * getTempRatio(valueHigh - hysteresis)
                xEnd = width * getTempRatio(valueHigh + hysteresis)
                canvas.drawRect(
                        xStart, 0.0f, xEnd, height.toFloat(), hysteresisPaint
                )
            }
        }
    }

    private fun drawCurrentTemp(canvas: Canvas) {
        if (currentTemp >= rangeLow && currentTemp <= rangeHigh) {
            val x = width * getTempRatio(currentTemp)
            canvas.drawLine(
                    x,
                    0.0f,
                    x,
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

    fun setValueLow(valueLow: LiveData<Int>) {
        this.valueLow = valueLow.value!!
    }

    fun setValueHigh(valueHigh: LiveData<Int>) {
        this.valueHigh = valueHigh.value!!
    }

    fun setHysteresis(hysteresis: LiveData<Float>) {
        this.hysteresis = hysteresis.value!!
    }

    fun setCurrentTemp(currentTemp: LiveData<Float>) {
        this.currentTemp = currentTemp.value!!
    }

    companion object {
        const val MIN_UNITS = 3
        const val HYSTERESIS_LOW = 0.0f
        const val HYSTERESIS_HIGH = 1.0f
    }
}