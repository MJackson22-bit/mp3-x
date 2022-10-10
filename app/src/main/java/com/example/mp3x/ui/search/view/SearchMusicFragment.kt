package com.example.mp3x.ui.search.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mp3x.R
import com.example.mp3x.ui.search.viewmodel.SearchMusicViewModel

class SearchMusicFragment : Fragment() {

    companion object {
        fun newInstance() = SearchMusicFragment()
    }

    private lateinit var viewModel: SearchMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchMusicViewModel::class.java)
        // TODO: Use the ViewModel
    }

}