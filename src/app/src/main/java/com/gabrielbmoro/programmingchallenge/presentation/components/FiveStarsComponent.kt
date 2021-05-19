package com.gabrielbmoro.programmingchallenge.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.databinding.ComponentFiveStarsBinding
import kotlin.math.roundToInt

class FiveStarsComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ComponentFiveStarsBinding = ComponentFiveStarsBinding.inflate(
        LayoutInflater.from(context), this,
        true
    )

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.FiveStarsComponent)) {
            val votes = getFloat(R.styleable.FiveStarsComponent_votes, INVALID_NUMBER)
            setVotesAvg(votes)
            recycle()
        }
    }

    @DrawableRes
    private fun getDrawableAccordingToStarPosition(votes: Float, position: Int): Int {
        return when {
            votes >= position -> R.drawable.ic_star
            votes < position -> {
                if (votes.roundToInt() == position)
                    R.drawable.ic_star_half
                else
                    R.drawable.ic_star_border
            }
            else -> R.drawable.ic_star_border
        }
    }

    fun setVotesAvg(votes: Float) {
        val numberOfStarts = (votes / AVERAGE_TOTAL) * STARS_AVAILABLE
        if (votes != INVALID_NUMBER) {
            binding.componentIvFirstStar.background = ContextCompat.getDrawable(
                context,
                getDrawableAccordingToStarPosition(numberOfStarts, 1)
            )
            binding.componentIvSecondStar.background = ContextCompat.getDrawable(
                context,
                getDrawableAccordingToStarPosition(numberOfStarts, 2)
            )
            binding.componentIvThirdStar.background = ContextCompat.getDrawable(
                context,
                getDrawableAccordingToStarPosition(numberOfStarts, 3)
            )
            binding.componentIvFourthStar.background = ContextCompat.getDrawable(
                context,
                getDrawableAccordingToStarPosition(numberOfStarts, 4)
            )
            binding.componentIvFifthStar.background = ContextCompat.getDrawable(
                context,
                getDrawableAccordingToStarPosition(numberOfStarts, 5)
            )
        }

        if (votes > 0f) {
            binding.root.contentDescription = context.getString(R.string.alt_votes_average, votes)
        }
    }

    companion object {
        private const val STARS_AVAILABLE = 5
        private const val AVERAGE_TOTAL = 10
        private const val INVALID_NUMBER = -1f
    }

}