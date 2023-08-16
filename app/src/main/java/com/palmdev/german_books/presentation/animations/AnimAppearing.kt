package com.palmdev.german_books.presentation.animations

import android.view.View

object AnimAppearing {
    operator fun invoke(view: View, endAction: (() -> Unit)? = null) {

        view.animate().alpha(0f).withEndAction {
            view.visibility = View.VISIBLE
            view.animate()
                .alpha(1f)
                .setDuration(500)
                .withEndAction {

                    if (endAction != null) {
                        endAction()
                    }
                }.start()
        }.start()

    }
}