package com.example.mp3x.ui.musicdetail.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mp3x.R
import com.example.mp3x.ui.musicdetail.viewmodel.MusicDetailViewModel

class MusicDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MusicDetailFragment()
    }

    private lateinit var viewModel: MusicDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MusicDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}