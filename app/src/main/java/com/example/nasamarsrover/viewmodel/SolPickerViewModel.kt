package com.example.nasamarsrover.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SolPickerViewModel: ViewModel() {

    val solLiveData = MutableLiveData<String>()
    val isApplyBtnEnabled = MediatorLiveData<Boolean>().apply {
        addSource(solLiveData){
            value = !it.isNullOrBlank()
        }
    }
}