package com.palmdev.german_books.presentation.animations

import android.view.View

object AnimAppearFromBottom {
    operator fun invoke(view: View, endAction: (() -> Unit)? = null) {
        view.animate()
            .translationY(300f)
            .setDuration(0)
            .withEndAction {
                view.animate()
                    .translationY(0f)
                    .setDuration(300)
                    .withEndAction {
                        if (endAction != null) {
                            endAction()
                        }
                    }.start()
            }.start()
    }
}