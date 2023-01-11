package com.example.images.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.images.R
import com.example.images.databinding.FragmentImageListBinding
import com.example.images.viewmodel.ImagesViewModel
import com.example.images.databinding.ViewItemBinding
import com.example.images.domain.ImagesModels
import kotlinx.coroutines.launch


class ImageListFragment : Fragment() {

    private val SEARCH_PREFIX = "https://unsplash.com"


    private val viewModel: ImagesViewModel by lazy{
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this,ImagesViewModel.Factory(activity.application))
            .get(ImagesViewModel::class.java)
    }

    private var viewModelAdapter: ImageAdpter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<ImagesModels>> {
            images -> images?.apply {
            viewModelAdapter?.images = images
        }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentImageListBinding = DataBindingUtil
            .inflate(inflater,R.layout.fragment_image_list,container,false)

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel
        viewModelAdapter =ImageAdpter(ImageClick{

            val packageManager = context?.packageManager ?: return@ImageClick
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if(intent.resolveActivity(packageManager) == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.download_url))
            }
            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        //TODO refresh code
        binding.mySwipeRefreshLayout.setOnRefreshListener {
            Log.i("Refresh", "onRefresh called from SwipeRefreshLayout")

            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.imagesRepository.refreshImages()
                binding.mySwipeRefreshLayout.isRefreshing = false
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    // test
    private val ImagesModels.launchUri: Uri
        get() {
            val httpUri = Uri.parse(download_url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }

    //////////////////////

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


class ImageClick(val block: (ImagesModels) -> Unit) {

    fun onClick(image: ImagesModels) = block(image)
}

class ImageAdpter(val callback: ImageClick) : RecyclerView.Adapter<ImageViewHolder>() {
    var images:List<ImagesModels> = emptyList()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val withDataBinding:ViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ImageViewHolder.LAYOUT,
            parent,
            false)
        return ImageViewHolder(withDataBinding)
    }
    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.image = images[position]
            it.imageCallback = callback
        }
    }
}

class ImageViewHolder(val viewDataBinding: ViewItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.view_item
    }
}