package com.jit.cryptorate

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.databinding.ActivityMainBinding
import com.jit.cryptorate.utils.NetworkResult
import com.jit.cryptorate.viewmodel.CryptoViewModel
import com.jit.cryptorate.viewmodel.CryptoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@SuppressLint("SimpleDateFormat", "SetTextI18n")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CryptoViewModel
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var viewModelFactory: CryptoViewModelFactory

    @Inject
    lateinit var adapter: CryptoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as CryptoApplication).cryptoComponent.inject(this)

        viewModel = ViewModelProvider(this,viewModelFactory).get(CryptoViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")

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
            progressDialog.dismiss()

            when(it){

                is NetworkResult.Loading ->{
                    progressDialog.show()
                }
                is NetworkResult.Success ->{
                    bindCryptoResult(it, it.resultData!!)
                }
                is NetworkResult.Error ->{
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.NetworkError ->{

                    bindCryptoResult(it, it.resultData!!)
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindCryptoResult(
        it: NetworkResult<CryptoData>,
        resultData: CryptoData
    ) {
        adapter.submitList(it.resultData!!.data)
        val timestamp = Date(resultData.timestamp)
        binding.titleText.text = "Last Update: $timestamp"
    }
}