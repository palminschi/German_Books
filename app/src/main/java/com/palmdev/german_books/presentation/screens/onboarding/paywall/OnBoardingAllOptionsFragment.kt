package com.palmdev.german_books.presentation.screens.onboarding.paywall

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.google.common.collect.ImmutableList
import com.palmdev.german_books.BuildConfig
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentPurchasesAllBinding
import com.palmdev.german_books.legacy.data.User
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.SUPPORT_EMAIL
import java.util.*

class OnBoardingAllOptionsFragment :
    BaseFragment<FragmentPurchasesAllBinding>(FragmentPurchasesAllBinding::inflate),
    PurchasesUpdatedListener {

    private var billingClient: BillingClient? = null
    private val skuList =
        listOf("yearly_subscription_trial", "three_months_subscription", "monthly_subscription")
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        findNavController().popBackStack()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (User(requireContext()).getPremiumStatus(requireContext())) {
            findNavController().navigate(R.id.homeFragment)
            return
        }

        binding.btnContactEmail.setOnClickListener {
            val subject =
                "Support ${getString(R.string.app_name)} | ${BuildConfig.VERSION_NAME}"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$SUPPORT_EMAIL?subject=$subject")
            }
            startActivity(intent)
        }

        initBillingClient()

        binding.btnClose.setOnClickListener { findNavController().navigate(R.id.homeFragment) }
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

        // Monthly
        var basicMonthlyPrice = 0L
        val monthlyProd = productsDetails.find { it.productId == skuList[2] }
        val monthlyPrice =
            monthlyProd?.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()
        if (monthlyPrice == null) {
            binding.cardMonthly.visibility = View.GONE
        } else {
            binding.cardMonthly.visibility = View.VISIBLE
            basicMonthlyPrice = monthlyPrice.priceAmountMicros
            val currencyCode = monthlyPrice.priceCurrencyCode

            val formattedTotalPrice =
                "${formatPrice(basicMonthlyPrice)} ${formatCurrencyCode(currencyCode)}"

            binding.tvMonthlyTotalPrice.text = formattedTotalPrice
            binding.tvMonthlyPerMonth.text = "$formattedTotalPrice ${getString(R.string.perMonth)}"

            // Listener
            binding.cardMonthly.setOnClickListener {
                binding.checkboxMonthly.isChecked = true
                binding.checkboxThreeMonths.isChecked = false
                binding.checkboxYearly.isChecked = false
            }
            binding.checkboxMonthly.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        binding.btnBuy.textButton = getString(R.string.getPremium)
                        binding.cardMonthly.setCardBackgroundColor(
                            resources.getColor(R.color.blue_accent, null)
                        )
                        binding.btnBuy.setOnClickListener {
                            launchPurchaseFlow(monthlyProd)
                        }
                    }
                    false -> {
                        binding.cardMonthly.setCardBackgroundColor(
                            resources.getColor(R.color.white, null)
                        )
                    }
                }
            }
        }

        // Six Months
        val threeMonthsProd = productsDetails.find { it.productId == skuList[1] }
        val threeMonthsPrice =
            threeMonthsProd?.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()
        if (threeMonthsPrice == null) {
            binding.cardThreeMonths.visibility = View.GONE
        } else {
            binding.cardThreeMonths.visibility = View.VISIBLE

            val priceLong = threeMonthsPrice.priceAmountMicros
            val currencyCode = threeMonthsPrice.priceCurrencyCode
            val formattedTotalPrice =
                "${formatPrice(priceLong)} ${formatCurrencyCode(currencyCode)}"

            binding.tvThreeMonthsTotalPrice.text = formattedTotalPrice
            binding.tvThreeMonthsPerMonth.text =
                "${formatPrice(priceLong / 3)} ${formatCurrencyCode(currencyCode)} ${getString(R.string.perMonth)}"

            // Listeners
            binding.cardThreeMonths.setOnClickListener {
                binding.checkboxMonthly.isChecked = false
                binding.checkboxThreeMonths.isChecked = true
                binding.checkboxYearly.isChecked = false
            }
            binding.checkboxThreeMonths.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        binding.btnBuy.textButton = getString(R.string.getPremium)
                        binding.cardThreeMonths.setCardBackgroundColor(
                            resources.getColor(R.color.blue_accent, null)
                        )
                        binding.btnBuy.setOnClickListener {
                            launchPurchaseFlow(threeMonthsProd)
                        }
                    }
                    false -> {
                        binding.cardThreeMonths.setCardBackgroundColor(
                            resources.getColor(R.color.white, null)
                        )
                    }
                }
            }
        }

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
                "${formatPrice(basicMonthlyPrice * 12)} ${formatCurrencyCode(currencyCode)}"
            binding.tvYearlyPerMonth.text =
                "${formatPrice(priceLong / 12)} ${formatCurrencyCode(currencyCode)} ${getString(R.string.perMonth)}"

            // Listeners
            binding.btnBuy.setOnClickListener {
                launchPurchaseFlow(yearlyProd)
            }
            binding.cardYearly.setOnClickListener {
                binding.checkboxMonthly.isChecked = false
                binding.checkboxThreeMonths.isChecked = false
                binding.checkboxYearly.isChecked = true
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
                    .build(),
                //Product 2
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(skuList[1])
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                //Product 3
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(skuList[2])
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
                    }, 500
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
                this.findNavController().navigate(R.id.homeFragment)
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