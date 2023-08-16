package com.palmdev.german_books.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Funding Choice
    private var mConsentInformation: ConsentInformation? = null
    private var mConsentForm: ConsentForm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.let { NavigationUI.setupWithNavController(it, navController) }

        // Init Funding Choice
        val params = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
            .build()
        mConsentInformation = UserMessagingPlatform.getConsentInformation(this)
        mConsentInformation?.requestConsentInfoUpdate(
            this,
            params,
            { // The consent information state was updated.
                // You are now ready to check if a form is available.
                if (mConsentInformation?.isConsentFormAvailable == true) {
                    loadForm()
                }
            },
            {
                // Handle the error.
            })
    }

    fun navigateToNavBarDestination(destinationId: Int) {
        binding.bottomNavigationView.selectedItemId = destinationId
    }

    // Funding Choice Form
    private fun loadForm() {
        UserMessagingPlatform.loadConsentForm(
            this,
            { consentForm ->
                this.mConsentForm = consentForm
                if (mConsentInformation!!.consentStatus == ConsentInformation.ConsentStatus.REQUIRED ||
                    mConsentInformation!!.consentStatus == ConsentInformation.ConsentStatus.UNKNOWN
                ) {
                    consentForm.show(
                        this@MainActivity
                    ) { // Handle dismissal by reloading form.
                        loadForm()
                    }
                }
            }
        ) {
            // Handle the error
        }
    }

    fun setBottomNavigationVisibility(visible: Boolean) {
        if (this::binding.isInitialized) {
            binding.bottomNavigationView.visibility = if (visible) View.VISIBLE else View.GONE
        }

    }
}




