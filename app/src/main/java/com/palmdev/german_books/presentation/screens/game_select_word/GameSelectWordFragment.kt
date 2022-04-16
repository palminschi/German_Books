package com.palmdev.german_books.presentation.screens.game_select_word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameSelectWordBinding

class GameSelectWordFragment : Fragment() {

    private lateinit var binding: FragmentGameSelectWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_select_word, container, false)
        binding = FragmentGameSelectWordBinding.bind(view)
        return view
    }


}