package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemWalletBinding
import com.example.myapplication.model.WalletItem
import com.example.myapplication.util.DecimalFormatUtil

class WalletAdapter : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {
    private var items: List<WalletItem> = emptyList()

    class ViewHolder(
        private val binding: ItemWalletBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalletItem) {
            // 加载图标
            Glide.with(binding.root).load(item.currency.imageUrl).circleCrop().into(binding.ivCurrencyIcon)

            binding.tvCurrencyName.text = item.currency.name
            // 显示本地余额和美元价值
            binding.tvUSBalance.text = DecimalFormatUtil().formatDecimal(item.balance.amount) + " " + (item.currency.symbol ?: "").uppercase()
            binding.tvUsdValue.text = "$" + DecimalFormatUtil().formatDecimal(item.usdValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<WalletItem>) {
        items = newItems
        notifyDataSetChanged() // 全量更新todo 可优化部分更新
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}