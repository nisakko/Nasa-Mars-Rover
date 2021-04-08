package com.example.nasamarsrover.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.nasamarsrover.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class DialogModel (@param:StringRes @field:StringRes var title: Int = R.string.empty_string,
                   @param:StringRes @field:StringRes var message: Int = R.string.empty_string,
                   @param:StringRes @field:StringRes var btnActiveText: Int = R.string.empty_string,
                   @param:StringRes @field:StringRes var btnPassiveText: Int = R.string.cancel): Parcelable