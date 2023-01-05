package com.example.images.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.images.R
import com.example.images.adapter.ImageGridAdpter
import com.example.images.adapter.ImageListener
import com.example.images.adapter.bindRecyclerView
import com.example.images.databinding.FragmentImageListBinding
import com.example.images.viewmodel.ImageViewModel


class ImageListFragment : Fragment() {

    private val viewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageList.adapter = ImageGridAdpter(ImageListener { imageData ->
            viewModel.onImageClicked(imageData)
            findNavController().navigate(R.id.action_imageListFragment_to_imageDetailsFragment)
            })
        return binding.root
    }
}