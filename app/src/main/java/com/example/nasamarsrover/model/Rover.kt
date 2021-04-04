package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Rover(@SerializedName(value = "id")
                 val id: Int,
                 @SerializedName(value = "name")
                 val name: String,
                 @SerializedName(value = "landing_date")
                 val landingDate: String,
                 @SerializedName(value = "launch_date")
                 val launchDate: String,
                 @SerializedName(value = "status")
                 @field:Json(name = "status")
                 val status: String)
