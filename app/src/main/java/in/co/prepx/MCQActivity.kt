package `in`.co.prepx

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class MCQActivity : AppCompatActivity() {
    private lateinit var questionText: MaterialTextView
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var option1Text: MaterialTextView
    private lateinit var option2Text: MaterialTextView
    private lateinit var option3Text: MaterialTextView
    private lateinit var option4Text: MaterialTextView
    private lateinit var btnNext: MaterialButton
    private lateinit var btnExit: MaterialButton
    private lateinit var descriptionText: MaterialTextView
    private lateinit var descriptionCard: MaterialCardView
    private lateinit var cards: List<MaterialCardView>
    private lateinit var optionLayouts: List<View>

    private var currentQuestion = 0
    private lateinit var questions: List<Question>
    private val selectedOptions = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setImmersiveMode()

        // Get the subject from intent
        val subject = intent.getStringExtra("subject") ?: "General"
        questions = QuestionBank.getQuestions(subject)

        // Set up toolbar menu
        val toolbar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        toolbar.title = "$subject Quiz"

        toolbar.setOnMenuItemClickListener { menuItem ->
            Log.d("MCQActivity", "Menu item clicked: ${menuItem.itemId}")
            when (menuItem.itemId) {
                R.id.action_report_question -> {
                    showReportQuestionDialog()
                    true
                }

                else -> false
            }
        }

        questionText = findViewById(R.id.question_text)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        option1Text = findViewById(R.id.option1_text)
        option2Text = findViewById(R.id.option2_text)
        option3Text = findViewById(R.id.option3_text)
        option4Text = findViewById(R.id.option4_text)
        btnNext = findViewById(R.id.btn_next)
        btnExit = findViewById(R.id.btn_exit)
        descriptionText = findViewById(R.id.description_text)
        descriptionCard = findViewById(R.id.description_card)

        val card1 = option1.parent.parent as MaterialCardView
        val card2 = option2.parent.parent as MaterialCardView
        val card3 = option3.parent.parent as MaterialCardView
        val card4 = option4.parent.parent as MaterialCardView
        cards = listOf(card1, card2, card3, card4)

        optionLayouts = listOf(
            option1.parent as View,
            option2.parent as View,
            option3.parent as View,
            option4.parent as View
        )

        setOptionListeners()
        loadQuestion(currentQuestion)

        btnNext.setOnClickListener {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
                loadQuestion(currentQuestion)
            } else {
                // Quiz finished, navigate to ReviewActivity
                val intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("questions", ArrayList(questions))
                intent.putExtra("selectedOptions", selectedOptions)
                startActivity(intent)
                finish()
            }
        }

        btnExit.setOnClickListener {
            val dialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setTitle("Exit Quiz")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.primary))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.primary))
        }
    }

    private fun resetOptionBackgroundsAndTints() {
        val optionTexts = listOf(option1Text, option2Text, option3Text, option4Text)
        for ((i, option) in listOf(option1, option2, option3, option4).withIndex()) {
            val layout = optionLayouts[i]
            val optionText = optionTexts[i]
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            option.buttonTintList = null
            optionText.setTextColor(ContextCompat.getColor(this, R.color.grey))
        }
    }

    private fun animateDescriptionFadeIn() {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 400
        descriptionCard.startAnimation(fadeIn)
        descriptionCard.visibility = View.VISIBLE

        // Scroll to bottom buttons after a short delay to ensure description is visible
        val scrollView = findViewById<android.widget.ScrollView>(R.id.main_scroll_view)
        scrollView.postDelayed({
            scrollView.smoothScrollTo(0, scrollView.getChildAt(0).height)
        }, 500)
    }

    private fun resetCardElevations() {
        for (card in cards) {
            card.cardElevation = 4f
        }
    }

    private fun setOptionsEnabled(enabled: Boolean) {
        option1.isEnabled = enabled
        option2.isEnabled = enabled
        option3.isEnabled = enabled
        option4.isEnabled = enabled
        for (layout in optionLayouts) {
            layout.isEnabled = enabled
        }
    }

    private fun loadQuestion(index: Int) {
        val q = questions[index]
        questionText.text = q.question
        option1Text.text = q.options[0]
        option2Text.text = q.options[1]
        option3Text.text = q.options[2]
        option4Text.text = q.options[3]
        descriptionText.text = q.description
        descriptionCard.visibility = View.GONE // Hide description initially
        resetOptionBackgroundsAndTints()
        resetCardElevations()
        setOptionsEnabled(true)
        // Uncheck all options
        option1.isChecked = false
        option2.isChecked = false
        option3.isChecked = false
        option4.isChecked = false
    }

    private fun getCorrectOption(): RadioButton {
        return when (questions[currentQuestion].correctIndex) {
            0 -> option1
            1 -> option2
            2 -> option3
            3 -> option4
            else -> option1
        }
    }

    private fun setOptionListeners() {
        val options = listOf(option1, option2, option3, option4)
        for (option in options) {
            val clickListener = View.OnClickListener {
                val selectedOption = it as RadioButton
                val isCorrect = selectedOption == getCorrectOption()

                // Disable all options to prevent multiple clicks
                setOptionsEnabled(false)

                // Provide visual feedback
                updateOptionAppearance(selectedOption, isCorrect)

                // Vibrate if the answer is incorrect
                if (!isCorrect) {
                    vibrate()
                }

                // Show the description
                animateDescriptionFadeIn()

                // Record the selected option
                val selectedOptionIndex = options.indexOf(selectedOption)
                if (selectedOptions.size <= currentQuestion) {
                    selectedOptions.add(selectedOptionIndex)
                } else {
                    selectedOptions[currentQuestion] = selectedOptionIndex
                }
            }
            option.setOnClickListener(clickListener)
            (option.parent as View).setOnClickListener { option.performClick() }
        }
    }

    private fun updateOptionAppearance(selectedOption: RadioButton, isCorrect: Boolean) {
        val lightGreen = ContextCompat.getColor(this, R.color.light_green)
        val lightRed = ContextCompat.getColor(this, R.color.light_red)
        val darkGreen = ContextCompat.getColor(this, R.color.dark_green)
        val darkRed = ContextCompat.getColor(this, R.color.dark_red)

        val options = listOf(
            findViewById<RadioButton>(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )
        val optionTexts = listOf(
            findViewById<MaterialTextView>(R.id.option1_text),
            findViewById(R.id.option2_text),
            findViewById(R.id.option3_text),
            findViewById(R.id.option4_text)
        )

        for ((i, option) in options.withIndex()) {
            val layout = option.parent as View
            val optionText = optionTexts[i]
            if (option == selectedOption) {
                layout.setBackgroundColor(if (isCorrect) lightGreen else lightRed)
                option.buttonTintList =
                    ColorStateList.valueOf(if (isCorrect) darkGreen else darkRed)
                optionText.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                layout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                ) // transparent
                option.buttonTintList = null
                optionText.setTextColor(ContextCompat.getColor(this, R.color.grey))
            }
        }
    }

    private fun vibrate() {
        val v = getSystemService(VIBRATOR_SERVICE) as android.os.Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                android.os.VibrationEffect.createOneShot(
                    200,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            v.vibrate(200)
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun showReportQuestionDialog() {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogTheme).create()
        val dialogView = layoutInflater.inflate(R.layout.dialog_report_question, null)
        dialog.setView(dialogView)

        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.report_options_group)
        val othersInputContainer = dialogView.findViewById<View>(R.id.others_input_container)
        val othersReasonInput =
            dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.others_reason_input)

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
        dialog.show()

        // Set button text colors after the dialog is shown
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.primary))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.primary))
    }

    private fun setImmersiveMode() {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // No longer hiding status/navigation bars to keep them visible
        // WindowCompat.setDecorFitsSystemWindows(window, false) is sufficient for edge-to-edge without hiding bars
    }
}
