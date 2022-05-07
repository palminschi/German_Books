package com.palmdev.german_books

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.palmdev.german_books.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Funding Choice
    private var mConsentInformation: ConsentInformation? = null
    private var mConsentForm: ConsentForm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Init navigation
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView?.setupWithNavController(navController)

        // Funding Choice
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


    // Funding Choice Form
    private fun loadForm() {
        UserMessagingPlatform.loadConsentForm(
            this,
            { consentForm ->
                this.mConsentForm = consentForm
                if (mConsentInformation!!.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
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

    companion object {

        private var bottomNavigationView: BottomNavigationView? = null

        fun hideBottomNavigation() {
            bottomNavigationView?.visibility = View.GONE
        }

        fun showBottomNavigation() {
            bottomNavigationView?.visibility = View.VISIBLE
        }
    }
}