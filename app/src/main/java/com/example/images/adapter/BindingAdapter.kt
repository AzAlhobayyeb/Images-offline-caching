package com.example.images.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.images.R
import com.example.images.network.ImagesData
import com.example.images.viewmodel.ImageApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data:List<ImagesData>?){
    val adapter = recyclerView.adapter as ImageGridAdpter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUrl = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUrl){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_baseline_broken_image_24)
        }
    }
}
@BindingAdapter("ImageApiStatus")
fun bindStatus(statusImageView: ImageView, status: ImageApiStatus){
    when(status){
        ImageApiStatus.LOADING ->{
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ImageApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ImageApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
