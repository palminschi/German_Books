package com.palmdev.german_books.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = getScreenOrientation()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Navigate up or call regular onBackPressed if no custom behavior is implemented
                if (!onBackPressed()) {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        })
        (activity as? MainActivity)?.setBottomNavigationVisibility(isNavigationVisible)

    }

    // Override this method in derived fragments to provide custom back press behavior
    open fun onBackPressed(): Boolean {
        return false
    }

    open val isNavigationVisible = true


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setBottomNavigationVisibility(true)
    }

    open fun getScreenOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}