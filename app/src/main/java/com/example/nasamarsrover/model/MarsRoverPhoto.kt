package com.example.nasamarsrover.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsRoverPhoto(@SerializedName(value = "id")
                             val id: Int,
                          @SerializedName(value = "sol")
                             val sol: Int,
                          @SerializedName(value = "camera")
                             var camera: Camera,
                          @SerializedName(value = "img_src")
                             val imgSrc: String,
                          @SerializedName(value = "earth_date")
                             val earthDate: String,
                          @SerializedName(value = "rover")
                             val rover: Rover
): Parcelable

{
    companion object {
        @JvmStatic
        @BindingAdapter("roverImage")
        fun loadImage(view: ImageView, imgSrc: String) {
            Glide.with(view.context).load(imgSrc).into(view)
        }
    }
}



