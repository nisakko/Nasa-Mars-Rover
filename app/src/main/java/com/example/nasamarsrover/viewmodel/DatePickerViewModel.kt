package com.example.nasamarsrover.viewmodel

import androidx.lifecycle.ViewModel

class DatePickerViewModel: ViewModel() {

    fun formatDate(dayOfMonth: Int, month: Int, year:Int): String = "${year}-${month+1}-${dayOfMonth}"
}