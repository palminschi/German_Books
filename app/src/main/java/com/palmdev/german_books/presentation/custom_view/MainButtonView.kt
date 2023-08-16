package com.palmdev.german_books.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ViewMainButtonBinding
import com.palmdev.german_books.extensions.toDp
import com.palmdev.german_books.extensions.toSp
import com.palmdev.german_books.presentation.sounds.Sound
import com.palmdev.german_books.presentation.sounds.SoundPlayer

class MainButtonView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewMainButtonBinding

    var textButton = context.getString(R.string.start)
        set(value) {
            field = value
            binding.textView.text = textButton
        }
    var buttonTextAllCaps = false
        set(value) {
            field = value
            binding.textView.isAllCaps = value
        }
    var buttonCornerRadius = 15.toDp(context)
        set(value) {
            field = value
            binding.cardViewButton.radius = value
            binding.cardViewBackground.radius = value
        }
    var selectedColor = 0
        set(value) {
            field = value
            setBtnColor(value)
        }
    var textMarginVertical = 16.toDp(context)
        set(value) {
            field = value
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(
                6.toDp(context).toInt(),
                value.toInt(),
                6.toDp(context).toInt(),
                value.toInt()
            )
            binding.textView.layoutParams = params
        }
    var textSizeButton = 15.toSp(context)
        set(value) {
            field = value
            binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }
    var transcriptionText = ""
        set(value) {
            field = value
            if (value.isEmpty()) {
                binding.tvTranscription.visibility = GONE
                binding.tvTranscriptionLine.visibility = GONE
            } else {
                binding.tvTranscription.visibility = VISIBLE
                binding.tvTranscriptionLine.visibility = VISIBLE
                binding.tvTranscription.text = value
            }
        }


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_main_button, this, true)
        binding = ViewMainButtonBinding.bind(this)
        initializeAttrs(attrs, defStyleAttr)
    }

    private fun initializeAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MainButtonView,
            defStyleAttr,
            0
        )

        this.transcriptionText =
            typedArray.getString(R.styleable.MainButtonView_transcriptionText) ?: ""

        this.textButton = typedArray.getString(R.styleable.MainButtonView_textButton) ?: context.getString(R.string.start)

        this.buttonCornerRadius =
            typedArray.getDimension(R.styleable.MainButtonView_buttonCornerRadius, 15.toDp(context))
        binding.cardViewButton.radius = this.buttonCornerRadius
        binding.cardViewBackground.radius = this.buttonCornerRadius

        this.buttonTextAllCaps =
            typedArray.getBoolean(R.styleable.MainButtonView_buttonTextAllCaps, false)

        this.textSizeButton =
            typedArray.getDimension(R.styleable.MainButtonView_textSizeButton, 15.toSp(context))
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSizeButton)

        this.textMarginVertical =
            typedArray.getDimension(R.styleable.MainButtonView_textMarginVertical, 16.toDp(context))

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(
            6.toDp(context).toInt(),
            this.textMarginVertical.toInt(),
            6.toDp(context).toInt(),
            this.textMarginVertical.toInt()
        )
        binding.textView.layoutParams = params

        this.selectedColor = typedArray.getInt(R.styleable.MainButtonView_buttonColor, 0)
        setBtnColor(this.selectedColor)

        typedArray.recycle()
    }

    private fun setBtnColor(colorId: Int) {
        when (colorId) {
            0 -> {
                binding.linearLayout.background =
                    AppCompatResources.getDrawable(context, R.drawable.gradient_purple)
                binding.cardViewBackground.setCardBackgroundColor(
                    resources.getColor(R.color.purple_dark, null)
                )
                binding.textView.setTextColor(
                    resources.getColor(R.color.white, null)
                )
            }
            1 -> {
                binding.linearLayout.background = null
                binding.linearLayout.setBackgroundColor(
                    resources.getColor(R.color.gray_light, null)
                )
                binding.cardViewBackground.setCardBackgroundColor(
                    resources.getColor(R.color.gray_accent, null)
                )
                binding.textView.setTextColor(
                    resources.getColor(R.color.black, null)
                )
            }
            2 -> {
                binding.linearLayout.background = null
                binding.linearLayout.setBackgroundColor(
                    resources.getColor(R.color.white, null)
                )
                binding.cardViewBackground.setCardBackgroundColor(
                    resources.getColor(R.color.gray, null)
                )

                val layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(
                    2, 2, 2, 10
                )
                binding.cardViewButton.layoutParams = layoutParams

                val backgroundParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
                backgroundParams.setMargins(
                    0, 0, 0, 0
                )
                binding.cardViewBackground.layoutParams = backgroundParams
                binding.textView.setTextColor(
                    resources.getColor(R.color.black, null)
                )
            }

            3 -> {
                binding.linearLayout.background = null
                binding.linearLayout.setBackgroundColor(
                    resources.getColor(R.color.yellow, null)
                )
                binding.cardViewBackground.setCardBackgroundColor(
                    resources.getColor(R.color.yellow_dark, null)
                )
                binding.textView.setTextColor(
                    resources.getColor(R.color.black, null)
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                if (this.selectedColor == 2) {
                    binding.root.animate().scaleX(1F).scaleY(1F).start()
                } else {
                    binding.cardViewButton.animate().translationY(0F).start()
                }
                performClick()
            }
            MotionEvent.ACTION_DOWN -> {
                SoundPlayer(context, Sound.CLICK).play()
                if (this.selectedColor == 2) {
                    binding.root.animate().scaleX(0.9F).scaleY(0.9F).setDuration(50).start()
                } else {
                    binding.cardViewButton.animate().translationY(7F).setDuration(100).start()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (this.selectedColor == 2) {
                    binding.root.animate().scaleX(1F).scaleY(1F).start()
                } else {
                    binding.cardViewButton.animate().translationY(0F).start()
                }
            }
        }
        return true
    }


}