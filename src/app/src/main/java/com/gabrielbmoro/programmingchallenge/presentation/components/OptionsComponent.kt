package com.gabrielbmoro.programmingchallenge.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.res.getTextArrayOrThrow
import com.gabrielbmoro.programmingchallenge.R
import timber.log.Timber

typealias OptionComponentSelectedCallback = ((String) -> Unit)

class OptionsComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : RadioGroup(context, attrs) {

    private val radioButtonsList = ArrayList<RadioButton>()

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.OptionsComponent)) {
            try {
                val array = getTextArrayOrThrow(R.styleable.OptionsComponent_options)
                radioButtonsList.addAll(
                        array.map { charSequence ->
                            val radioButton = RadioButton(context).apply {
                                text = charSequence
                                val padding = resources.getDimensionPixelSize(R.dimen.margin_5)
                                setPadding(padding, padding, padding, padding)
                            }
                            addView(radioButton)
                            radioButton
                        }
                )
            } catch (runtimeException: RuntimeException) {
                Timber.e(runtimeException)
            }
            recycle()
        }
    }

    fun setupCallback(callback: OptionComponentSelectedCallback) {
        setOnCheckedChangeListener { _, checkedId ->
            radioButtonsList.firstOrNull { it.id == checkedId }?.text?.toString()?.let { selectedItem ->
                callback.invoke(selectedItem)
            }
        }
    }

    fun setCheckTo(optionValue: CharSequence) {
        radioButtonsList.forEach { item ->
            if (item.text == optionValue)
                item.isChecked = true
        }
    }
}