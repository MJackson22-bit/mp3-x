package com.example.mp3x.adapter

import android.content.DialogInterface.OnClickListener
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mp3x.R
import com.example.mp3x.databinding.ItemMusicBinding
import com.example.mp3x.model.Music
import java.io.File

class ViewHolderMusicAdapter(view: View) : ViewHolder(view){
    private val binding = ItemMusicBinding.bind(view)

    fun render(item: Music, onClickListener: (Music) -> Unit){
        binding.tvAuthorMusic.text = item.author
        binding.tvTitleMusic.text = item.name
        val bitmapImageAlbum = getAlbumImage(item.path)
        if(bitmapImageAlbum == null){
            binding.ivAlbum.setImageResource(R.drawable.unknown_album)
        }else{
            binding.ivAlbum.setImageBitmap(bitmapImageAlbum)
        }
        Log.i("bitmapImage", getAlbumImage(item.path).toString())
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }

    private fun getAlbumImage(path: String): Bitmap? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        val data = mmr.embeddedPicture
        return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
    }
}