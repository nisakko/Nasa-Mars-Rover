package com.example.nasamarsrover.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.tryToNavigate(directions: NavDirections) {
    try {
        findNavController().navigate(directions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun Fragment.tryToNavigate(actionID: Int) {
    try {
        findNavController().navigate(actionID)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}