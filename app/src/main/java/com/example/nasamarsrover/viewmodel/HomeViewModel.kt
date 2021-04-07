package com.example.nasamarsrover.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.nasamarsrover.API_PAGE_SIZE
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelper
import com.example.nasamarsrover.data.datasource.PagingDataSource
import com.example.nasamarsrover.data.enums.RoverType
import com.example.nasamarsrover.data.local.MarsRoverPreferenceRepository
import com.example.nasamarsrover.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiHelper: NasaMarsRoverApiHelper,
    private val marsRoverPreferenceRepository: MarsRoverPreferenceRepository
) : ViewModel() {

    val currentCameraFilterLiveData = MutableLiveData<CameraModel>()
    val currentRover = MutableLiveData(RoverType.Curiosity.roverName)
    val solLiveData = MutableLiveData(DEFAULT_SOL)
    val earthDateLiveData = MutableLiveData<String>(null)

    val cameraList = MediatorLiveData<List<CameraModel>>().apply {
        addSource(currentRover) { rover ->
            when (rover) {
                RoverType.Spirit.roverName -> {
                    spiritInfo.value?.let {
                        value = it.cameras
                    }
                }
                RoverType.Opportunity.roverName -> {
                    opportunityInfo.value?.let {
                        value = it.cameras
                    }
                }
                else -> {
                    curiosityInfo.value?.let {
                        value = it.cameras
                    }
                }
            }
        }
    }

    val curiosityInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Curiosity.roverName)) {
            it == null
        }) { remoteRequired ->
            if (remoteRequired) {
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Curiosity.roverName)
                }
            } else {
                postValue(getRoverInfoFromLocalRepository(RoverType.Curiosity.roverName))
            }
        }
    }

    val opportunityInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Opportunity.roverName)) {
            it == null
        }) { remoteRequired ->
            if (remoteRequired) {
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Opportunity.roverName)
                }
            } else {
                postValue(getRoverInfoFromLocalRepository(RoverType.Opportunity.roverName))
            }
        }
    }

    val spiritInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Spirit.roverName)) {
            it == null
        }) { remoteRequired ->
            if (remoteRequired) {
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Spirit.roverName)
                }
            } else {
                postValue(getRoverInfoFromLocalRepository(RoverType.Spirit.roverName))
            }
        }
    }

    private fun getRoverInfoFromLocalRepository(roverName: String): RoverModel? =
        marsRoverPreferenceRepository.getRoverInfo(roverName)

    private fun Response<RoverModelWrapper>.saveRoverModelToPreference() {
        if (this.isSuccessful) {
            val roverModel = this.body()?.rover
            roverModel?.let {
                marsRoverPreferenceRepository.saveRoverInfo(it)
            }
        }
    }

    suspend fun getRoverInfoFromRemote(roverName: String): Boolean {
        return try {
            apiHelper.getRoverInfo(roverName).saveRoverModelToPreference()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getPhotosFromRemote(roverName: String): Flow<PagingData<MarsRoverPhoto>> {

        return Pager(PagingConfig(pageSize = API_PAGE_SIZE)) {
            PagingDataSource(
                apiHelper,
                roverName,
                solLiveData.value,
                earthDateLiveData.value,
                currentCameraFilterLiveData.value
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getCameraFilterList(cameras: List<CameraModel>): MutableList<FilterCameraModel>{
        val filterCameraModels = mutableListOf<FilterCameraModel>()
        cameras.map { camera ->
            filterCameraModels.add(
                FilterCameraModel(
                    isSelected = camera == currentCameraFilterLiveData.value,
                    cameraModel = camera
                )
            )
        }
        return filterCameraModels
    }

    companion object {
        const val DEFAULT_SOL = 1000
    }
}