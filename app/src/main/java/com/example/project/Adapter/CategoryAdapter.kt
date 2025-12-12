package com.example.project.Adapter

import com.example.project.R

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project.activity.ItemsListActivity
import com.example.project.data.CategoryModel
import com.example.project.databinding.ViewholderCategoryBinding


class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.Viewholder {
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
                val intent= Intent(context, ItemsListActivity::class.java).apply{
                    putExtra("id" , item.id.toString())
                    putExtra("title" , item.title)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                context.startActivity(intent)
            }, 500)
        }

        if (selectedPosition == position) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.full_corner_bg)
            holder.binding.titleCat.setTextColor(
                ContextCompat.getColor(context, R.color.white)
            )
        } else {
            holder.binding.titleCat.setBackgroundResource(R.drawable.white_bg)
            holder.binding.titleCat.setTextColor(
                ContextCompat.getColor(context, R.color.darkBrown)
            )
        }
    }


    override fun getItemCount(): Int = items.size

}