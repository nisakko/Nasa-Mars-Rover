package com.example.nasamarsrover.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.nasamarsrover.R
import com.example.nasamarsrover.databinding.FragmentRoverPhotoDialogBinding
import com.example.nasamarsrover.view.fragment.base.BaseBindingDialogFragment
import kotlinx.android.synthetic.main.fragment_rover_photo_dialog.*

class RoverPhotoDetailsDialogFragment: BaseBindingDialogFragment<FragmentRoverPhotoDialogBinding>() {

    private val args: RoverPhotoDetailsDialogFragmentArgs by navArgs()

    override fun getLayoutId() = R.layout.fragment_rover_photo_dialog

    override fun applyBinding(binding: FragmentRoverPhotoDialogBinding) {
        binding.marsRoverPhoto = args.roverPhoto
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        closeButton.setOnClickListener {
            dismiss()
        }
    }
}