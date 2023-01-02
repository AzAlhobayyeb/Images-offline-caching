package com.example.images.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.images.databinding.ViewItemBinding
import com.example.images.network.ImagesData



class ImageGridAdpter :ListAdapter<ImagesData,ImageGridAdpter.ImageViewHolder>(DiffCallback){

    class ImageViewHolder(
        private var binding: ViewItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(imagesData: ImagesData){
            binding.image = imagesData
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback:DiffUtil.ItemCallback<ImagesData>(){
        override fun areItemsTheSame(oldItem: ImagesData, newItem: ImagesData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImagesData, newItem: ImagesData): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        view:Int
    ):ImageViewHolder{
        return ImageViewHolder(
            ViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
    override fun onBindViewHolder(holder: ImageViewHolder,position: Int){
        val images = getItem(position)
        holder.bind(images)
    }

}