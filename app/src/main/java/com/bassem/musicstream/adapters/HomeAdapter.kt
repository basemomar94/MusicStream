package com.bassem.musicstream.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.entities.Song
import com.bumptech.glide.Glide

class HomeAdapter(
    var allSongsList: MutableList<Song>,
    val context: Context,
    val listner: HomeInterface
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.bookImage)

        init {
            image.setOnClickListener {
                val song = allSongsList[absoluteAdapterPosition]
                listner.viewBook(song, allSongsList, absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_home_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = allSongsList[position]
        val photo = book.coverLink
        val imageView = holder.image
        Glide.with(context).load(photo).into(imageView)


    }

    override fun getItemCount(): Int {
        return allSongsList.size
    }

    fun addList(list: MutableList<Song>) {
        allSongsList = list
    }

    interface HomeInterface {
        fun viewBook(song: Song, list: MutableList<Song>, position: Int)
    }
}