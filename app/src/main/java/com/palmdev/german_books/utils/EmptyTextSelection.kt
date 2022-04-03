package com.palmdev.german_books.utils

import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordDialogFragment

class EmptyTextSelection : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu?.clear()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu?.clear()
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }

}