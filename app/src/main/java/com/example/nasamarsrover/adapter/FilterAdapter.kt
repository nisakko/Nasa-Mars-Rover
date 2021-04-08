package com.example.nasamarsrover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamarsrover.databinding.FilterListItemBinding
import com.example.nasamarsrover.model.FilterCameraModel

class FilterAdapter(private val onClick: (FilterCameraModel) -> Unit) :
    ListAdapter<FilterCameraModel, FilterAdapter.ViewHolder>(FilterItemDiffCallback) {

    class ViewHolder(private val binding: FilterListItemBinding, val onClick: (FilterCameraModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterCameraModel){
            binding.filterCamera = item
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


    object FilterItemDiffCallback : DiffUtil.ItemCallback<FilterCameraModel>() {
        override fun areItemsTheSame(oldItem: FilterCameraModel, newItem: FilterCameraModel): Boolean {
            return oldItem.cameraModel.name == newItem.cameraModel.name
        }

        override fun areContentsTheSame(oldItem: FilterCameraModel, newItem: FilterCameraModel): Boolean {
            return oldItem == newItem
        }
    }
}