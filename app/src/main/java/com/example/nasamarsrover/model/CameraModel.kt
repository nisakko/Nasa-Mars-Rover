package com.example.nasamarsrover.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CameraModel(@SerializedName(value = "name")
                       val name: String,
                       @SerializedName(value = "full_name")
                       val fullName: String) : Parcelable
