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
import com.example.nasamarsrover.adapter.RoverPhotoAdapter
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
    lateinit var roverPhotoAdapter: RoverPhotoAdapter
    @Inject
    lateinit var handler: CoroutineExceptionHandler

    override fun getRootLayoutId() = R.layout.fragment_home

    override fun initView(rootView: View) {
        Toast.makeText(requireContext(), "App has been started", Toast.LENGTH_LONG).show()
        setupList()
        initObservers()

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                homeViewModel.currentRover.value = tab?.text.toString()
                roverPhotoAdapter.refresh()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        fhazCamBtn.setOnClickListener {
            homeViewModel.cameraLiveData.value = "FHAZ"
            roverPhotoAdapter.refresh()
        }

        navCamBtn.setOnClickListener {
            homeViewModel.cameraLiveData.value = "NAVCAM"
            roverPhotoAdapter.refresh()
        }
    }

    private fun setupList() {
        recyclerView.apply {
            roverPhotoAdapter = RoverPhotoAdapter()
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = roverPhotoAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            roverPhotoAdapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun initObservers() {

        homeViewModel.cameraFilterList.observe(viewLifecycleOwner, {
            Log.d("cameraListTag",it.toString())
        })

        homeViewModel.curiosityInfo.observe(viewLifecycleOwner, {
            Log.d("roverTag","::Curiosity $it")
        })
        homeViewModel.spiritInfo.observe(viewLifecycleOwner, {
            Log.d("roverTag","::Spirit $it")
        })
        homeViewModel.opportunityInfo.observe(viewLifecycleOwner, {
            Log.d("roverTag","::Opportunity $it")
        })
        viewLifecycleOwner.lifecycleScope.launch(handler) {

            homeViewModel.listData.collectLatest { pagedData ->
                roverPhotoAdapter.submitData(pagedData)
            }
        }
    }
}