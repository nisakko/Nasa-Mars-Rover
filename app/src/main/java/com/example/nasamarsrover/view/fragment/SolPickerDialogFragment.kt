package com.example.nasamarsrover.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nasamarsrover.R
import com.example.nasamarsrover.databinding.FragmentSolPickerDialogBinding
import com.example.nasamarsrover.view.fragment.base.BaseBindingDialogFragment
import com.example.nasamarsrover.viewmodel.HomeViewModel
import com.example.nasamarsrover.viewmodel.SolPickerViewModel
import kotlinx.android.synthetic.main.fragment_sol_picker_dialog.*

class SolPickerDialogFragment : BaseBindingDialogFragment<FragmentSolPickerDialogBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val solPickerViewModel: SolPickerViewModel by viewModels()
    private val args: SolPickerDialogFragmentArgs by navArgs()

    override fun applyBinding(binding: FragmentSolPickerDialogBinding) {
        binding.viewModel = solPickerViewModel
        binding.dialogModel = args.dialogModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_sol_picker_dialog

    override fun initView(view: View, savedInstanceState: Bundle?) {
        cancelButton.setOnClickListener {
            dismiss()
        }

        solTextInputLayout?.editText?.doOnTextChanged { text, _, _, _ ->
            solPickerViewModel.solLiveData.value = text?.trim()?.toString() ?: ""
        }

        applyButton.setOnClickListener {
            solPickerViewModel.solLiveData.observe(viewLifecycleOwner, {
                homeViewModel.solLiveData.value = it.toInt()
                dismiss()
            })
        }
    }
}
