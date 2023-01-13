package com.example.images.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.images.R
import com.example.images.databinding.FragmentImageDetailsBinding
import com.example.images.viewmodel.ImagesViewModel
import java.io.File
import java.io.FileOutputStream

class ImageDetailsFragment : Fragment() {

    private var _binding: FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!
    val SEARCH_PREFIX = "https://unsplash.com/@"

    private lateinit var author:String
    private lateinit var download_url:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            author = it.getString("author").toString()
            download_url = it.getString("download_url").toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = binding.ndimage

        fun bindImage(imageView: ImageView, imgUri: String?){

            imgUri?.let {
                val imageUri = imgUri.toUri().buildUpon().scheme("https").build()
                imageView.load(imageUri)
            }
        }
        bindImage(image,download_url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.context_menu, menu)
        val optionMenu = menu.findItem(R.id.option_menu)
        setIcon(optionMenu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.visit_author_page -> visitAuthor()
            R.id.share_image -> shareImage()
        }
            return super.onOptionsItemSelected(item)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
    }

    private fun visitAuthor(){
        var authorUn = author
        authorUn = authorUn.replace("\\s".toRegex(), "")
        val queryUri: Uri = Uri.parse("${SEARCH_PREFIX}${authorUn}")
        val intent = Intent(Intent.ACTION_VIEW, queryUri)
        context?.startActivity(intent)
    }

    private fun shareImage(){

            val intent = Intent(Intent.ACTION_SEND).apply {

                val queryUrl: Uri = Uri.parse(download_url)
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$queryUrl")
                type = "text/*"
            }
        context?.startActivity(intent)
        }
}