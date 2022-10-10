package com.example.mp3x.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.mp3x.R
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3x.model.Music
import java.io.File

class MusicAdapter(
    private val listMusic: MutableList<Music>,
    private val onClickListener: (Music) -> Unit
) : RecyclerView.Adapter<ViewHolderMusicAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMusicAdapter {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderMusicAdapter(layoutInflater.inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderMusicAdapter, position: Int) {
        val item = listMusic[position]
        holder.render(item, onClickListener)
    }

    fun updateList(listMusic: MutableList<File>){

    }

    override fun getItemCount() = listMusic.size
}