package com.example.mp3x.ui.musicdetail.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.mp3x.R
import com.example.mp3x.databinding.FragmentMusicDetailBinding
import com.example.mp3x.model.Music
import com.example.mp3x.ui.main.viewmodel.MainViewModel
import com.example.mp3x.ui.musicdetail.viewmodel.MusicDetailViewModel

class MusicDetailFragment : Fragment() {

    private val viewModel: MusicDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: MusicDetailFragmentArgs by navArgs()
    private var music: Music? = null

    private var _binding: FragmentMusicDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findMusic()
    }

    private fun findMusic(){
        mainViewModel.listMusic.value?.forEach {
            if(args.musicId == it.id){
                setMusic(it)
                return@forEach
            }
        }
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
}