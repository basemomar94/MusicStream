package com.bassem.musicstream.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.entities.Song
import com.bumptech.glide.Glide

class SongsListAdapter(
    var allSongsList: MutableList<Song>,
    val context: Context,
    val listner: HomeInterface
) : RecyclerView.Adapter<SongsListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.photoList)
        val name = itemView.findViewById<TextView>(R.id.nameList)

        init {
            itemView.setOnClickListener {
                val song = allSongsList[absoluteAdapterPosition]
                listner.viewBook(song, allSongsList, absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = allSongsList[position]
        val photo = song.coverLink
        val imageView = holder.image
        holder.name.text = song.name
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