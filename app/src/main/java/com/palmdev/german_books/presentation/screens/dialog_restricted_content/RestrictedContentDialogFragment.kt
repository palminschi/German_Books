package com.palmdev.german_books.presentation.screens.dialog_restricted_content

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.palmdev.german_books.R

class RestrictedContentDialogFragment : Fragment() {



    private lateinit var viewModel: RestrictedContentDialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_restricted_content_fragment, container, false)
    }



}