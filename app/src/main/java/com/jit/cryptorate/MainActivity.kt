package com.jit.cryptorate

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jit.cryptorate.databinding.ActivityMainBinding
import com.jit.cryptorate.viewmodel.CryptoViewModel
import com.jit.cryptorate.viewmodel.CryptoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
@SuppressLint("SimpleDateFormat", "SetTextI18n")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CryptoAdapter

    private val viewModel:CryptoViewModel by viewModels{
        CryptoViewModelFactory(
            (application as CryptoApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CryptoAdapter()
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.apply {
            recylerView.adapter = adapter
            recylerView.layoutManager = manager
        }

        onInitialize()

        binding.fab.setOnClickListener {
            onInitialize()
        }
    }

    private fun onInitialize() {
        viewModel.getCryptoValues()
        viewModel.cryptoValue.observe(this){

            adapter.submitList(it.data)

            val timestamp = Date(it.timestamp)

            binding.titleText.text = "Last Update: $timestamp"
        }
    }


}