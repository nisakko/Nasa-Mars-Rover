package com.example.nasamarsrover.model

import com.google.gson.annotations.SerializedName

data class RoverModelWrapper(@SerializedName(value = "rover")
                             val rover: RoverModel)
