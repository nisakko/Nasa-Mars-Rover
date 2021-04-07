package com.example.nasamarsrover.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterCameraModel (val isSelected: Boolean = false,
                              val cameraModel: CameraModel): Parcelable