package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName

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
)
