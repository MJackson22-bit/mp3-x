package com.example.mp3x.ui.music.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3x.R
import com.example.mp3x.adapter.MusicAdapter
import com.example.mp3x.databinding.FragmentMusicBinding
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic
import com.example.mp3x.ui.main.viewmodel.MainViewModel
import com.example.mp3x.ui.music.viewmodel.MusicViewModel
import com.example.mp3x.ui.musicdetail.view.MusicDetailFragmentDirections

class MusicFragment : Fragment() {
    private val viewModel: MusicViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!
    private var musicCurrent: Music? = null

    private lateinit var adapter: MusicAdapter
    private var mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        setObservers()
        setListener()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel.displayMusic(requireContext(), mainViewModel)
    }

    private fun setListener() {
        binding.lavPlayPause.setOnClickListener {
            handleStateMusic()
        }
        binding.lavFavorite.setOnClickListener {
            binding.lavFavorite.playAnimation()
        }
        binding.musicCurrent.setOnClickListener {
            val action = MusicFragmentDirections.actionMusicFragmentToMusicDetailFragment(musicCurrent?.id!!)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun handleStateMusic(){
        if(mediaPlayer.isPlaying){
            binding.lavPlayPause.frame = 0
            mediaPlayer.seekTo(mediaPlayer.currentPosition)
            mediaPlayer.pause()
        }else{
            binding.lavPlayPause.frame = 60
            mediaPlayer.start()
        }
    }

    private fun setObservers() {

    }

    private fun initRecycler() {
        adapter = MusicAdapter(
            listMusic = ProviderMusic.listMusic,
            onClickListener = {music -> onItemSelected(music) }
        )
        val manager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }

    private fun onItemSelected(item: Music) {
        musicCurrent = item
        val uri = Uri.parse(item.uri.toString())
        if(mediaPlayer.isPlaying){
            mediaPlayer.release()
        }
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(requireContext(), uri)
            prepare()
            start()
        }
        configureToolsReproduce(item)
    }

    private fun configureToolsReproduce(item: Music) {
        binding.tvTitleMusic.text = item.name
        binding.tvAuthorMusic.text = item.author
        val imageAlbum = getAlbumImage(item.path)
        if(imageAlbum == null){
            binding.ivAlbumSelected.setImageResource(R.drawable.unknown_album)
        }else{
            binding.ivAlbumSelected.setImageBitmap(imageAlbum)
        }
    }

    private fun getAlbumImage(path: String): Bitmap? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        val data = mmr.embeddedPicture
        return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        if(musicCurrent != null){
            configureToolsReproduce(musicCurrent!!)
        }
    }
}