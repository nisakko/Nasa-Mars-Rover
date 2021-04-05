package com.example.nasamarsrover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamarsrover.databinding.FilterListItemBinding
import com.example.nasamarsrover.model.CameraModel

class FilterAdapter(private val onClick: (CameraModel) -> Unit) :
    ListAdapter<CameraModel,FilterAdapter.ViewHolder>(FilterItemDiffCallback) {

    class ViewHolder(private val binding: FilterListItemBinding, val onClick: (CameraModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CameraModel){
            binding.camera = item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FilterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }


    object FilterItemDiffCallback : DiffUtil.ItemCallback<CameraModel>() {
        override fun areItemsTheSame(oldItem: CameraModel, newItem: CameraModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CameraModel, newItem: CameraModel): Boolean {
            return oldItem == newItem
        }
    }
}