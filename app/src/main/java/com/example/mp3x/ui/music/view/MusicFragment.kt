package com.example.mp3x.ui.music.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mp3x.R
import com.example.mp3x.adapter.MusicAdapter
import com.example.mp3x.databinding.FragmentMusicBinding
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic
import com.example.mp3x.ui.base.BaseFragment
import com.example.mp3x.ui.main.viewmodel.MainViewModel
import com.example.mp3x.ui.music.viewmodel.MusicViewModel

class MusicFragment : BaseFragment<FragmentMusicBinding>(FragmentMusicBinding::inflate) {
    private val viewModel: MusicViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var musicCurrent: Music? = null

    private lateinit var adapter: MusicAdapter

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
        if(mainViewModel.musicPlaying.value?.isPlaying == true){
            binding.lavPlayPause.frame = 0
            mainViewModel.musicPlaying.value?.seekTo(mainViewModel.musicPlaying.value!!.currentPosition)
            mainViewModel.musicPlaying.value?.pause()
        }else{
            binding.lavPlayPause.frame = 60
            mainViewModel.musicPlaying.value?.start()
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
        if(mainViewModel.musicPlaying.value?.isPlaying == true){
            mainViewModel.musicPlaying.value?.release()
        }
        val mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(requireContext(), uri)
            prepare()
            start()
        }
        mainViewModel.setMusicPlayer(mediaPlayer)
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