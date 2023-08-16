package com.palmdev.german_books.presentation.screens.purchases

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.common.collect.ImmutableList
import com.palmdev.german_books.BuildConfig
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentPurchasesDiscountBinding
import com.palmdev.german_books.legacy.data.User
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.SUPPORT_EMAIL
import dagger.hilt.android.AndroidEntryPoint
import java.util.Currency
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DiscountPurchaseFragment :
    BaseFragment<FragmentPurchasesDiscountBinding>(FragmentPurchasesDiscountBinding::inflate),
    PurchasesUpdatedListener {

    private var billingClient: BillingClient? = null
    private val skuList = listOf("monthly_subscription_discount")
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private var countDownTimer: CountDownTimer? = null

    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        findNavController().navigate(R.id.homeFragment)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

    private fun startTimer() {

        countDownTimer?.cancel()
        val duration = 3 * 60 * 1000L

        countDownTimer = object : CountDownTimer(duration, 1000) { // Update text every second
            override fun onTick(millisUntilFinished: Long) {
                val hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(
                        1
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(
                        1
                    )
                )
                binding.tvTimer.text = hms
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00:00"
            }
        }.start()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (User(requireContext()).getPremiumStatus(requireContext())) {
            findNavController().navigate(R.id.purchasesEndFragment)
            return
        }

        startTimer()

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
        val monthlyProd = productsDetails.find { it.productId == skuList.first() }
        val monthlyPrice =
            monthlyProd?.subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()

        if (monthlyPrice != null) {
            val basicMonthlyPrice = monthlyPrice.priceAmountMicros
            val oldMonthlyPrice = basicMonthlyPrice * 3
            val currencyCode = monthlyPrice.priceCurrencyCode

            val formattedTotalPrice =
                "${formatPrice(basicMonthlyPrice)} ${formatCurrencyCode(currencyCode)}"
            val formattedOldPrice =
                "${formatPrice(oldMonthlyPrice)} ${formatCurrencyCode(currencyCode)}"

            binding.tvNewPrice.text = formattedTotalPrice
            binding.tvOldPrice.text = formattedOldPrice

            // Listener
            binding.cardMonthly.setOnClickListener {
                launchPurchaseFlow(monthlyProd)
            }
            binding.btnBuy.setOnClickListener {
                launchPurchaseFlow(monthlyProd)
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
                    .setProductId(skuList.first())
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
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
                    100
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