package com.palmdev.german_books.presentation.screens.reading.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentBottomSheetBinding
import com.palmdev.german_books.presentation.screens.reading.ReadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<ReadingViewModel>()
    lateinit var binding: FragmentBottomSheetBinding

    override fun onResume() {
        super.onResume()
        viewModel.updateUserLanguage()
        binding.tvLanguage.text = viewModel.userLanguage.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lLangSettings.setOnClickListener {
            findNavController().navigate(R.id.translatorLanguagesDialogFragment)
        }


        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switchTheme.isChecked = isDarkMode
            binding.switchTheme.setOnCheckedChangeListener { _, _ ->
                viewModel.switchDarkMode()
            }
        }

        viewModel.fontSize.observe(viewLifecycleOwner) { fontSize ->
            binding.tvCurrentFontSize.text = fontSize.roundToInt().toString()

            binding.fontSizePlus.setOnClickListener {
                if (fontSize < 30) viewModel.setFontSize(fontSize + 2)
            }

            binding.fontSizeMinus.setOnClickListener {
                if (fontSize > 12) viewModel.setFontSize(fontSize - 2)
            }
        }


        viewModel.currentBook.observe(viewLifecycleOwner) {
            binding.toggleLike.isChecked = it.isFavorite
            binding.toggleLike.background = if (binding.toggleLike.isChecked) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.icon_like_empty, null)
            }
            binding.toggleLike.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setIsFavoriteBook(isChecked, it)
                binding.toggleLike.background = if (isChecked) {
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)
                } else {
                    ResourcesCompat.getDrawable(resources, R.drawable.icon_like_empty, null)
                }
            }
        }

        viewModel.updateSavedWordsCount()
        viewModel.savedWordsCount.observe(viewLifecycleOwner) {
            binding.numberOfSelectedWords.text = it.toString()
        }
        binding.lSavedWords.setOnClickListener {
            findNavController().navigate(R.id.wordsFragment)
        }

    }
}