package com.example.dogedex

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dogedex.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWholeImageBinding

    companion object {
        const val PHOTO_URI = "PHOTO_URI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI)
        val uri = Uri.parse(photoUri)
        val path = uri.path
        if (path == null) {
            Toast.makeText(this, "Error showing image, no photo available!", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }

        Glide.with(this)
            .load(File(path))
            .into(binding.wholeImage)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}