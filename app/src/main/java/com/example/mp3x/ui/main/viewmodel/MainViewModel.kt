package com.example.mp3x.ui.main.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import com.example.mp3x.model.Music
import com.example.mp3x.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val mIsPermission = MutableLiveData(false)
    val isPermission get() = mIsPermission

    private val mListMusic = MutableLiveData(mutableListOf<Music>())
    val listMusic get() = mListMusic

    private val mMusicPlaying = MutableLiveData(MediaPlayer())
    val musicPlaying get() = mMusicPlaying

    fun setPermission(permission: Boolean){
        mIsPermission.postValue(permission)
    }

    fun addAllMusic(songs: MutableList<Music>){
        mListMusic.postValue(songs)
    }

    fun setMusicPlayer(musicPlayer: MediaPlayer){
        mMusicPlaying.postValue(musicPlayer)
    }
}