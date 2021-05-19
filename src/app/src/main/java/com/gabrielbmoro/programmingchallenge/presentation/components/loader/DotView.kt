package com.gabrielbmoro.programmingchallenge.presentation.components.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class DotView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    constructor(context: Context, @ColorInt color: Int) : this(
            context,
            null
    ) {
        this.paint.color = color
    }


    override fun onDraw(canvas: Canvas?) {
        val radius = width / 2f
        canvas?.drawCircle(radius, radius, radius / 2, paint)
    }
}