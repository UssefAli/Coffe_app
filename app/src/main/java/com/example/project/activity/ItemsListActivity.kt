package com.example.project.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.Adapter.itemListCategoryAdapter
import com.example.project.Repos.MainRepo
import com.example.project.databinding.ActivityItemsListBinding

class ItemsListActivity : AppCompatActivity() {

    lateinit var binding: ActivityItemsListBinding
    private val mainRepo = MainRepo()
    private var id: String = ""
    private var title: String= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundles()
        initList()
    }

    private fun initList() {
        binding.apply {
            progressBar3.visibility = View.VISIBLE
            mainRepo.loadItemsPerCategory(id).observe(this@ItemsListActivity , Observer{
                listView.layoutManager =
                GridLayoutManager(this@ItemsListActivity , 2)
                listView.adapter = itemListCategoryAdapter(it)
                progressBar3.visibility = View.GONE
            })
            textView9.setOnClickListener { finish() }


        }
    }

    private fun getBundles() {
        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!
        binding.textView10.text = title
    }
}