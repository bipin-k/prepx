package `in`.co.prepx

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileOutputStream

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val scoreText = findViewById<MaterialTextView>(R.id.score_text)
        val restartButton = findViewById<MaterialButton>(R.id.restart_button)
        val shareButton = findViewById<MaterialButton>(R.id.share_button)

        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val correctAnswers = intent.getIntExtra("correctAnswers", 0)

        scoreText.text = "You scored $correctAnswers out of $totalQuestions"

        restartButton.setOnClickListener {
            val intent = Intent(this, SubjectSelectionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        shareButton.setOnClickListener {
            val rootView = window.decorView.rootView
            val bitmap = takeScreenshot(rootView)
            val uri = saveBitmap(bitmap)
            shareScreenshot(uri)
        }
    }

    private fun takeScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): Uri? {
        val imagePath = File(cacheDir, "images")
        imagePath.mkdirs()
        val file = File(imagePath, "screenshot.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
        fileOutputStream.close()
        return FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
    }

    private fun shareScreenshot(uri: Uri?) {
        if (uri != null) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(intent, "Share Score"))
        }
    }
}