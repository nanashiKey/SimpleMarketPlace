package com.irfan.project.testuseradmin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.models.Barang


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class BarangAdapter : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>{
    constructor(){ }
    private lateinit var ctx: Context
    private lateinit var barangs: ArrayList<Barang>
    constructor(ctx : Context , barangs : ArrayList<Barang>){
        this.ctx = ctx
        this.barangs = barangs
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item_product, parent,false)
        return BarangViewHolder(v)
    }

    override fun getItemCount(): Int {
        return barangs.size
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangs.get(position)
        holder.tvItemName.text = barang.namabarang
        val harga = MethodHelpers.changeCurrencytoIDR(barang.hargabarang)
        holder.tvItemPrice.text = harga
        holder.tvItemStock.text = "stock : ${barang.stock}"
        holder.btnBeli.setOnClickListener {
            MethodHelpers.showShortToast(ctx, barang.namabarang)
        }

    }

    class BarangViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var llayout : LinearLayout
        var imgMarket : ImageView
        var tvItemName : TextView
        var tvItemPrice : TextView
        var tvItemStock : TextView
        var btnBeli : Button
        init {
            llayout = itemView.findViewById(R.id.llayout)
            imgMarket = itemView.findViewById(R.id.imgMarket)
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice)
            tvItemStock = itemView.findViewById(R.id.tvItemStock)
            btnBeli = itemView.findViewById(R.id.btnBeli)
        }
    }
}