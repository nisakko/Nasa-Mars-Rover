package com.example.nasamarsrover.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nasamarsrover.R
import com.example.nasamarsrover.application.GlideApp
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
            GlideApp.with(view.context).load(imgSrc).placeholder(R.drawable.ic_wrapping_loading_placeholder).
            error(R.drawable.ic_wrapping_error_placeholder).
            dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
        }
    }
}



