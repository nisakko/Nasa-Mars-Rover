package com.example.nasamarsrover.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasamarsrover.R
import com.example.nasamarsrover.adapter.FilterAdapter
import com.example.nasamarsrover.model.CameraModel
import com.example.nasamarsrover.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog.*

class BottomSheetFilterDialog : BottomSheetDialogFragment(){

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val args: BottomSheetFilterDialogArgs by navArgs()
    private val filterAdapter = FilterAdapter { camera -> onItemClick(camera) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
    }

    private fun setupRV(){
        bottomSheetRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = filterAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        filterAdapter.submitList(args.filterList.toList())
    }

    private fun onItemClick(camera: CameraModel){
        homeViewModel.cameraLiveData.value = camera.name
        dismiss()
    }
}