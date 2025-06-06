package com.example.autoschool11.ui.screens.road_signs_recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.autoschool11.R
import com.example.autoschool11.core.domain.models.RoadSignModel
import com.example.autoschool11.databinding.RoadSignRecognitionResultDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignInfoBottomSheet(private val sign: RoadSignModel) : BottomSheetDialogFragment() {

    private var _binding: RoadSignRecognitionResultDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RoadSignRecognitionResultDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign.imageResId?.let { resId ->
            binding.imageViewSign.setImageResource(resId)
        }

        binding.textViewName.text = sign.name
        binding.textViewDescription.text = sign.description

        binding.buttonOk.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SignInfoBottomSheet"

        fun newInstance(sign: RoadSignModel): SignInfoBottomSheet {
            return SignInfoBottomSheet(sign)
        }
    }
}
