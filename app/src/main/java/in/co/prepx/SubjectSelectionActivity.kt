package `in`.co.prepx

import android.content.Intent
import android.os.Bundle
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
        val subjectMap = mapOf(
            R.id.history_card to "History",
            R.id.geography_card to "Geography",
            R.id.polity_card to "Polity",
            R.id.mathematics_card to "Mathematics",
            R.id.science_card to "Science"
        )

        for ((cardId, subject) in subjectMap) {
            findViewById<MaterialCardView>(cardId).setOnClickListener {
                startMCQActivity(subject)
            }
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

        // No longer hiding status/navigation bars to keep them visible
        // WindowCompat.setDecorFitsSystemWindows(window, false) is sufficient for edge-to-edge without hiding bars
    }
}
