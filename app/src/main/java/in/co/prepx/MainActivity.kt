package `in`.co.prepx

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.res.ColorStateList
import android.app.AlertDialog
import android.widget.Button

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
        val btnNext = findViewById<Button>(R.id.btn_next)
        val btnExit = findViewById<Button>(R.id.btn_exit)
//        val expandIcon = findViewById<ImageView>(R.id.expand_icon)
        val descriptionText = findViewById<TextView>(R.id.description_text)

        // Sample questions and answers
        data class Question(val question: String, val options: List<String>, val correctIndex: Int, val description: String)
        val questions = listOf(
            Question(
                "What is the capital city of France?",
                listOf("A. Paris", "B. London", "C. Berlin", "D. Madrid"),
                0,
                "This question tests your knowledge of European capitals. France is a country in Western Europe, and its capital is a major cultural and economic center."
            ),
            Question(
                "What is the largest planet in our solar system?",
                listOf("A. Earth", "B. Jupiter", "C. Mars", "D. Venus"),
                1,
                "Jupiter is the largest planet in our solar system."
            )
        )
        var currentQuestion = 0

        val lightGreen = ContextCompat.getColor(this, R.color.light_green)
        val lightRed = ContextCompat.getColor(this, R.color.light_red)
        val darkGreen = ContextCompat.getColor(this, R.color.dark_green)
        val darkRed = ContextCompat.getColor(this, R.color.dark_red)
        val defaultBg = 0x00000000          // transparent
        val defaultTint = option1.buttonTintList

        fun resetOptionBackgroundsAndTints() {
            val options = listOf(option1, option2, option3, option4)
            for (option in options) {
                option.setBackgroundColor(defaultBg)
                option.buttonTintList = defaultTint
            }
        }

        fun loadQuestion(index: Int) {
            val q = questions[index]
            questionText.text = q.question
            option1.text = q.options[0]
            option2.text = q.options[1]
            option3.text = q.options[2]
            option4.text = q.options[3]
            descriptionText.text = q.description
            resetOptionBackgroundsAndTints()
            // Uncheck all options
            option1.isChecked = false
            option2.isChecked = false
            option3.isChecked = false
            option4.isChecked = false
        }

        // Update correctOption reference dynamically
        fun getCorrectOption(): RadioButton {
            return when (questions[currentQuestion].correctIndex) {
                0 -> option1
                1 -> option2
                2 -> option3
                3 -> option4
                else -> option1
            }
        }

        // Update answer selection logic to use getCorrectOption
        fun setOptionListeners() {
            val options = listOf(option1, option2, option3, option4)
            for (option in options) {
                option.setOnClickListener {
                    resetOptionBackgroundsAndTints()
                    val correctOption = getCorrectOption()
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
        setOptionListeners()
        loadQuestion(currentQuestion)

        btnNext.setOnClickListener {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
                loadQuestion(currentQuestion)
            } else {
                // Optionally show a message or loop to first question
                currentQuestion = 0
                loadQuestion(currentQuestion)
            }
        }

        btnExit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Exit PrepX!")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()
        }
    }
}