package `in`.co.prepx

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.button.MaterialButton

class MCQActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setImmersiveMode()
        
        // Get the subject from intent
        val subject = intent.getStringExtra("subject") ?: "General"
        
        // Set up toolbar menu
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        toolbar.title = "$subject Quiz"
        
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_report_question -> {
                    showReportQuestionDialog()
                    true
                }
                else -> false
            }
        }

        val questionText = findViewById<MaterialTextView>(R.id.question_text)
        val option1 = findViewById<RadioButton>(R.id.option1)
        val option2 = findViewById<RadioButton>(R.id.option2)
        val option3 = findViewById<RadioButton>(R.id.option3)
        val option4 = findViewById<RadioButton>(R.id.option4)
        val option1Text = findViewById<MaterialTextView>(R.id.option1_text)
        val option2Text = findViewById<MaterialTextView>(R.id.option2_text)
        val option3Text = findViewById<MaterialTextView>(R.id.option3_text)
        val option4Text = findViewById<MaterialTextView>(R.id.option4_text)
        val btnNext = findViewById<MaterialButton>(R.id.btn_next)
        val btnExit = findViewById<MaterialButton>(R.id.btn_exit)
        val descriptionText = findViewById<MaterialTextView>(R.id.description_text)

        data class Question(
            val question: String,
            val options: List<String>,
            val correctIndex: Int,
            val description: String
        )

        val questions = when (subject) {
            "History" -> listOf(
                Question(
                    "Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire? Who was the first Emperor of the Maurya Empire?",
                    listOf("Chandragupta Maurya", "Ashoka", "Bindusara", "Samprati"),
                    0,
                    "Chandragupta Maurya was the founder and first emperor of the Maurya Empire."
                ),
                Question(
                    "In which year did India gain independence from British rule?",
                    listOf("1945", "1946", "1947", "1948"),
                    2,
                    "India gained independence on August 15, 1947."
                ),
                Question(
                    "Who was known as the 'Father of the Nation' in India?",
                    listOf("Jawaharlal Nehru", "Mahatma Gandhi", "Subhas Chandra Bose", "Sardar Patel"),
                    1,
                    "Mahatma Gandhi is known as the 'Father of the Nation' in India."
                )
            )
            "Geography" -> listOf(
                Question(
                    "What is the capital of India?",
                    listOf("Mumbai", "Delhi", "Kolkata", "Chennai"),
                    1,
                    "New Delhi is the capital of India."
                ),
                Question(
                    "Which is the longest river in India?",
                    listOf("Ganga", "Yamuna", "Brahmaputra", "Godavari"),
                    0,
                    "The Ganga is the longest river in India."
                ),
                Question(
                    "Which mountain range runs along the northern border of India?",
                    listOf("Western Ghats", "Eastern Ghats", "Himalayas", "Aravalli"),
                    2,
                    "The Himalayas run along the northern border of India."
                )
            )
            "Polity" -> listOf(
                Question(
                    "How many fundamental rights are guaranteed by the Indian Constitution?",
                    listOf("5", "6", "7", "8"),
                    1,
                    "The Indian Constitution guarantees 6 fundamental rights."
                ),
                Question(
                    "Who is the head of the Indian government?",
                    listOf("President", "Prime Minister", "Chief Justice", "Speaker"),
                    1,
                    "The Prime Minister is the head of the Indian government."
                ),
                Question(
                    "In which year was the Indian Constitution adopted?",
                    listOf("1947", "1949", "1950", "1951"),
                    1,
                    "The Indian Constitution was adopted on November 26, 1949."
                )
            )
            "Mathematics" -> listOf(
                Question(
                    "What is the value of π (pi) to two decimal places?",
                    listOf("3.12", "3.14", "3.16", "3.18"),
                    1,
                    "The value of π is approximately 3.14159..."
                ),
                Question(
                    "What is the square root of 144?",
                    listOf("10", "11", "12", "13"),
                    2,
                    "12 × 12 = 144, so the square root of 144 is 12."
                ),
                Question(
                    "What is 15% of 200?",
                    listOf("25", "30", "35", "40"),
                    1,
                    "15% of 200 = (15/100) × 200 = 30."
                )
            )
            "Science" -> listOf(
                Question(
                    "What is the chemical symbol for gold?",
                    listOf("Ag", "Au", "Fe", "Cu"),
                    1,
                    "Au is the chemical symbol for gold (from Latin 'aurum')."
                ),
                Question(
                    "Which planet is known as the Red Planet?",
                    listOf("Venus", "Mars", "Jupiter", "Saturn"),
                    1,
                    "Mars is known as the Red Planet due to its reddish appearance."
                ),
                Question(
                    "What is the hardest natural substance on Earth?",
                    listOf("Steel", "Iron", "Diamond", "Granite"),
                    2,
                    "Diamond is the hardest natural substance on Earth."
                )
            )
            else -> listOf(
                Question(
                    "What is the capital city of France?",
                    listOf("Paris", "London", "Berlin", "Madrid"),
                    0,
                    "Paris is the capital city of France."
                ),
                Question(
                    "What is the largest planet in our solar system?",
                    listOf("Earth", "Jupiter", "Mars", "Venus"),
                    1,
                    "Jupiter is the largest planet in our solar system."
                )
            )
        }
        var currentQuestion = 0

        val lightGreen = ContextCompat.getColor(this, R.color.light_green)
        val lightRed = ContextCompat.getColor(this, R.color.light_red)
        val darkGreen = ContextCompat.getColor(this, R.color.dark_green)
        val darkRed = ContextCompat.getColor(this, R.color.dark_red)
        val defaultBg = 0x00000000          // transparent
        val defaultTint = option1.buttonTintList

        val card1 = option1.parent.parent as MaterialCardView
        val card2 = option2.parent.parent as MaterialCardView
        val card3 = option3.parent.parent as MaterialCardView
        val card4 = option4.parent.parent as MaterialCardView
        val cards = listOf(card1, card2, card3, card4)

        val optionLayouts = listOf(
            option1.parent as View,
            option2.parent as View,
            option3.parent as View,
            option4.parent as View
        )

        fun resetOptionBackgroundsAndTints() {
            val optionTexts = listOf(option1Text, option2Text, option3Text, option4Text)
            for ((i, option) in listOf(option1, option2, option3, option4).withIndex()) {
                val layout = optionLayouts[i]
                val optionText = optionTexts[i]
                layout.setBackgroundColor(defaultBg)
                option.buttonTintList = defaultTint
                optionText.setTextColor(ContextCompat.getColor(this, R.color.grey))
            }
        }

        fun animateDescriptionFadeIn() {
            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.duration = 400
            descriptionText.startAnimation(fadeIn)
            descriptionText.visibility = View.VISIBLE
            
            // Scroll to bottom buttons after a short delay to ensure description is visible
            val scrollView = findViewById<android.widget.ScrollView>(R.id.main_scroll_view)
            scrollView.postDelayed({
                scrollView.smoothScrollTo(0, scrollView.getChildAt(0).height)
            }, 500)
        }

        fun resetCardElevations() {
            for (card in cards) {
                card.cardElevation = 4f
            }
        }

        fun setOptionsEnabled(enabled: Boolean) {
            option1.isEnabled = enabled
            option2.isEnabled = enabled
            option3.isEnabled = enabled
            option4.isEnabled = enabled
            for (layout in optionLayouts) {
                layout.isEnabled = enabled
            }
        }

        fun loadQuestion(index: Int) {
            val q = questions[index]
            questionText.text = q.question
            option1Text.text = q.options[0]
            option2Text.text = q.options[1]
            option3Text.text = q.options[2]
            option4Text.text = q.options[3]
            descriptionText.text = q.description
            descriptionText.visibility = View.GONE // Hide description initially
            resetOptionBackgroundsAndTints()
            resetCardElevations()
            setOptionsEnabled(true)
            // Uncheck all options
            option1.isChecked = false
            option2.isChecked = false
            option3.isChecked = false
            option4.isChecked = false
        }

        fun getCorrectOption(): RadioButton {
            return when (questions[currentQuestion].correctIndex) {
                0 -> option1
                1 -> option2
                2 -> option3
                3 -> option4
                else -> option1
            }
        }

        fun setOptionListeners() {
            val options = listOf(option1, option2, option3, option4)
            val optionTexts = listOf(option1Text, option2Text, option3Text, option4Text)
            for ((i, option) in options.withIndex()) {
                val layout = optionLayouts[i]
                val card = cards[i]
                val optionText = optionTexts[i]
                val clickListener = View.OnClickListener {
                    resetOptionBackgroundsAndTints()
                    resetCardElevations()
                    val correctOption = getCorrectOption()
                    card.cardElevation = 12f
                    if (option == correctOption) {
                        layout.setBackgroundColor(lightGreen)
                        option.buttonTintList = ColorStateList.valueOf(darkGreen)
                        optionText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                    } else {
                        layout.setBackgroundColor(lightRed)
                        option.buttonTintList = ColorStateList.valueOf(darkRed)
                        optionText.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                        // Vibrate on incorrect answer
                        val v = getSystemService(android.content.Context.VIBRATOR_SERVICE) as android.os.Vibrator
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            v.vibrate(android.os.VibrationEffect.createOneShot(200, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            v.vibrate(200)
                        }
                    }
                    setOptionsEnabled(false)
                    animateDescriptionFadeIn()
                    option.isChecked = true
                }
                option.setOnClickListener(clickListener)
                layout.setOnClickListener(clickListener)
            }
        }
        setOptionListeners()
        loadQuestion(currentQuestion)

        btnNext.setOnClickListener {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
                loadQuestion(currentQuestion)
            } else {
                currentQuestion = 0
                loadQuestion(currentQuestion)
            }
        }

        btnExit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Exit Quiz")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun showReportQuestionDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val dialogView = layoutInflater.inflate(R.layout.dialog_report_question, null)
        dialog.setView(dialogView)
        
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.report_options_group)
        val othersInputContainer = dialogView.findViewById<View>(R.id.others_input_container)
        val othersReasonInput = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.others_reason_input)
        
        // Add character limit enforcement
        othersReasonInput.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: android.text.Editable?) {
                if (s != null && s.length > 100) {
                    // Remove characters beyond the 100 character limit
                    othersReasonInput.setText(s.subSequence(0, 100))
                    othersReasonInput.setSelection(100)
                }
            }
        })
        
        // Handle radio button selection
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.option_others) {
                othersInputContainer.visibility = View.VISIBLE
            } else {
                othersInputContainer.visibility = View.GONE
            }
        }
        
        dialog.setTitle("Report Question")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Report") { _, _ ->
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedReason = when (selectedId) {
                R.id.option_incorrect_text -> "Incorrect Question Text"
                R.id.option_incorrect_options -> "Incorrect Options"
                R.id.option_wrong_answer -> "Wrong Answer"
                R.id.option_others -> {
                    val othersText = othersReasonInput.text.toString().trim()
                    if (othersText.isEmpty()) {
                        "Others"
                    } else {
                        "Others: $othersText"
                    }
                }
                else -> "Others"
            }
            
            // Handle the report - you can add your reporting logic here
            // For now, just show a confirmation
            AlertDialog.Builder(this)
                .setTitle("Question Reported")
                .setMessage("Thank you for your feedback. We will review this question.")
                .setPositiveButton("OK", null)
                .show()
        }
        
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }
        
        dialog.show()
    }

    private fun setImmersiveMode() {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ (API 30+)
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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