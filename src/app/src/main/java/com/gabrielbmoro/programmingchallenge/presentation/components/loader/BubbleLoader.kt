package com.gabrielbmoro.programmingchallenge.presentation.components.loader

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.gabrielbmoro.programmingchallenge.R
import kotlin.math.roundToInt

class BubbleLoader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val animatorSet = AnimatorSet()

    init {
        orientation = HORIZONTAL

        with(context.obtainStyledAttributes(attrs, R.styleable.BubbleLoader)) {
            val animatorList = setup(this)
            animatorSet.playTogether(animatorList)
            recycle()
        }
    }

    private fun setup(typedArray: TypedArray): List<Animator> {
        val dots = typedArray.getInt(R.styleable.BubbleLoader_amountOfDots, DEFAULT_DOTS)
        val dotSize = typedArray.getDimensionPixelSize(R.styleable.BubbleLoader_dotSize, 0)
        val dotColor = typedArray.getColor(R.styleable.BubbleLoader_dotsColor, Color.BLACK)
        val animatorList = ArrayList<Animator>()
        repeat(dots) { count ->
            val dotView = createAndAddDot(dotColor, dotSize, this@BubbleLoader)
            val animator = getAnimator(view = dotView, dotIndex = count, amountOfDots = dots)
            animatorList.add(animator)
        }
        return animatorList.toList()
    }

    private fun createAndAddDot(@ColorInt color: Int, dotSize: Int, bubbleLoader: BubbleLoader): View {
        val dotView = createDot(color = color)
        val layoutSize = dotSize + (dotSize * DOT_EXPANDABLE_PROPORTION).roundToInt()
        bubbleLoader.addView(
                dotView,
                LayoutParams(
                        layoutSize,
                        layoutSize
                )
        )
        return dotView
    }

    fun start() {
        animatorSet.start()
        visibility = View.VISIBLE
    }

    fun stop() {
        animatorSet.end()
        visibility = View.GONE
    }

    private fun getAnimator(view: View, dotIndex: Int, amountOfDots: Int): Animator {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, DOT_EXPANDABLE_PROPORTION)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, DOT_EXPANDABLE_PROPORTION)
        return ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
            repeatCount = INFINITE
            repeatMode = ObjectAnimator.REVERSE
            duration = DURATION
        }.also { anim ->
            anim.startDelay = ((dotIndex / amountOfDots.toFloat()) * DURATION).toLong()
        }
    }

    private fun createDot(@ColorInt color: Int): View {
        return DotView(
                context = context,
                color = color
        )
    }

    companion object {
        private const val DOT_EXPANDABLE_PROPORTION = 1.8f
        private const val DURATION = 600L
        private const val INFINITE = -1
        private const val DEFAULT_DOTS = 3
    }
}