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
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.activities.MainActivity
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import com.irfan.project.testuseradmin.models.Barang
import com.irfan.project.testuseradmin.models.DefaultResponse
import com.irfan.project.testuseradmin.models.Hadiah
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HadiahAdapter : RecyclerView.Adapter<HadiahAdapter.HadiahViewHolder>{

    lateinit var ctx : Context
    lateinit var hadiahs : ArrayList<Hadiah>
    constructor()
    constructor(ctx : Context, hadiahs : ArrayList<Hadiah>){
        this.ctx = ctx
        this.hadiahs = hadiahs
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HadiahViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item_hadiah, parent, false)
        return HadiahViewHolder(v)
    }

    override fun getItemCount(): Int {
        return hadiahs.size
    }

    override fun onBindViewHolder(holder: HadiahViewHolder, position: Int) {
        val hadiah = hadiahs.get(position)
        holder.tvItemName.text = hadiah.namahadiah
        holder.tvItemPoint.text = "Point : ${hadiah.point}"
        holder.tvItemStock.text = "Stock : ${hadiah.banyakitem}"

        holder.btnTukarPoint.setOnClickListener {
            redeemedHadiah(hadiah.id!!, hadiah.point!!)
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
                        doUpdate(hadiah)
                        dialog.dismiss()
                    }
                    btnDel.setOnClickListener {
                        doDelete(hadiah.id!!)
                        dialog.dismiss()
                    }
                    dialog.show()
                    return true
                }
            })
        }
    }

    class HadiahViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tvItemName : TextView
        var tvItemPoint : TextView
        var tvItemStock : TextView
        var btnTukarPoint : Button
        var llayout : LinearLayout
        init {
            llayout = itemView.findViewById(R.id.llayout)
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemPoint = itemView.findViewById(R.id.tvItemPoint)
            btnTukarPoint = itemView.findViewById(R.id.btnHadiah)
            tvItemStock = itemView.findViewById(R.id.tvItemStock)
        }
    }

    fun redeemedHadiah(hadiahId : Int, hadiahPoint : Int){
        val executeData = MethodHelpers.doRetrofitExecute()
        val usrId = PrefsHelper(ctx).getUserID()
        executeData.redeemedHadiah(hadiahId, usrId)
            .enqueue(object : Callback<DefaultResponse>{
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    e("TAGERROR", t.message!!)
                    t.printStackTrace()
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
                        val points = PrefsHelper(ctx).getUserPoint()
                        PrefsHelper(ctx).setUserPoint(points - hadiahPoint)
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        MethodHelpers.showShortToast(ctx, jsonObj.getString("message"))
                    }
                }

            })
    }

    fun doUpdate(hadiah : Hadiah){
        val dialog = Dialog(ctx)
        dialog.setContentView(R.layout.pop_uploadhadiah)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT )
        val etNamaHadiah : EditText = dialog.findViewById(R.id.etNamaHadiah)
        val etPoint : EditText = dialog.findViewById(R.id.etPoint)
        val etBanyakItem : EditText = dialog.findViewById(R.id.etBanyakItem)
        val btnUpload : Button = dialog.findViewById(R.id.btnUploadH)
        val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
        etNamaHadiah.setText(hadiah.namahadiah)
        etPoint.setText(hadiah.point.toString())
        etBanyakItem.setText(hadiah.banyakitem.toString())
        btnUpload.text = "UPDATE"
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnUpload.setOnClickListener {
            val executedata = MethodHelpers.doRetrofitExecute()
            executedata.updateHadiah(
                hadiah.id!!,
                etNamaHadiah.text.toString(),
                etPoint.text.toString().toInt(),
                etBanyakItem.text.toString().toInt()
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

    fun doDelete(id : Int){
        val executedata = MethodHelpers.doRetrofitExecute()
        executedata.delHadiah(id).enqueue(object : Callback<DefaultResponse>{
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
}