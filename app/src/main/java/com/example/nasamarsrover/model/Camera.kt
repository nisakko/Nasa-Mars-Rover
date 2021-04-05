package com.example.nasamarsrover.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Camera(@SerializedName(value = "id")
                  val id: Int,
                  @SerializedName(value = "name")
                  var name: String,
                  @SerializedName(value = "rover_id")
                  val roverId: Int,
                  @SerializedName(value = "full_name")
                  val fullName: String) : Parcelable
