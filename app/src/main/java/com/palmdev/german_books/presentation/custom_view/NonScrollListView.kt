package com.palmdev.german_books.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ListView


class NonScrollListView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
    : ListView(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpecCustom = MeasureSpec.makeMeasureSpec(
            Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpecCustom)
        val params: ViewGroup.LayoutParams = layoutParams
        params.height = measuredHeight
    }
}