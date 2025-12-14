package com.example.project.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.semantics.text
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.project.Adapter.CategoryAdapter
import com.example.project.Adapter.PopularAdapter
import com.example.project.Repos.MainRepo
import com.example.project.databinding.ActivityMainBinding
import androidx.core.content.edit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainRepo= MainRepo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "Guest"

        handleIntent(intent)


        binding.addCategory.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }
        binding.EditItem.setOnClickListener {
            startActivity(Intent(this, EditItemActivity::class.java))
        }

        initBanner()
        initCategory()
        initPopular()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW && intent.data != null) {

//            val fullUrl = intent.data.toString()
//            Toast.makeText(this, "URL: $fullUrl", Toast.LENGTH_LONG).show()

            val uid = intent.data?.getQueryParameter("uid")

            if (!uid.isNullOrEmpty()) {
                binding.textView.text = "UID: $uid"
                Toast.makeText(this, "Success! UID: $uid", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "URL received, but uid was empty", Toast.LENGTH_LONG).show()
            }
        } else {}
    }

    private fun initPopular() {
        binding.progressBar5.visibility = View.VISIBLE
        mainRepo.loadPopular().observeForever {

            binding.PopularRecycle.layoutManager = GridLayoutManager(this , 2)
            binding.PopularRecycle.adapter = PopularAdapter(it)
            binding.progressBar5.visibility = View.GONE
        }

    }

    private fun initCategory() {
        binding.progressBar4.visibility = View.VISIBLE
        mainRepo.loadCategory().observeForever {
            binding.CategoryRecycle.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL , false
            )
            binding.CategoryRecycle.adapter = CategoryAdapter(it)
            binding.progressBar4.visibility = View.GONE
        }

    }

    private fun initBanner() {
        binding.progressBar2.visibility = View.VISIBLE
        mainRepo.loadBanner().observeForever {
            Glide.with(this@MainActivity).load(it[0].url).into(binding.banner)
            binding.progressBar2.visibility = View.GONE
        }

    }
}