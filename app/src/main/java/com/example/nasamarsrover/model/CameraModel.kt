package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName

data class CameraModel(@SerializedName(value = "name")
                       val name: String,
                       @SerializedName(value = "full_name")
                       val fullName: String)
