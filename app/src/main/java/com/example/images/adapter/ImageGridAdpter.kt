package com.example.images.adapter


import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.images.databinding.ViewItemBinding
import com.example.images.network.ImagesData
import com.example.images.ui.ImageListFragment
import com.example.images.ui.ImageListFragmentDirections


class ImageGridAdpter(val clickListener:ImageListener)
    :ListAdapter<ImagesData,ImageGridAdpter.ImageViewHolder>(DiffCallback) {

    class ImageViewHolder(
        var binding: ViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ImageListener, imagesData: ImagesData) {
            binding.image = imagesData
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ImagesData>() {
        override fun areItemsTheSame(oldItem: ImagesData, newItem: ImagesData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImagesData, newItem: ImagesData): Boolean {
            return oldItem.url == newItem.url && oldItem.author == newItem.author
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(
            ViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val images = getItem(position)
        holder.bind(clickListener, images)
    }
}
    class ImageListener(val clickListener:(imageData: ImagesData) ->Unit){
        fun onCLick(imageData: ImagesData) = clickListener(imageData)
    }
