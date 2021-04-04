package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Camera(@SerializedName(value = "id")
                  val id: Int,
                  @SerializedName(value = "name")
                  var name: String,
                  @SerializedName(value = "rover_id")
                  val roverId: Int,
                  @SerializedName(value = "full_name")
                  val fullName: String)
