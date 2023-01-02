package com.example.images.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.images.adapter.ImageGridAdpter
import com.example.images.databinding.FragmentImageListBinding
import com.example.images.viewmodel.ImageViewModel


class ImageListFragment : Fragment() {

    private val viewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageList.adapter = ImageGridAdpter()

        return binding.root
    }
}