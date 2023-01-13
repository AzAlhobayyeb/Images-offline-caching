package com.example.images.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.images.R
import com.example.images.adapter.ImageGridAdpter
import com.example.images.databinding.FragmentImageListBinding
import com.example.images.viewmodel.ImagesViewModel

import kotlinx.coroutines.launch


class ImageListFragment : Fragment() {

    private val SEARCH_PREFIX = "https://unsplash.com"


    private val viewModel: ImagesViewModel by lazy{
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this,ImagesViewModel.Factory(activity.application))
            .get(ImagesViewModel::class.java)
    }
    private var _binding:FragmentImageListBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImageListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ImageGridAdpter(viewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        binding.mySwipeRefreshLayout.setOnRefreshListener {

            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.imagesRepository.refreshImages()
                binding.mySwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_menu, menu)
        val unsplashMenu = menu.findItem(R.id.unsplash_menu)
        setIcon(unsplashMenu)
    }

    private fun visitUnslash() {
            val queryUri: Uri = Uri.parse(SEARCH_PREFIX)
            val intent = Intent(Intent.ACTION_VIEW, queryUri)
            context?.startActivity(intent)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.visit_unslash->{ visitUnslash() }
        }
        return super.onOptionsItemSelected(item)
    }

}