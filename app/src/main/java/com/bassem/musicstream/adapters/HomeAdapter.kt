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
    var allBooksList: MutableList<Song>,
    val context: Context
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.bookImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = allBooksList[position]
        val photo = book.coverLink
        val imageView = holder.image
        Glide.with(context).load(photo).into(imageView)

    }

    override fun getItemCount(): Int {
        return allBooksList.size
    }

    fun addList(list: MutableList<Song>) {
        allBooksList = list
    }
}