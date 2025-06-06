package com.example.autoschool11.ui.screens.road_signs_recognition

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.autoschool11.R
import com.example.autoschool11.core.domain.models.RoadSignModel
import com.example.autoschool11.core.utils.observe
import com.example.autoschool11.databinding.FragmentRoadSignsRecognitionBinding
import com.example.autoschool11.databinding.RoadSignRecognitionResultDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignRecognitionFragment : Fragment() {

    private var _binding: FragmentRoadSignsRecognitionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignRecognitionViewModel by viewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoadSignsRecognitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeGalleryLauncher()
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        binding.pickButton.setOnClickListener {
            openGallery()
        }

        binding.recognizeButton.setOnClickListener {
            viewModel.onRecognizeButtonClicked(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.recognizedSign.observe(viewLifecycleOwner) { signModel ->
            signModel?.let {
                showSignInfoDialog(it)
            }
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun initializeGalleryLauncher() {
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.onImageSelected(uri)
                binding.pickedSignImage.setImageURI(uri)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Изображение не выбрано", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showSignInfoDialog(sign: RoadSignModel) {
        val dialogBinding = RoadSignRecognitionResultDialogBinding.inflate(layoutInflater)

        sign.imageResId?.let { resId ->
            dialogBinding.imageViewSign.setImageResource(resId)
        }

        dialogBinding.textViewName.text = sign.name
        dialogBinding.textViewDescription.text = sign.description

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
