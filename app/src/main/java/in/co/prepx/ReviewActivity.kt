package `in`.co.prepx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `in`.co.prepx.R
import `in`.co.prepx.Question
import com.google.android.material.appbar.MaterialToolbar

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reviewRecyclerView = findViewById<RecyclerView>(R.id.review_recycler_view)
        reviewRecyclerView.layoutManager = LinearLayoutManager(this)

        val questions = intent.getSerializableExtra("questions") as? ArrayList<Question>
        val selectedOptions = intent.getSerializableExtra("selectedOptions") as? ArrayList<Int>

        if (questions != null && selectedOptions != null) {
            val adapter = ReviewAdapter(questions, selectedOptions)
            reviewRecyclerView.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}