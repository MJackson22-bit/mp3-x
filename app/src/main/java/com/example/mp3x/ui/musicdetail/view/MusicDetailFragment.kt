package com.example.mp3x.ui.musicdetail.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mp3x.R
import com.example.mp3x.databinding.FragmentMusicDetailBinding
import com.example.mp3x.model.Music
import com.example.mp3x.ui.base.BaseFragment
import com.example.mp3x.ui.main.viewmodel.MainViewModel
import com.example.mp3x.ui.musicdetail.viewmodel.MusicDetailViewModel
import com.google.android.material.slider.Slider

class MusicDetailFragment : BaseFragment<FragmentMusicDetailBinding>(FragmentMusicDetailBinding::inflate), Slider.OnChangeListener {

    private val viewModel: MusicDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: MusicDetailFragmentArgs by navArgs()
    private var music: Music? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findMusic()
        setListener()
    }

    private fun setListener() {
        binding.lavPlayPause.setOnClickListener {
            Log.i("duration", mainViewModel.musicPlaying.value?.currentPosition.toString())
        }
        binding.btnBackPressed.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun findMusic(){
        mainViewModel.listMusic.value?.forEach {
            if(args.musicId == it.id){
                setMusic(it)
                initializeSeekbar()
                return@forEach
            }
        }
    }

    private fun initializeSeekbar() {
        binding.sliderProgressMusic.addOnChangeListener(this)
        binding.sliderProgressMusic.valueFrom = 0f
        binding.sliderProgressMusic.valueTo = getDuration(mainViewModel.musicPlaying.value!!) / 60
    }

    private fun getDuration(player: MediaPlayer): Float{
        return player.duration / 1000f;
    }

    private fun setMusic(music: Music){
        val imageAlbum = getAlbumImage(music.path)
        if(imageAlbum != null){
            binding.ivAlbum.setImageBitmap(imageAlbum)
        }else{
            binding.ivAlbum.setImageResource(R.drawable.unknown_album)
        }
        binding.tvAuthorMusic.text = music.author
        binding.tvTitleMusic.text = music.name
    }

    private fun getAlbumImage(path: String): Bitmap? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        val data = mmr.embeddedPicture
        return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        val secs = (value * 1000) * 60
        mainViewModel.musicPlaying.value?.seekTo(secs.toInt())
    }
}