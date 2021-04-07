package com.example.nasamarsrover.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nasamarsrover.R
import com.example.nasamarsrover.databinding.FragmentDatePickerDialogBinding
import com.example.nasamarsrover.view.fragment.base.BaseBindingDialogFragment
import com.example.nasamarsrover.viewmodel.DatePickerViewModel
import com.example.nasamarsrover.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_date_picker_dialog.*
import java.util.*

class DatePickerDialogFragment: BaseBindingDialogFragment<FragmentDatePickerDialogBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val datePickerViewModel: DatePickerViewModel by viewModels()
    private val args: DatePickerDialogFragmentArgs by navArgs()

    override fun applyBinding(binding: FragmentDatePickerDialogBinding) {
        binding.dialogModel = args.dialogModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_date_picker_dialog

    override fun initView(view: View, savedInstanceState: Bundle?) {
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)

        val today = Calendar.getInstance()
        initDatePicker(today)

        cancelButton.setOnClickListener {
            dismiss()
        }

        applyButton.setOnClickListener {
            homeViewModel.earthDateLiveData.value = datePickerViewModel.formatDate(datePicker.dayOfMonth, datePicker.month, datePicker.year)
            dismiss()
        }
    }

    private fun initDatePicker(calendar: Calendar){
        datePicker.init(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, _, _, _ -> }
    }
}