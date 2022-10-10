package com.example.mp3x.provider

import com.example.mp3x.model.Music
import java.io.File

class ProviderMusic {
    companion object{
        var listMusic: MutableList<Music> = mutableListOf()
    }
}