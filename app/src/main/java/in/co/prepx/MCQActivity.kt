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
        
        // Set up toolbar menu
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        
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

        val questions = listOf(
            Question(
                "What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France? What is the capital city of France?",
                listOf("Paris Paris Paris Paris Paris Paris Paris Paris Paris Paris Paris", "London", "Berlin", "Madrid"),
                0,
                "This question tests your knowledge of European capitals. France is a country in Western Europe, and its capital is a major cultural and economic center."
            ),
            Question(
                "What is the largest planet in our solar system?",
                listOf("Earth", "Jupiter", "Mars", "Venus"),
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