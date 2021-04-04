package com.example.nasamarsrover.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.nasamarsrover.API_PAGE_SIZE
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelper
import com.example.nasamarsrover.data.datasource.PostDataSource
import com.example.nasamarsrover.data.enums.RoverType
import com.example.nasamarsrover.data.local.MarsRoverPreferenceRepository
import com.example.nasamarsrover.model.CameraModel
import com.example.nasamarsrover.model.RoverModel
import com.example.nasamarsrover.model.RoverModelWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiHelper: NasaMarsRoverApiHelper,
    private val marsRoverPreferenceRepository: MarsRoverPreferenceRepository
) : ViewModel() {

    val cameraLiveData = MutableLiveData<String>()
    val currentRover = MutableLiveData<String>(RoverType.Curiosity.roverName)
    val cameraFilterList = MediatorLiveData<List<CameraModel>>().apply {
        addSource(currentRover){ rover ->
            when(rover){
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
        }){ required ->
            if(required){
                Log.d("roverRemoteRequired","curiosity yes")
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Curiosity.roverName).saveRoverModelToPreference()
                }
            } else{
                Log.d("roverRemoteRequired","curiosity no")
                postValue(getRoverInfoFromLocalRepository(RoverType.Curiosity.roverName))
            }
        }
    }

    val opportunityInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Opportunity.roverName)) {
            it == null
        }){ required ->
            if(required){
                Log.d("roverRemoteRequired","opportunity yes")
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Opportunity.roverName).saveRoverModelToPreference()
                }
            } else{
                Log.d("roverRemoteRequired","opportunity no")
                postValue(getRoverInfoFromLocalRepository(RoverType.Opportunity.roverName))
            }
        }
    }

    val spiritInfo = MediatorLiveData<RoverModel>().apply {
        addSource(Transformations.map(marsRoverPreferenceRepository.getRoverInfoLiveData(RoverType.Spirit.roverName)) {
            it == null
        }){ required ->
            if(required){
                Log.d("roverRemoteRequired","spirit yes")
                viewModelScope.launch {
                    getRoverInfoFromRemote(RoverType.Spirit.roverName).saveRoverModelToPreference()
                }
            } else{
                Log.d("roverRemoteRequired","spirit no")
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

    private suspend fun getRoverInfoFromRemote(roverName: String): Response<RoverModelWrapper> =
        apiHelper.getRoverInfo(roverName)

    val listData =
        Pager(PagingConfig(pageSize = API_PAGE_SIZE)) {
            PostDataSource(apiHelper, currentRover.value!!,1000, cameraLiveData.value)

        }.flow.cachedIn(viewModelScope)
}