package com.example.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogedex.R
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.databinding.ActivityDogListBinding
import com.example.dogedex.dogdetail.DogDetailActivity

const val COLUMN_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {
    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        val dogAdapter = DogAdapter()
        val pbLoading = binding.pbLoading


        recycler.apply {
            layoutManager = GridLayoutManager(this@DogListActivity, COLUMN_SPAN_COUNT)
            adapter = dogAdapter
        }

        dogAdapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DogDetailActivity.DOG_KEY, it)
            startActivity(intent)
        }

        dogListViewModel.dogList.observe(this) { dogList ->
            dogAdapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> {
                    pbLoading.isVisible = true
                }

                is ApiResponseStatus.Error -> {
                    pbLoading.isVisible = false
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Success -> {
                    pbLoading.isVisible = false
                }

                else -> {
                    pbLoading.isVisible = false
                    Toast.makeText(this, "Status not implemented!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parent)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}