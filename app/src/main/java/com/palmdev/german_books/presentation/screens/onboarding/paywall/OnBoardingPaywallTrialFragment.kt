package com.palmdev.german_books.presentation.screens.onboarding.paywall

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.bumptech.glide.Glide
import com.google.common.collect.ImmutableList
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentPurchasesTrialBinding
import com.palmdev.german_books.legacy.data.User
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.CAUGHT_ERROR
import java.util.*

class OnBoardingPaywallTrialFragment :
    BaseFragment<FragmentPurchasesTrialBinding>(FragmentPurchasesTrialBinding::inflate),
    PurchasesUpdatedListener {

    private var billingClient: BillingClient? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val skuList = listOf("yearly_subscription_trial")


    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.img)
            .load(R.drawable.img_happy_people)
            .into(binding.img)

        val isPremium = User(requireContext()).getPremiumStatus(requireContext())
        if (isPremium) {
            findNavController().navigate(R.id.homeFragment)
            return
        }

        initBillingClient()

        binding.btnAllOptions.setOnClickListener { findNavController().navigate(R.id.onBoardingAllOptionsFragment) }
        binding.btnClose.setOnClickListener { findNavController().navigate(R.id.onBoardingAllOptionsFragment) }
    }

    override fun onResume() {
        super.onResume()
        handlePendingTransaction()
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                verifySubPurchase(purchase)
            }
        }
    }

    private fun initBillingClient() {
        //Initialize a BillingClient with PurchasesUpdatedListener onCreate method
        billingClient = BillingClient.newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener(this)
            .build()

        //start the connection after initializing the billing client
        establishConnection()
    }

    private fun establishConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    initProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                try {
                    establishConnection()
                } catch (e: Exception) {
                    e.message?.let { Log.e(CAUGHT_ERROR, it) }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showProducts(productsDetails: List<ProductDetails>) {

        // Yearly
        val yearlyProd = productsDetails.find { it.productId == skuList[0] }
        val yearlyPrice =
            yearlyProd?.subscriptionOfferDetails?.last()?.pricingPhases?.pricingPhaseList?.last()
        if (yearlyPrice == null) {
            binding.cardYearly.visibility = View.GONE
        } else {
            // with trial
            binding.tvTrial.visibility = View.VISIBLE

            binding.cardYearly.visibility = View.VISIBLE
            val priceLong = yearlyPrice.priceAmountMicros
            val currencyCode = yearlyPrice.priceCurrencyCode
            val formattedTotalPrice =
                "${formatPrice(priceLong)} ${formatCurrencyCode(currencyCode)}"

            binding.tvYearlyTotalPrice.text = formattedTotalPrice
            binding.tvYearlyOldPrice.text =
                "${formatPrice(priceLong * 2)} ${formatCurrencyCode(currencyCode)}"
            binding.tvYearlyPerMonth.text =
                "${formatPrice(priceLong / 12)} ${formatCurrencyCode(currencyCode)} ${getString(R.string.perMonth)}"

            // Listeners
            binding.btnBuy.setOnClickListener {
                launchPurchaseFlow(yearlyProd)
            }
            binding.cardYearly.setOnClickListener {
                launchPurchaseFlow(yearlyProd)
            }
            binding.checkboxYearly.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        binding.btnBuy.textButton = getString(R.string.startFreeTrial)
                        binding.cardYearly.setCardBackgroundColor(
                            resources.getColor(R.color.blue_accent, null)
                        )
                        binding.btnBuy.setOnClickListener {
                            launchPurchaseFlow(yearlyProd)
                        }
                    }
                    false -> {
                        binding.cardYearly.setCardBackgroundColor(
                            resources.getColor(R.color.white, null)
                        )
                    }
                }
            }
        }
    }

    private fun formatPrice(amountMicros: Long): String {
        return String.format("%.2f", amountMicros / 1000000.0)

    }

    private fun formatCurrencyCode(currencyCode: String): String {
        return try {
            Currency.getInstance(currencyCode).symbol
        } catch (e: Exception) {
            currencyCode
        }
    }

    private fun initProducts() {
        val productList: ImmutableList<QueryProductDetailsParams.Product> =
            ImmutableList.of(
                //Product 1
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(skuList[0])
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient?.queryProductDetailsAsync(
            params
        ) { billingResult, prodDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                handler.postDelayed(
                    {
                        try {
                            showProducts(prodDetailsList)
                        } catch (e: Exception) {
                            e.message?.let { Log.e("ON_BOARDING_ERROR", it) }
                        }
                    },
                    500
                )
            }

        }
    }

    private fun launchPurchaseFlow(productDetails: ProductDetails) {
        assert(productDetails.subscriptionOfferDetails != null)
        val productDetailsParamsList = ImmutableList.of(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(productDetails.subscriptionOfferDetails!![0].offerToken)
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        billingClient?.launchBillingFlow(requireActivity(), billingFlowParams)
    }

    private fun verifySubPurchase(purchases: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams
            .newBuilder()
            .setPurchaseToken(purchases.purchaseToken)
            .build()
        billingClient?.acknowledgePurchase(
            acknowledgePurchaseParams
        ) { billingResult: BillingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                // set user got premium
                User(requireContext()).setPremiumStatus(true)
                this.findNavController().navigate(R.id.purchasesEndFragment)
            }
        }
    }

    private fun handlePendingTransaction() {
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
        ) { billingResult: BillingResult, list: List<Purchase> ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in list) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                        verifySubPurchase(purchase)
                    }
                }
            }
        }
    }
}