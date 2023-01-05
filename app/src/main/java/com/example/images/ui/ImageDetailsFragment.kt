package com.example.images.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import com.example.images.R
import com.example.images.databinding.FragmentImageDetailsBinding
import com.example.images.viewmodel.ImageViewModel

class ImageDetailsFragment : Fragment() {

    private val viewModel: ImageViewModel by activityViewModels()
        val SEARCH_PREFIX = "https://www.google.com/search?q="

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // Inflate the layout for this fragment
        Log.v("ta22","inside on create view details,viewModel.imagePicked.value=${viewModel.imagePicked}")

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.context_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("ta22","onViewCreated details,viewModel.imagePicked.value=${viewModel.imagePicked.value}")
        var searchButton: ImageButton =view.findViewById(R.id.ndbutton)
        searchButton.setOnClickListener{
            var queryUri: Uri = Uri.parse("${SEARCH_PREFIX}")
            var intent = Intent(Intent.ACTION_VIEW, queryUri)
            context?.startActivity(intent)
            Log.v("ta22","search Buttonclicked,viewModel.imagePicked.value=${viewModel.imagePicked.value}")
        }
    }
}