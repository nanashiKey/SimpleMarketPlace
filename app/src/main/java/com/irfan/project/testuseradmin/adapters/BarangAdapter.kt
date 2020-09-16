package com.irfan.project.testuseradmin.adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
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
            val dialog = Dialog(ctx)
            dialog.setContentView(R.layout.pop_beliitems)
            dialog.setCancelable(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT )
            val btnBeli : Button = dialog.findViewById(R.id.btnBeli)
            val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
            btnBeli.setOnClickListener {
                MethodHelpers.showShortToast(ctx, barang.namabarang)
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        val id = PrefsHelper(ctx).getUserID()
        if(id == 1){
            holder.llayout.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(p0: View?): Boolean {
                    val dialog = Dialog(ctx)
                    dialog.setContentView(R.layout.pop_updel)
                    dialog.window!!.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT )
                    val btnUpdate : Button = dialog.findViewById(R.id.btnUpdate)
                    val btnDel : Button = dialog.findViewById(R.id.btnDel)
                    btnUpdate.setOnClickListener {
                        dialog.dismiss()
                    }
                    btnDel.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()
                    return true
                }
            })
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