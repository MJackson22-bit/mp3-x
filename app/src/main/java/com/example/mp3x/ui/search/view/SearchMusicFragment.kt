package com.example.mp3x.ui.search.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mp3x.R
import com.example.mp3x.databinding.FragmentSearchMusicBinding
import com.example.mp3x.ui.base.BaseFragment
import com.example.mp3x.ui.search.viewmodel.SearchMusicViewModel

class SearchMusicFragment : BaseFragment<FragmentSearchMusicBinding>(FragmentSearchMusicBinding::inflate) {

    companion object {
        fun newInstance() = SearchMusicFragment()
    }

    private lateinit var viewModel: SearchMusicViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}