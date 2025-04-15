package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemWalletBinding
import com.example.myapplication.model.WalletItem

class WalletAdapter : ListAdapter<WalletItem, WalletAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(
        private val binding: ItemWalletBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalletItem) {
            // 加载图标
            Glide.with(binding.root)
                .load(item.currency.imageUrl)
                .circleCrop()
                .into(binding.ivCurrencyIcon)

            binding.tvCurrencyName.text = item.currency.name

            // 显示本地余额和美元价值
            binding.tvUSBalance.text = "%.8f ${item.currency.symbol.uppercase()}"
                .format(item.balance.amount)
            binding.tvUsdValue.text = "≈$%.2f".format(item.usdValue)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WalletItem>() {
        override fun areItemsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
            return oldItem.currency.id == newItem.currency.id
        }

        override fun areContentsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}