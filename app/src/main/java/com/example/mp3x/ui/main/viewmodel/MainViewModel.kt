package com.example.mp3x.ui.main.viewmodel

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mp3x.model.Music
import com.example.mp3x.provider.ProviderMusic

class MainViewModel : ViewModel(){
    private val mIsPermission = MutableLiveData(false)
    val isPermission get() = mIsPermission

    private val mListMusic = MutableLiveData(mutableListOf<Music>())
    val listMusic get() = mListMusic

    fun setPermission(permission: Boolean){
        mIsPermission.postValue(permission)
    }

    fun addAllMusic(songs: MutableList<Music>){
        mListMusic.postValue(songs)
    }
}