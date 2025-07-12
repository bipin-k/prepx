package `in`.co.prepx

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.card.MaterialCardView

class SubjectSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_selection)
        setImmersiveMode()

        // Set up toolbar menu
        val toolbar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Set up subject card click listeners
        val historyCard = findViewById<MaterialCardView>(R.id.history_card)
        val geographyCard = findViewById<MaterialCardView>(R.id.geography_card)
        val polityCard = findViewById<MaterialCardView>(R.id.polity_card)
        val mathematicsCard = findViewById<MaterialCardView>(R.id.mathematics_card)
        val scienceCard = findViewById<MaterialCardView>(R.id.science_card)

        historyCard.setOnClickListener {
            startMCQActivity("History")
        }

        geographyCard.setOnClickListener {
            startMCQActivity("Geography")
        }

        polityCard.setOnClickListener {
            startMCQActivity("Polity")
        }

        mathematicsCard.setOnClickListener {
            startMCQActivity("Mathematics")
        }

        scienceCard.setOnClickListener {
            startMCQActivity("Science")
        }
    }

    private fun startMCQActivity(subject: String) {
        val intent = Intent(this, MCQActivity::class.java)
        intent.putExtra("subject", subject)
        startActivity(intent)
    }

    private fun setImmersiveMode() {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ (API 30+)
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Android 10 and below
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }
}
