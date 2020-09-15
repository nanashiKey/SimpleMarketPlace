package com.irfan.project.testuseradmin.helpers

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.*


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class MethodHelpers {
    companion object{
        fun showShortToast(ctx : Context, msg : String ) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }
        fun <T : Any> goTo(ctx : Context, goto : Class<T>){
            ctx.startActivity(Intent(ctx, goto))
        }

        fun doRetrofitExecute() : APIServices{
            val retrofit = Retrofit.Builder()
                .baseUrl(Const.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiAccess = retrofit.create(APIServices::class.java)
            return apiAccess
        }

        fun changeCurrencytoIDR(nilai : Double) : String{
            val localeId = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
            return formatRupiah.format(nilai)
        }
        fun setNavBackground(saved: AppCompatActivity, color : Int){
            val windows = saved.window
            windows.navigationBarColor = saved.resources.getColor(color)
        }
        fun setWindowsBar(saved : AppCompatActivity, color: Int){
            val windows = saved.window
            windows.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            windows.setStatusBarColor(ContextCompat.getColor(saved, color))
        }
    }
}