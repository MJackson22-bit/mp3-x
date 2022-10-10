package com.example.mp3x.model

import android.net.Uri

data class Music(val id: Long, val name: String, val author: String, val album: String, val path: String, val uri: Uri)
