package com.example.mp3x.view

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3x.adapter.MusicAdapter
import com.example.mp3x.databinding.ActivityMainBinding
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runtimePermission()
        initRecycler()
    }

    private fun initRecycler() {
        adapter = MusicAdapter(
            listMusic = ProviderMusic.listMusic,
            onClickListener = {music -> onItemSelected(music) }
        )
        val manager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }

    private fun onItemSelected(item: Music) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
    }

    private fun runtimePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermission()
        }else{
            displayMusic()
        }
    }

    private fun displayMusic() {
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
        val audioCursor = this.contentResolver?.query(audioQuery, audioProjection, null, null, null)

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

                    val music = Music(name, artistName, album, data)

                    ProviderMusic.listMusic.add(music)


                } while (audioCursor.moveToNext())
            }
            audioCursor.close()
        }
        Log.i("allMusics", ProviderMusic.listMusic.toString())
    }

    private fun getDuration(uri: Uri): Long? {
        val retriever =  MediaMetadataRetriever()
        retriever.setDataSource(this, uri)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeMilliSec = time?.toLong()
        retriever.release()
        return timeMilliSec
    }

    override fun onResume() {
        super.onResume()
        runtimePermission()
    }

    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 777)
        }else{
            Toast.makeText(this, "Go to Settings and accept the permissions", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                displayMusic()
            }else{
                Toast.makeText(this, "Go to Settings and accept the permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }
}