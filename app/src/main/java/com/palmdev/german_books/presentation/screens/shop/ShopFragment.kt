package com.palmdev.german_books.presentation.screens.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ShopFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopFragment : Fragment() {

    private val viewModel: ShopViewModel by viewModel()
    private lateinit var binding: ShopFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.shop_fragment, container, false)
        binding = ShopFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPurchase.setOnClickListener {
            viewModel.buyPremium(requireContext(), requireActivity())
        }

        viewModel.initPremiumStatus()
        viewModel.premiumStatus.observe(viewLifecycleOwner){
            if (it) binding.tvBtnPurchase.text = getText(R.string.youHavePremium)
            else binding.tvBtnPurchase.text = getText(R.string.getPremium)
        }
    }

}