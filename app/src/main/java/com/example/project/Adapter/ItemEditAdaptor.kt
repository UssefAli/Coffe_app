package com.example.project.Adapter

import com.example.project.R

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.data.ItemModel
import com.example.project.databinding.ViewholderItemsBinding


class ItemEditAdaptor(val items: MutableList<ItemModel>) :
    RecyclerView.Adapter<ItemEditAdaptor.Viewholder>() {

    class Viewholder(val binding: ViewholderItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemEditAdaptor.Viewholder {
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
            holder.binding.view.setBackgroundResource(R.drawable.brown_stroke_white_bg)

        } else {
            holder.binding.view.setBackgroundResource(R.drawable.white_bg)
        }
    }


    override fun getItemCount(): Int = items.size
    fun getSelectedItem(): ItemModel? {
        return if (selectedPosition != -1) items[selectedPosition] else null
    }
}