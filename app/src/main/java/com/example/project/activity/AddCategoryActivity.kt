package com.example.project.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.Adapter.CategoryEditAdapter
import com.example.project.Repos.MainRepo
import com.example.project.data.CategoryModel
import com.example.project.databinding.ActivityAddCategoryBinding

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding

    private val mainRepo = MainRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategories()

        binding.addNewCategoryButton.setOnClickListener {
            val name = binding.categoryNameEditText.text.toString()

            if (name.isNotEmpty()) {

                val newCategory = CategoryModel(title = name, id = 0)
                mainRepo.saveCategory(newCategory)

            }
        }

        binding.deleteCategoryButton.setOnClickListener {
            val adapter = binding.categoriesRecyclerView.adapter as CategoryEditAdapter
            val selected = adapter.getSelectedCategory()

            if (selected != null) {
                selected.id.let { id ->
                    mainRepo.deleteCategory(id)
                }
            }
        }
        binding.back3.setOnClickListener {
            finish()
        }
    }

    private fun initCategories() {
        mainRepo.loadCategory().observeForever {
            binding.categoriesRecyclerView.layoutManager =  GridLayoutManager(this , 2)
            binding.categoriesRecyclerView.adapter = CategoryEditAdapter(it)
        }

    }
}