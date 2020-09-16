package com.irfan.project.testuseradmin.adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.activities.MainActivity
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import com.irfan.project.testuseradmin.models.Barang
import com.irfan.project.testuseradmin.models.DefaultResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
                beliItem(barang.id)
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
                        doUpdate(barang)
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

    fun beliItem(itemid : Int){
        val executeData = MethodHelpers.doRetrofitExecute().beliBarang(
            PrefsHelper(ctx).getUserID(),
            itemid)
        executeData.enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                e("TAGERROR", t.message!!)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.isSuccessful){
                    MethodHelpers.showShortToast(ctx, response.body()!!.message)
                    val point  = PrefsHelper(ctx).getUserPoint()
                    PrefsHelper(ctx).setUserPoint(point + 5)
                    ctx.startActivity(Intent(ctx, MainActivity::class.java))
                    (ctx as Activity).finish()
                    (ctx as Activity).overridePendingTransition(0,0)
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.string())
                    MethodHelpers.showShortToast(ctx, jsonObj.getString("message"))
                }
            }

        })
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

    fun doUpdate(barang : Barang){
        val dialog = Dialog(ctx)
        dialog.setContentView(R.layout.pop_uploadbarang)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT )
        val etnamaBarang : EditText = dialog.findViewById(R.id.etNamaBarang)
        val etHargaBarang : EditText = dialog.findViewById(R.id.etHargaBarang)
        val etStock : EditText = dialog.findViewById(R.id.etStock)
        val btnUpload : Button = dialog.findViewById(R.id.btnUploadB)
        val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
        etnamaBarang.setText(barang.namabarang)
        etHargaBarang.setText(barang.hargabarang.toString())
        etStock.setText(barang.stock.toString())

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnUpload.setOnClickListener {
            val executedata = MethodHelpers.doRetrofitExecute()
            executedata.updateBarang(
                barang.id,
                etnamaBarang.text.toString(),
                etHargaBarang.text.toString().toDouble(),
                etStock.text.toString().toInt()
            ).enqueue(object : Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("TAGERROR", t.message!!)
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if(response.isSuccessful){
                        ctx.startActivity(Intent(ctx, MainActivity::class.java))
                        (ctx as Activity).finish()
                        (ctx as Activity).overridePendingTransition(0,0)
                        MethodHelpers.showShortToast(ctx, response.body()!!.message)
                        dialog.dismiss()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        MethodHelpers.showShortToast(ctx, jsonObj.getString("message"))
                        dialog.dismiss()
                    }
                }

            })
        }

        dialog.show()
    }
}