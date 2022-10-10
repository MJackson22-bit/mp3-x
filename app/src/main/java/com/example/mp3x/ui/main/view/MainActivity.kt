package com.example.mp3x.ui.main.view

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3x.adapter.MusicAdapter
import com.example.mp3x.databinding.ActivityMainBinding
import com.example.mp3x.ui.main.viewmodel.MainViewModel
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicAdapter

    private var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runtimePermission()
        initRecycler()
        setObservers()
    }

    private fun setObservers() {

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
        val uri = Uri.parse(item.uri.toString())
        if(mediaPlayer.isPlaying){
            mediaPlayer.release()
        }
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(applicationContext, uri)
            prepare()
            start()
        }
    }

    private fun runtimePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermission()
        }else{
            viewModel.displayMusic(this)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val pos = savedInstanceState.getInt("posicion")
        mediaPlayer.seekTo(pos)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val pos = mediaPlayer.currentPosition
        outState.putInt("posicion", pos)
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
        runtimePermission()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.seekTo(mediaPlayer.currentPosition)
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayer.seekTo(mediaPlayer.currentPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.seekTo(mediaPlayer.currentPosition)
    }

    private fun getDuration(uri: Uri): Long? {
        val retriever =  MediaMetadataRetriever()
        retriever.setDataSource(this, uri)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeMilliSec = time?.toLong()
        retriever.release()
        return timeMilliSec
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
                viewModel.displayMusic(context = this)
            }else{
                Toast.makeText(this, "Go to Settings and accept the permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }
}