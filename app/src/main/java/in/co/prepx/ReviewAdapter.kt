package `in`.co.prepx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import `in`.co.prepx.R

class ReviewAdapter(private val questions: List<Question>, private val selectedOptions: List<Int>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.review_question_text)
        val userAnswerText: TextView = itemView.findViewById(R.id.review_user_answer_text)
        val correctAnswerText: TextView = itemView.findViewById(R.id.review_correct_answer_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review_answer, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val question = questions[position]
        val userAnswerIndex = selectedOptions[position]

        holder.questionText.text = "Q${position + 1}: ${question.question}"

        val userAnswer = if (userAnswerIndex != -1) question.options[userAnswerIndex] else "Not Answered"
        holder.userAnswerText.text = "Your Answer: $userAnswer"

        holder.correctAnswerText.text = "Correct Answer: ${question.options[question.correctIndex]}"
    }

    override fun getItemCount(): Int = questions.size
}