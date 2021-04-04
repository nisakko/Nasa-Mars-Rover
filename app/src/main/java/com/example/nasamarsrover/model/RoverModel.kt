package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName

data class RoverModel(@SerializedName(value = "id")
                      val id: Int,
                      @SerializedName(value = "name")
                      val name: String,
                      @SerializedName(value = "landing_date")
                      val landingDate: String,
                      @SerializedName(value = "launch_date")
                      val launchDate: String,
                      @SerializedName(value = "status")
                      val status: String,
                      @SerializedName(value = "max_sol")
                      val maxSol: Int,
                      @SerializedName(value = "max_date")
                      val maxDate: String,
                      @SerializedName(value = "total_photos")
                      val totalPhotos: Int,
                      @SerializedName(value = "cameras")
                      val cameras: List<CameraModel>)
