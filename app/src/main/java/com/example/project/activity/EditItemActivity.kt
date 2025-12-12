package com.example.project.activity

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.Adapter.ItemEditAdaptor
import com.example.project.Repos.MainRepo
import com.example.project.data.CategoryModel
import com.example.project.data.ItemModel
import com.example.project.databinding.ActivityEditeItemBinding

class EditItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditeItemBinding
    private val categoryList = mutableListOf<CategoryModel>()
    private val categoryTitles = mutableListOf<String>()

    val picUrlList = ArrayList<String>()

    private val defaultImageUrl = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1748598194/5_gdnijm.png"

    init {
        picUrlList.add(defaultImageUrl)
    }
    private val mainRepo = MainRepo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditeItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initItems()
        initSpinnerCategories()


        binding.addItemButton.setOnClickListener {
            val name = binding.itemNameEditText.text.toString()
            val price = binding.itemPriceEditText.text.toString()
            val rating = binding.itemRatingEditText.text.toString()
            val description = binding.itemDescriptionEditText.text.toString()
            val selectedCategoryPosition = binding.categorySpinner.selectedItemPosition

            val selectedCategory = categoryList[selectedCategoryPosition]

            val categoryId = selectedCategory.id

            if(name.isNotEmpty() && price.isNotEmpty() && rating.isNotEmpty() && description.isNotEmpty()){
                val newItem = ItemModel(
                    Id = 0,
                    title = name,
                    description = description,
                    price = price.toDouble(),
                    rating = rating.toDouble(),
                    categoryId = categoryId.toString(),
                    picUrl = picUrlList,
                    extra = name
                )
                mainRepo.saveItem(newItem)
            }

        }
        binding.deleteItemButton.setOnClickListener {
            val adapter = binding.itemsRecyclerView.adapter as ItemEditAdaptor
            val selected = adapter.getSelectedItem()

            if (selected != null) {
                selected.Id.let { id ->
                    mainRepo.deleteItem(id)
                }
            }
        }
        binding.updateItemButton.setOnClickListener {
            val adapter = binding.itemsRecyclerView.adapter as ItemEditAdaptor
            val selected = adapter.getSelectedItem()
            var name = binding.itemNameEditText.text.toString()
            var price = binding.itemPriceEditText.text.toString()
            var rating = binding.itemRatingEditText.text.toString()
            var description = binding.itemDescriptionEditText.text.toString()
            val selectedCategoryPosition = binding.categorySpinner.selectedItemPosition
            val selectedCategory = categoryList[selectedCategoryPosition]
            val categoryId = selectedCategory.id

            if(selected != null){
                if(name.isEmpty())
                    name = selected.title
                if(description.isEmpty() )
                    description = selected.description
                if(rating.isEmpty() )
                    rating = selected.rating.toString()
                if(price.isEmpty() )
                    price = selected.price.toString()
                val newItem = ItemModel(
                    Id = selected.Id,
                    title = name,
                    description = description,
                    price = price.toDouble(),
                    rating = rating.toDouble(),
                    categoryId = categoryId.toString(),
                    picUrl = selected.picUrl,
                    extra = selected.extra,
                )
                mainRepo.updateItem(newItem)

            }
        }
        binding.back4.setOnClickListener { finish() }

    }

    private fun initItems() {
        mainRepo.loadItems().observeForever {
            binding.itemsRecyclerView.layoutManager =  GridLayoutManager(this , 2)
            binding.itemsRecyclerView.adapter = ItemEditAdaptor(it)
        }
    }

    private fun initSpinnerCategories() {

        mainRepo.getCategories { categories ->
            if (categories.isNotEmpty()) {

                categoryList.clear()
                categoryTitles.clear()

                categoryList.addAll(categories)
                categoryTitles.addAll(categories.map { it.title })

                val adapter = ArrayAdapter(this@EditItemActivity, R.layout.simple_spinner_item, categoryTitles)
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                binding.categorySpinner.adapter = adapter
            }
        }
    }


}