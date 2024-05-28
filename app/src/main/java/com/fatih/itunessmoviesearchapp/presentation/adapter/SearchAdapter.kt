package com.fatih.itunessmoviesearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatih.itunessmoviesearchapp.R
import com.fatih.itunessmoviesearchapp.data.dto.Movie
import com.fatih.itunessmoviesearchapp.presentation.Util.downloadFromUrl
import com.fatih.itunessmoviesearchapp.presentation.view.MainFragmentDirections

class SearchAdapter : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DataItemComp) {

    private var isLoading = false
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder){
            holder.bind(isLoading)
        } else {
            val item = getItem(position)
            if (item != null){
                (holder as DataItemViewHolder).bind(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.progress_bar){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_bar,parent,false)
            FooterViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent,false)
            DataItemViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount -1 && isLoading){
            R.layout.progress_bar
        } else {
            R.layout.item_search
        }
    }

    fun showLoading(isLoading : Boolean){
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    class DataItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val movieName : TextView = itemView.findViewById(R.id.filmName)
        private val imgUrl : ImageView = itemView.findViewById(R.id.imageView)
        fun bind(movie: Movie){
            movieName.text = movie.collectionName
            imgUrl.downloadFromUrl(movie.artworkUrl100)
            imgUrl.setOnClickListener {
                val imgUrl = movie.artworkUrl100
                val filmAdi = movie.collectionName ?: "null"
                val filmDiscrp = movie.longDescription
                val filmLink = movie.previewUrl
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(imgUrl =imgUrl,filmAdi=filmAdi, filmDescription = filmDiscrp,filmLink)
                Navigation.findNavController(it).navigate(action)
            }

        }

    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)
        fun bind(isLoading : Boolean){
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    object DataItemComp : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}