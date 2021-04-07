package com.example.nasamarsrover.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.nasamarsrover.R
import com.example.nasamarsrover.databinding.FragmentInformationDialogBinding
import com.example.nasamarsrover.view.fragment.base.BaseBindingDialogFragment
import com.example.nasamarsrover.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_information_dialog.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class InformationDialogFragment : BaseBindingDialogFragment<FragmentInformationDialogBinding>() {

    private val args: InformationDialogFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun applyBinding(binding: FragmentInformationDialogBinding) {
        binding.dialogModel = args.dialogModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_information_dialog

    override fun initView(view: View, savedInstanceState: Bundle?) {

        cancelButton.setOnClickListener {
            dismiss()
        }

        dialogRetryButton.setOnClickListener {
            lifecycleScope.launch {
                retryProgressBar.isVisible = true
                val deferredResult = async {
                    homeViewModel.currentRover.value?.let { roverName ->
                        homeViewModel.getRoverInfoFromRemote(roverName)
                    } ?: false
                }
                val isSuccess = deferredResult.await()
                retryProgressBar.isVisible = false
                if (isSuccess) dismiss() else Toast.makeText(
                    requireContext(),
                    getString(R.string.get_rover_info_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
