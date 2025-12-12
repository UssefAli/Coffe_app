package com.example.project.Adapter

import com.example.project.R

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project.data.CategoryModel
import com.example.project.databinding.ViewholderCategoryBinding


class CategoryEditAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryEditAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryEditAdapter.Viewholder {
        context=parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context) , parent , false)
        return Viewholder(binding)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val item = items[position]

        holder.binding.titleCat.text = item.title

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position

            if (lastSelectedPosition != -1)
                notifyItemChanged(lastSelectedPosition)

            notifyItemChanged(selectedPosition)

            Handler(Looper.getMainLooper()).postDelayed({

            }, 500)
        }

        if (selectedPosition == position) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.normal_brown_stroke)
            holder.binding.titleCat.setTextColor(
                ContextCompat.getColor(context, R.color.darkBrown)
            )
        } else {
            holder.binding.titleCat.setBackgroundResource(R.color.lightCream)
            holder.binding.titleCat.setTextColor(
                ContextCompat.getColor(context, R.color.darkBrown)
            )
        }
    }


    override fun getItemCount(): Int = items.size
    fun getSelectedCategory(): CategoryModel? {
        return if (selectedPosition != -1) items[selectedPosition] else null
    }
}