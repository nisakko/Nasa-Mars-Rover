package com.example.nasamarsrover.data.local

import androidx.lifecycle.LiveData
import com.example.nasamarsrover.model.RoverModel

interface MarsRoverPreferenceRepository {

    fun saveRoverInfo(roverModel: RoverModel): Boolean
    fun getRoverInfo(roverName: String): RoverModel?
    fun getRoverInfoLiveData(roverName: String): LiveData<RoverModel?>
    fun deleteRoverInfo(roverName: String): Boolean
}