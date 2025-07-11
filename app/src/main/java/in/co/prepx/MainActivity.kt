package `in`.co.prepx

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.res.ColorStateList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find UI elements
        val questionText = findViewById<TextView>(R.id.question_text)
        val option1 = findViewById<RadioButton>(R.id.option1)
        val option2 = findViewById<RadioButton>(R.id.option2)
        val option3 = findViewById<RadioButton>(R.id.option3)
        val option4 = findViewById<RadioButton>(R.id.option4)
//        val expandIcon = findViewById<ImageView>(R.id.expand_icon)
        val descriptionText = findViewById<TextView>(R.id.description_text)

        // Set MCQ question, options, and description
        questionText.text = "What is the capital city of France?"
        option1.text = "A. Paris"
        option2.text = "B. London"
        option3.text = "C. Berlin"
        option4.text = "D. Madrid"
        descriptionText.text =
            "This question tests your knowledge of European capitals. France is a country in Western Europe, and its capital is a major cultural and economic center."

        // Toggle description visibility on icon click
        /*expandIcon.setOnClickListener {
            if (descriptionText.visibility == View.VISIBLE) {
                descriptionText.visibility = View.GONE
                expandIcon.setImageResource(android.R.drawable.expand_more)
                expandIcon.contentDescription = "Expand description"
            } else {
                descriptionText.visibility = View.VISIBLE
                expandIcon.setImageResource(android.R.drawable.expand_less)
                expandIcon.contentDescription = "Collapse description"
            }
        }*/

        // Add answer selection logic
        val correctOption = option1
        val options = listOf(option1, option2, option3, option4)

        val lightGreen = ContextCompat.getColor(this, R.color.light_green)
        val lightRed = ContextCompat.getColor(this, R.color.light_red)
        val darkGreen = ContextCompat.getColor(this, R.color.dark_green)
        val darkRed = ContextCompat.getColor(this, R.color.dark_red)
        val defaultBg = 0x00000000          // transparent
        val defaultTint = option1.buttonTintList

        fun resetOptionBackgroundsAndTints() {
            for (option in options) {
                option.setBackgroundColor(defaultBg)
                option.buttonTintList = defaultTint
            }
        }

        for (option in options) {
            option.setOnClickListener {
                resetOptionBackgroundsAndTints()
                if (option == correctOption) {
                    option.setBackgroundColor(lightGreen)
                    option.buttonTintList = ColorStateList.valueOf(darkGreen)
                } else {
                    option.setBackgroundColor(lightRed)
                    option.buttonTintList = ColorStateList.valueOf(darkRed)
                    // Vibrate on incorrect answer
                    val v = getSystemService(android.content.Context.VIBRATOR_SERVICE) as android.os.Vibrator
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        v.vibrate(android.os.VibrationEffect.createOneShot(200, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        v.vibrate(200)
                    }
                }
            }
        }
    }
}