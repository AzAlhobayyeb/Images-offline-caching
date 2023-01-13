package com.example.images.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.images.domain.ImagesModels


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<ImagesModels>?) {
    val adapter = recyclerView.adapter as ImageGridAdpter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String){
    Glide.with(imageView.context).load(url).into(imageView)
}
