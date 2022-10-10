package com.example.mp3x.ui.music.viewmodel

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic

class MusicViewModel : ViewModel() {
    fun displayMusic(context: Context) {
        val audioProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM
        )

        val audioQuery = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audioCursor = context.contentResolver?.query(audioQuery, audioProjection, null, null, null)

        if (audioCursor != null && audioCursor.count > 0) {
            if (audioCursor.moveToFirst()) {
                val idColumn = audioCursor.getColumnIndex(MediaStore.Video.Media._ID)
                val data = audioCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                val dateAddedColumn = audioCursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)
                val titleColumn = audioCursor.getColumnIndex(MediaStore.Video.Media.TITLE)
                val displayName = audioCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                val artists = audioCursor.getColumnIndex( MediaStore.Audio.Media.ARTIST)
                val size = audioCursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
                val album = audioCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                do {
                    val id = audioCursor.getLong(idColumn)
                    val data = audioCursor.getString(data)
                    val dateAdded = audioCursor.getString(dateAddedColumn)
                    val name = audioCursor.getString(titleColumn)
                    val nameWithFormat = audioCursor.getString(displayName)
                    val artistName = audioCursor.getString(artists)
                    val sizeSpace = audioCursor.getLong(size)
                    val album = audioCursor.getString(album)
                    val uri = ContentUris.withAppendedId(audioQuery,id)

                    val music = Music(
                        id = id,
                        name = name,
                        author = artistName,
                        album = album,
                        path = data,
                        uri = uri
                    )

                    ProviderMusic.listMusic.add(music)


                } while (audioCursor.moveToNext())
            }
            audioCursor.close()
        }
        Log.i("allMusics", ProviderMusic.listMusic.toString())
    }
}