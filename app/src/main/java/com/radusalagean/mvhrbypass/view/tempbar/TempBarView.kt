package com.radusalagean.mvhrbypass.view.tempbar

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.radusalagean.mvhrbypass.R

class TempBarView : View {

    // Static values
    @Dimension private var tempBarHeight: Float = 0.0f
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
    @Dimension private var currentTempWidth: Float = 0.0f
    @Dimension private var barLabelSpacing: Float = 0.0f
    @Dimension private var labelTextSize: Float = 0.0f

    // Dynamic values
    var valueLow: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var valueHigh: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var hysteresis: Float = 0.0f
        set(value) {
            field = value
            invalidate()
        }
    var currentTemp: Float = 0.0f
        set(value) {
            field = value
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
    private lateinit var labelTextPaint: TextPaint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context as Context
        val typedArr = context.obtainStyledAttributes(
                attrs,
                R.styleable.TempBarView,
                0,
                R.style.TempBarViewDefStyle
        )
        tempBarHeight = typedArr.getDimension(R.styleable.TempBarView_tempBarHeight, 0.0f)
        rangeLow = typedArr.getInt(R.styleable.TempBarView_rangeLow, 0)
        rangeHigh = typedArr.getInt(R.styleable.TempBarView_rangeHigh, 0)
        barColor = typedArr.getColor(R.styleable.TempBarView_barColor, 0)
        currentTempColor = typedArr.getColor(R.styleable.TempBarView_currentTempColor, 0)
        hysteresisColor = typedArr.getColor(R.styleable.TempBarView_hysteresisColor, 0)
        currentTempWidth = typedArr.getDimension(R.styleable.TempBarView_currentTempWidth, 0.0f)
        barLabelSpacing = typedArr.getDimension(R.styleable.TempBarView_barLabelSpacing, 0.0f)
        labelTextSize = typedArr.getDimension(R.styleable.TempBarView_labelTextSize, 0.0f)
        valueLow = typedArr.getInt(R.styleable.TempBarView_valueLow, 0)
        valueHigh = typedArr.getInt(R.styleable.TempBarView_valueHigh, 0)
        plusInfiniteValue = typedArr.getBoolean(R.styleable.TempBarView_plusInfiniteValue, false)
        hysteresis = typedArr.getFloat(R.styleable.TempBarView_hysteresis, 0.0f)
        currentTemp = typedArr.getFloat(R.styleable.TempBarView_currentTemp, 0.0f)
        typedArr.recycle()
        barPaint = buildPaint(barColor)
        hysteresisPaint = buildPaint(hysteresisColor)
        currentTempPaint = buildPaint(currentTempColor)
        labelTextPaint = buildTextPaint(barColor, labelTextSize)
        assertSpecs()
    }

    private fun buildPaint(@ColorInt color: Int): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(color)
        }
    }

    private fun buildTextPaint(@ColorInt color: Int, @Dimension textSize: Float): TextPaint {
        return TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(color)
            this.textSize = textSize
        }
    }

    private fun assertSpecs() {
        if (rangeLow >= rangeHigh)
            error("rangeLow has to be lower than rangeHigh")
        if (rangeLow + MIN_UNITS > rangeHigh)
            error("there has to be a range of at least $MIN_UNITS units between rangeLow and rangeHigh")
        if (valueLow >= valueHigh)
            error("valueLow has to be lower than valueHigh")
        if (valueLow + MIN_UNITS > valueHigh)
            error("there has to be a range of at least $MIN_UNITS units between valueLow and valueHigh")
        if (valueLow < rangeLow || valueLow >= rangeHigh)
            error("valueLow is out of range")
        if (!plusInfiniteValue && (valueHigh > rangeHigh || valueHigh <= rangeLow))
            error("valueHigh is out of range")
        if (HYSTERESIS_LOW > hysteresis || HYSTERESIS_HIGH < hysteresis)
            error("hysteresis is out of range")
    }

    private fun getEffectiveWidth(): Int {
        return width - paddingStart - paddingEnd
    }

    private fun getTempRatio(temp: Float): Float {
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
                xStart, 0.0f, xEnd, tempBarHeight, barPaint
        )
        if (plusInfiniteValue && paddingEnd > 0) {
            canvas.drawRect(
                    (width - paddingEnd).toFloat(),
                    0.0f,
                    width.toFloat(),
                    tempBarHeight,
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
                    xStart, 0.0f, xEnd, tempBarHeight, hysteresisPaint
            )
            // high end
            if (!plusInfiniteValue) {
                xStart = getEffectiveWidth() * getTempRatio(valueHigh - hysteresis) + paddingStart
                xEnd = getEffectiveWidth() * getTempRatio(valueHigh + hysteresis) + paddingStart
                canvas.drawRect(
                        xStart, 0.0f, xEnd, tempBarHeight, hysteresisPaint
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
                    tempBarHeight,
                    currentTempPaint
            )
        }
    }

    private fun drawLabels(canvas: Canvas) {

        fun getTextX(value: Int, text: String): Float {
            val textWidth = labelTextPaint.measureText(text)
            return paddingStart + getEffectiveWidth() * getTempRatio(value.toFloat()) - (textWidth / 2)
        }

        var x: Float
        val y = tempBarHeight + barLabelSpacing + labelTextSize
        var text: String
        // low end
        text = valueLow.toString()
        x = getTextX(valueLow, text)
        canvas.drawText(text, x, y, labelTextPaint)
        // high end
        if (!plusInfiniteValue) {
            text = valueHigh.toString()
            x = getTextX(valueHigh, text)
            canvas.drawText(text, x, y, labelTextPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = (paddingTop + tempBarHeight + barLabelSpacing + labelTextSize + paddingBottom).toInt()
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawBar(it)
            drawHysteresis(it)
            drawCurrentTemp(it)
            drawLabels(it)
        }
    }

    companion object {
        const val MIN_UNITS = 3
        const val HYSTERESIS_LOW = 0.0f
        const val HYSTERESIS_HIGH = 1.0f
    }
}