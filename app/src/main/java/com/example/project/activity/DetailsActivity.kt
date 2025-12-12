package com.example.project.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project.data.ItemModel
import com.example.project.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private lateinit var  item: ItemModel

    var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle()

        binding.back2.setOnClickListener { finish() }

        binding.plus.setOnClickListener {
            quantity++
            binding.num.text = quantity.toString()
            binding.price1.text = "$" + (item.price * quantity)
        }
        binding.minus.setOnClickListener {
            if (quantity > 1) {     // prevent going below 1
                quantity--
                binding.num.text = quantity.toString()
                binding.price1.text = "$" + (item.price * quantity)
            }
        }
    }

    private fun bundle() {
        binding.apply{
            item = intent.getSerializableExtra("object") as ItemModel
            Glide.with(this@DetailsActivity).load(item.picUrl[0]).into(binding.imageView10)
            title2.text = item.title
            Description1.text = item.description
            rating.text = item.rating.toString()
            price1.text = "$" + item.price
            num.text = quantity.toString()
        }

    }
}