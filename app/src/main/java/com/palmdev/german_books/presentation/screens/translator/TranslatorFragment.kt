package com.palmdev.german_books.presentation.screens.translator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.palmdev.data.util.Constants
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.TranslatorFragmentBinding
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordDialogFragment
import com.palmdev.german_books.presentation.screens.dialog_translator_languages.TranslatorLanguagesDialogFragment
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import com.palmdev.german_books.utils.VoiceText
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslatorFragment : Fragment() {

    private val viewModel: TranslatorViewModel by viewModel()
    private lateinit var binding: TranslatorFragmentBinding
    private val mTranslator = GoogleMLKitTranslator
    private val mVoiceText by lazy { VoiceText(context = requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.translator_fragment, container, false)
        binding = TranslatorFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initTranslatorPrefs()
        if (viewModel.translatorPrefs.value?.name == Constants.SHARED_PREFS_NO_DATA) {
            val dialogTranslatorLanguages = TranslatorLanguagesDialogFragment {
                setUpTranslator()
            }
            dialogTranslatorLanguages.isCancelable = false
            dialogTranslatorLanguages.show(parentFragmentManager, "TAG")
        } else {
            setUpTranslator()
        }

        initButtonSelectLang()
        initButtonSwapLang()
        initButtonTranslate()
        initSoundButtons()
        initCopyButtons()
        initButtonSave()
    }


    private fun setUpTranslator() {
        viewModel.initTranslatorPrefs()
        viewModel.translatorPrefs.value?.let {
            mTranslator.createTranslator(toLanguage = it.code)
            binding.tvTranslateTo.text = it.name
            binding.tvTranslateFrom.text = getText(R.string.german)
            binding.btnSelectLang.text = it.name
        }
    }


    private fun initButtonSelectLang() {
        binding.btnSelectLang.setOnClickListener {
            val dialogTranslatorLanguages = TranslatorLanguagesDialogFragment {
                setUpTranslator()
            }
            dialogTranslatorLanguages.show(parentFragmentManager, "TAG")
        }
    }

    private fun initButtonTranslate() {
        binding.btnTranslate.setOnClickListener { view ->
            val textForTranslate = binding.editText.text.toString()
            mTranslator.translate(textForTranslate)
                ?.addOnCompleteListener { binding.tvTranslatedText.text = it.result }
                ?.addOnFailureListener { binding.tvTranslatedText.text = getString(R.string.error) }
            view.animate()
                .scaleX(0.7f).scaleY(0.7f)
                .setDuration(50).withEndAction {
                    view.animate()
                        .scaleX(1f).scaleY(1f)
                        .duration = 100
                }
        }
    }

    private fun initButtonSwapLang() {
        binding.btnSwapLanguages.setOnClickListener {
            binding.editText.text.clear()
            binding.tvTranslatedText.text = ""

            val languageOne = binding.tvTranslateFrom.text
            val languageTwo = binding.tvTranslateTo.text

            binding.tvTranslateFrom.text = languageTwo
            binding.tvTranslateTo.text = languageOne

            if (binding.tvTranslateFrom.text == getText(R.string.german)) {
                binding.btnSoundTop.visibility = View.VISIBLE
                binding.btnSoundBottom.visibility = View.GONE
                viewModel.translatorPrefs.value?.let { lang ->
                    mTranslator.createTranslator(
                        toLanguage = lang.code
                    )
                }
            } else if (binding.tvTranslateTo.text == getText(R.string.german)) {
                binding.btnSoundTop.visibility = View.GONE
                binding.btnSoundBottom.visibility = View.VISIBLE
                viewModel.translatorPrefs.value?.let { lang ->
                    mTranslator.createTranslator(
                        fromLanguage = lang.code,
                        toLanguage = "de"
                    )
                }
            }

            val animTopToBottom =
                AnimationUtils.loadAnimation(requireContext(), R.anim.anim_top_to_bottom)
            val animBottomToTop =
                AnimationUtils.loadAnimation(requireContext(), R.anim.anim_bottom_to_top)
            val animRotate = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_rotate)
            binding.topCardView.startAnimation(animTopToBottom)
            binding.bottomCardView.startAnimation(animBottomToTop)
            binding.btnSwapLanguages.startAnimation(animRotate)
        }
    }

    private fun initSoundButtons() {
        binding.btnSoundTop.setOnClickListener {
            mVoiceText.play(binding.editText.text.toString())
        }
        binding.btnSoundBottom.setOnClickListener {
            mVoiceText.play(binding.tvTranslatedText.text.toString())
        }
    }

    private fun initCopyButtons() {
        binding.btnCopyTop.setOnClickListener {
            val textForCopy = binding.editText.text
            if (textForCopy.isEmpty()) return@setOnClickListener

            val myClipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("Label", textForCopy)
            myClipboard.setPrimaryClip(myClip)

            Toast.makeText(requireContext(), getString(R.string.toastCopied), Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnCopyBottom.setOnClickListener {
            val textForCopy = binding.tvTranslatedText.text
            if (textForCopy.isEmpty()) return@setOnClickListener

            val myClipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip = ClipData.newPlainText("Label", textForCopy)
            myClipboard.setPrimaryClip(myClip)

            Toast.makeText(requireContext(), getString(R.string.toastCopied), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initButtonSave() {
        binding.btnSaveWord.setOnClickListener {
            val dialog = when (getText(R.string.german)) {
                binding.tvTranslateFrom.text -> {
                    SaveWordDialogFragment(
                        word = binding.editText.text.toString(),
                        translatedWord = binding.tvTranslatedText.text.toString()
                    )
                }
                binding.tvTranslateTo.text -> {
                    SaveWordDialogFragment(
                        word = binding.tvTranslatedText.text.toString(),
                        translatedWord = binding.editText.text.toString()
                    )
                }
                else -> SaveWordDialogFragment()

            }
            dialog.show(parentFragmentManager, "TAG")
        }
    }

}