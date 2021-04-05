package com.example.nasamarsrover.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rover(@SerializedName(value = "id")
                 val id: Int,
                 @SerializedName(value = "name")
                 val name: String,
                 @SerializedName(value = "landing_date")
                 val landingDate: String,
                 @SerializedName(value = "launch_date")
                 val launchDate: String,
                 @SerializedName(value = "status")
                 val status: String): Parcelable
