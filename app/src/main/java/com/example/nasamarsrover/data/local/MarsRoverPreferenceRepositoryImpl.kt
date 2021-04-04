package com.example.nasamarsrover.data.local

import androidx.lifecycle.LiveData
import com.example.nasamarsrover.model.RoverModel
import java.util.*
import javax.inject.Inject

class MarsRoverPreferenceRepositoryImpl @Inject constructor(private val preferenceRepository: PreferenceRepository):
    MarsRoverPreferenceRepository {

    override fun saveRoverInfo(roverModel: RoverModel): Boolean {
        return preferenceRepository.saveObject(roverModel.name.toLowerCase(Locale.ENGLISH),roverModel)
    }

    override fun getRoverInfo(roverName: String): RoverModel? {
        return preferenceRepository.readObjectNow(roverName)
    }

    override fun getRoverInfoLiveData(roverName: String): LiveData<RoverModel?> {
        return preferenceRepository.readObject(roverName)
    }

    override fun deleteRoverInfo(roverName: String): Boolean {
        return preferenceRepository.remove(roverName)
    }
}