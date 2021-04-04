package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName

data class MarsRoverResponse(@SerializedName(value = "photos")
                             val photos: List<MarsRoverPhoto>)
