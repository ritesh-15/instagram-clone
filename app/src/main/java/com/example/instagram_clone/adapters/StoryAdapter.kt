package com.example.instagram_clone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.databinding.ItemHomeStoryBinding
import com.example.instagram_clone.models.Post

class StoryAdapter(
    private val context: Context,
    private val list:ArrayList<Post>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemHomeStoryBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), mListener!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            // TODO
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    class MyViewHolder(val binding: ItemHomeStoryBinding, listener: OnClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                listener.onClick(adapterPosition)
            }

        }

    }
}

