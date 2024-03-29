package com.jit.cryptorate

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.databinding.ActivityMainBinding
import com.jit.cryptorate.utils.NetworkResult
import com.jit.cryptorate.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("SimpleDateFormat", "SetTextI18n")
class MainActivity : AppCompatActivity(), OnAdapterClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CryptoViewModel
    private lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var adapter: CryptoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.apply {
            recylerView.adapter = adapter
            recylerView.layoutManager = manager
        }

        adapter.bindListener(this)


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
                    bindCryptoResult(it.resultData!!)
                }
                is NetworkResult.Error ->{
                    val message = "${it.code}: ${it.message}"
                    Toast.makeText(this@MainActivity,message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.NetworkError ->{
                    bindCryptoResult(it.resultData!!)
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Exception ->{
                    Toast.makeText(this@MainActivity, it.e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindCryptoResult(
        resultData: CryptoData
    ) {
        adapter.submitList(resultData.data)
        val timestamp = Date(resultData.timestamp)
        binding.titleText.text = "Last Update: $timestamp"
    }

    override fun onAdapterItemClick(url: String?) {
        if(url != null)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        else
            Toast.makeText(this,"Unable To View", Toast.LENGTH_SHORT)
                .show()
    }
}