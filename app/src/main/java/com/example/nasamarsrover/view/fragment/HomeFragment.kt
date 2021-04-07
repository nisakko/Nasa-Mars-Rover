package com.example.nasamarsrover.view.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasamarsrover.R
import com.example.nasamarsrover.adapter.FooterLoadStateAdapter
import com.example.nasamarsrover.adapter.RoverPhotoAdapter
import com.example.nasamarsrover.data.enums.RoverType
import com.example.nasamarsrover.model.DialogModel
import com.example.nasamarsrover.model.MarsRoverPhoto
import com.example.nasamarsrover.utils.tryToNavigate
import com.example.nasamarsrover.view.fragment.base.BaseFragment
import com.example.nasamarsrover.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val roverPhotoAdapter = RoverPhotoAdapter { marsRoverPhoto -> onItemClick(marsRoverPhoto)}

    @Inject
    lateinit var handler: CoroutineExceptionHandler

    override fun getRootLayoutId() = R.layout.fragment_home

    override fun initView(rootView: View) {
        setupList()
        initObservers()
        initClickListeners()
    }

    private fun initClickListeners(){

        toolBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.filterMenuItem -> {
                    homeViewModel.cameraFilterList.value?.let { cameraList ->
                        val navDirections = HomeFragmentDirections.actionHomeFragmentToBottomSheetFilterDialog(cameraList.toTypedArray())
                        tryToNavigate(navDirections)
                    } ?: kotlin.run {
                        val navDirections = HomeFragmentDirections.actionHomeFragmentToInformationDialogFragment(
                            DialogModel(R.string.rover_info_dialog_failed_title,
                                R.string.rover_info_dialog_failed_message,R.string.retry, R.string.cancel)
                        )
                        tryToNavigate(navDirections)
                    }
                    true
                }
                R.id.refreshMenuItem -> {
                    roverPhotoAdapter.refresh()
                    true
                }
                R.id.solMenuItem -> {
                    val navDirections = HomeFragmentDirections.actionHomeFragmentToSolPickerDialogFragment(
                        DialogModel(title = R.string.sol_description, btnActiveText = R.string.apply, btnPassiveText = R.string.cancel))
                    tryToNavigate(navDirections)
                    true
                }
                R.id.earthDateMenuItem -> {
                    val navDirections = HomeFragmentDirections.actionHomeFragmentToDatePickerDialogFragment(
                        DialogModel(title = R.string.sol_description, btnActiveText = R.string.apply, btnPassiveText = R.string.cancel))
                    tryToNavigate(navDirections)
                    true
                }
                else -> {
                    false
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                homeViewModel.currentRover.value = tab?.text.toString()
                homeViewModel.cameraLiveData.value = null
                roverPhotoAdapter.refresh()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupList() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = roverPhotoAdapter.withLoadStateFooter(
                footer = FooterLoadStateAdapter { roverPhotoAdapter.retry() }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            roverPhotoAdapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh
                progressBar.isVisible = refreshState is LoadState.Loading
                if(refreshState is LoadState.NotLoading && roverPhotoAdapter.itemCount == 0){
                    Toast.makeText(requireContext(), getString(R.string.no_results), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObservers() {

        homeViewModel.earthDateLiveData.observe(viewLifecycleOwner, {
            if(it != null){
                homeViewModel.solLiveData.value = null
                roverPhotoAdapter.refresh()
            }
        })

        homeViewModel.solLiveData.observe(viewLifecycleOwner, {
            if(it != null){
                homeViewModel.earthDateLiveData.value = null
                roverPhotoAdapter.refresh()
            }
        })

        homeViewModel.cameraLiveData.observe(viewLifecycleOwner, {
            roverPhotoAdapter.refresh()
        })

        homeViewModel.solLiveData.observe(viewLifecycleOwner, {
            roverPhotoAdapter.refresh()
        })

        homeViewModel.currentRover.observe(viewLifecycleOwner, { roverName ->
            viewLifecycleOwner.lifecycleScope.launch(handler) {
                homeViewModel.getPhotosFromRemote(roverName).collectLatest { pagedData ->
                    roverPhotoAdapter.submitData(pagedData)
                }
            }
        })

        homeViewModel.curiosityInfo.observe(viewLifecycleOwner, {
            if (tabLayout.selectedTabPosition == 0)
                homeViewModel.currentRover.value = RoverType.Curiosity.roverName
        })

        homeViewModel.cameraFilterList.observe(viewLifecycleOwner, {
            Log.d("cameraListTag", it.toString())
        })

        homeViewModel.spiritInfo.observe(viewLifecycleOwner, {
            if (tabLayout.selectedTabPosition == 1)
                homeViewModel.currentRover.value = RoverType.Spirit.roverName
        })
        homeViewModel.opportunityInfo.observe(viewLifecycleOwner, {
            if (tabLayout.selectedTabPosition == 2)
                homeViewModel.currentRover.value = RoverType.Opportunity.roverName
        })
    }

    private fun onItemClick(marsRoverPhoto: MarsRoverPhoto){
        val navDirections = HomeFragmentDirections.actionHomeFragmentToRoverPhotoDetailsDialogFragment(marsRoverPhoto)
        tryToNavigate(navDirections)
    }
}