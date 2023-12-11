package com.jit.cryptorate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jit.cryptorate.data.Data
import com.jit.cryptorate.databinding.CustomLayoutBinding
import javax.inject.Inject

class CryptoAdapter @Inject constructor():ListAdapter<Data,CryptoAdapter.CryptoHolder>(DiffUtilCall) {

    private var onAdapterClickListener:OnAdapterClickListener? = null

    fun bindListener(onAdapterClickListener: OnAdapterClickListener){
        this.onAdapterClickListener = onAdapterClickListener
    }

    inner class CryptoHolder(private val binding: CustomLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(cryptoData: Data){
                binding.nameText.text = cryptoData.name
                binding.codeText.text = cryptoData.symbol
                val price = String.format("%.3f",cryptoData.priceUsd?.toDouble())
                binding.rateText.text = "$${price}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
       val inflater =  LayoutInflater.from(parent.context)
        val binding = CustomLayoutBinding.inflate(inflater,parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
       val cryptoData = getItem(position)
        holder.bind(cryptoData)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,cryptoData.name,Toast.LENGTH_LONG).show()
            onAdapterClickListener?.onAdapterItemClick(cryptoData.explorer)
        }
    }
    companion object DiffUtilCall:DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.priceUsd == newItem.priceUsd
        }
    }
}

interface OnAdapterClickListener{
    /**
     * Adapter click listener
     */
    fun onAdapterItemClick(url:String?)
}