package com.palmdev.german_books.presentation.screens.game_write_word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameWriteWordBinding


class GameWriteWordFragment : Fragment() {

    private lateinit var binding: FragmentGameWriteWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_write_word, container, false)
        binding = FragmentGameWriteWordBinding.bind(view)
        return view
    }


}