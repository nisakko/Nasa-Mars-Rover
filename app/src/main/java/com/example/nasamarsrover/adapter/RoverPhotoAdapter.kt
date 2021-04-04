package com.example.nasamarsrover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasamarsrover.R
import com.example.nasamarsrover.model.MarsRoverPhoto
import kotlinx.android.synthetic.main.rover_photo_item.view.*

class RoverPhotoAdapter : PagingDataAdapter<MarsRoverPhoto, RoverPhotoAdapter.ViewHolder>(
    RoverPhotoDifferentiator
) {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        fun bind(item: MarsRoverPhoto?){
            item?.let { photo ->
                view.roverCamera.text = photo.camera.name
                Glide.with(view).load(photo.imgSrc).into(view.roverPhoto)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.rover_photo_item, parent, false)
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