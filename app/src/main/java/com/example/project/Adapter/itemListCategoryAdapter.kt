package com.example.project.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.activity.DetailsActivity

import com.example.project.data.ItemModel
import com.example.project.databinding.ViewholderItemsBinding


class itemListCategoryAdapter(val items: MutableList<ItemModel>) :
    RecyclerView.Adapter<itemListCategoryAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var context: Context
//    private var selectedPosition = -1
//    private var lastSelectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): itemListCategoryAdapter.Viewholder {
        context=parent.context
        val binding = ViewholderItemsBinding.inflate(LayoutInflater.from(context) , parent , false)
        return Viewholder(binding)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val item = items[position]

        holder.binding.textView6.text = item.title
        holder.binding.textView7.text = "$" + item.price.toString()
        holder.binding.textView8.text = item.extra
        Glide.with(context).load(item.picUrl[0]).into(holder.binding.imageView5)

        holder.itemView.setOnClickListener {
            val intent = Intent(context , DetailsActivity::class.java)
            intent.putExtra("object" , item)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = items.size

}