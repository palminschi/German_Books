package com.palmdev.german_books.presentation.animations

import android.view.View

object AnimAppearFromRight {
    operator fun invoke(view: View) {
        view.animate()
            .translationX(500f)
            .setDuration(0)
            .withEndAction {
                view.animate()
                    .translationX(-50f)
                    .setDuration(600)
                    .withEndAction {
                       view.animate()
                           .setDuration(400)
                           .translationX(10f)
                           .withEndAction {
                               view.animate()
                                   .setDuration(200)
                                   .translationX(0f)
                                   .start()
                           }.start()
                    }.start()
            }.start()
    }
}