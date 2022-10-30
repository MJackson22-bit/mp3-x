package com.example.mp3x.ui.library.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mp3x.R
import com.example.mp3x.databinding.FragmentLibraryBinding
import com.example.mp3x.databinding.FragmentMusicBinding
import com.example.mp3x.ui.base.BaseFragment
import com.example.mp3x.ui.library.viewmodel.LibraryViewModel

class LibraryFragment : BaseFragment<FragmentLibraryBinding>(FragmentLibraryBinding::inflate) {
    private lateinit var viewModel: LibraryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}