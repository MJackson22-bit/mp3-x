package com.example.mp3x.ui.main.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.mp3x.databinding.ActivityMainBinding
import com.example.mp3x.ui.base.BaseActivity
import com.example.mp3x.ui.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runtimePermission()
        setObservers()
        setListener()
    }

    private fun setListener() {
        binding.layoutPermissionNoRequest.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        runtimePermission()
    }

    private fun setObservers() {
        viewModel.isPermission.observe(this){
            binding.layoutPermissionNoRequest.isVisible = !it
            binding.fragmentContainer.isVisible = it
        }
    }


    private fun runtimePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            viewModel.setPermission(false)
            requestPermission()
        }else{
            viewModel.setPermission(true)
        }
    }


    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            viewModel.setPermission(false)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 777)
        }else{
            Toast.makeText(this, "Go to Settings and accept the permissions", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                viewModel.setPermission(true)
            }else{
                Toast.makeText(this, "Go to Settings and accept the permissions", Toast.LENGTH_SHORT).show()
                viewModel.setPermission(false)
            }
        }
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
}