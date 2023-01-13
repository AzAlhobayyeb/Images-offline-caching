package com.example.images.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.images.R
import com.example.images.databinding.ViewItemBinding
import com.example.images.domain.ImagesModels
import com.example.images.ui.ImageListFragmentDirections
import com.example.images.viewmodel.ImagesViewModel

class ImageGridAdpter(viewModel: ImagesViewModel)
    :ListAdapter<ImagesModels,ImageGridAdpter.ImageViewHolder>(DiffCallback) {

    val viewModel = viewModel
    companion object DiffCallback : DiffUtil.ItemCallback<ImagesModels>() {
        override fun areItemsTheSame(oldItem: ImagesModels, newItem: ImagesModels): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImagesModels, newItem: ImagesModels): Boolean {
            return oldItem.download_url == newItem.download_url && oldItem.author == newItem.author
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
        holder.bind(images)

        holder.card.setOnClickListener {
            val action = ImageListFragmentDirections.actionImageListFragmentToImageDetailsFragment(
                author = images.author, downloadUrl = images.download_url
            )
            holder.itemView.findNavController().navigate(action)
    }
    }

    class ImageViewHolder(
        var binding: ViewItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val card : CardView = binding.cardView

        fun bind(imagesModels: ImagesModels) {
            binding.image = imagesModels
            binding.executePendingBindings()
        }
    }
}
