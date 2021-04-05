package com.example.nasamarsrover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamarsrover.databinding.RoverPhotoItemBinding
import com.example.nasamarsrover.model.MarsRoverPhoto

class RoverPhotoAdapter(private val onClick: (MarsRoverPhoto) -> (Unit)) : PagingDataAdapter<MarsRoverPhoto, RoverPhotoAdapter.ViewHolder>
    (RoverPhotoDifferentiator) {

    class ViewHolder(private val binding: RoverPhotoItemBinding, val onClick: (MarsRoverPhoto) -> (Unit)) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MarsRoverPhoto?){
            item?.let { photo ->
                binding.marsRoverPhoto = photo
                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RoverPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick
        )
    }

    object RoverPhotoDifferentiator : DiffUtil.ItemCallback<MarsRoverPhoto>() {

        override fun areItemsTheSame(oldItem: MarsRoverPhoto, newItem: MarsRoverPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsRoverPhoto, newItem: MarsRoverPhoto): Boolean {
            return oldItem == newItem
        }
    }
}