package com.fatih.itunessmoviesearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.itunessmoviesearchapp.R
import com.fatih.itunessmoviesearchapp.data.local.FavFilms
import com.fatih.itunessmoviesearchapp.presentation.Util.downloadFromUrl

class FavFilmAdapter : RecyclerView.Adapter<FavFilmAdapter.FavViewHolder>() {

    class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtFilmAdi : TextView = itemView.findViewById(R.id.filmAdi)
        val imgGet : ImageView = itemView.findViewById(R.id.imageView2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rroom,parent,false)
        return FavViewHolder(view)
    }
    override fun getItemCount() = differ.currentList.size
    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.txtFilmAdi.text = differ.currentList[position].fName
        holder.imgGet.downloadFromUrl(differ.currentList[position].fImg)
    }
    private val differCallback = object : DiffUtil.ItemCallback<FavFilms>(){
        override fun areItemsTheSame(oldItem: FavFilms, newItem: FavFilms): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: FavFilms,
            newItem: FavFilms
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)
}