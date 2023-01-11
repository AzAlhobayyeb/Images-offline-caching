package com.example.images.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import com.example.images.R
import com.example.images.databinding.FragmentImageDetailsBinding
import java.io.File
import java.io.FileOutputStream

class ImageDetailsFragment : Fragment() {

  //  private val viewModel: ImageViewModel by activityViewModels()
    val SEARCH_PREFIX = "https://unsplash.com/@"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
     //   binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.context_menu, menu)
        val optionMenu = menu.findItem(R.id.option_menu)
        setIcon(optionMenu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.visit_author_page -> visitAuthor()
            R.id.save_image -> saveImage()
            R.id.share_image -> shareImage()
        }
            return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
    }

    private fun visitAuthor(){
     //   var authorUn = viewModel.imagePicked.value?.author
     //   if (authorUn != null) {
       //     authorUn = authorUn.replace("\\s".toRegex(), "") }
       // val queryUri: Uri = Uri.parse("${SEARCH_PREFIX}${authorUn}")
       // val intent = Intent(Intent.ACTION_VIEW, queryUri)
        //context?.startActivity(intent)
    }

   private fun saveImage(){
    }

    private fun shareImage(){
    }
}