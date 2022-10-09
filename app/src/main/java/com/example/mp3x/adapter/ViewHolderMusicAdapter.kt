package com.example.mp3x.adapter

import android.content.DialogInterface.OnClickListener
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mp3x.databinding.ItemMusicBinding
import com.example.mp3x.model.Music

class ViewHolderMusicAdapter(view: View) : RecyclerView.ViewHolder(view){
    private val binding = ItemMusicBinding.bind(view)

    fun render(item: Music, onClickListener: (Music) -> Unit){
        Log.i("renderMusic", item.toString())
    }
}