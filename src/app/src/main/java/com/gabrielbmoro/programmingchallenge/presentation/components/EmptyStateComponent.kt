package com.gabrielbmoro.programmingchallenge.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.gabrielbmoro.programmingchallenge.databinding.ComponentEmptyStateBinding

class EmptyStateComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        ComponentEmptyStateBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

}