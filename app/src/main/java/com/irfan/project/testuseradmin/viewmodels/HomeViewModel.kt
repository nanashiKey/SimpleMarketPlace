package com.irfan.project.testuseradmin.viewmodels

import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.models.Barang
import com.irfan.project.testuseradmin.models.BarangResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HomeViewModel : ViewModel() {

    var listBarang : MutableLiveData<ArrayList<Barang>>? = null
    val dataExecute : LiveData<ArrayList<Barang>>
        get() {
            if(listBarang == null){
                listBarang = MutableLiveData()
                doExecuteData()
            }
            return listBarang!!
        }

    private fun doExecuteData(){
        MethodHelpers.doRetrofitExecute().getAllBarang()
            .enqueue(object : Callback<BarangResponse>{
                override fun onFailure(call: Call<BarangResponse>, t: Throwable) {
                    e("TAGERROR", t.localizedMessage!!)
                }

                override fun onResponse(
                    call: Call<BarangResponse>,
                    response: Response<BarangResponse>
                ) {
                    if (response.isSuccessful){
                        val resBody = response.body()
                        if(resBody == null){
                            e("TAGERROR", response.message())
                        }else{
                            listBarang!!.value = resBody.data!!
                        }
                    }else{
                        e("TAGERROR", response.errorBody()!!.string())
                    }
                }

            } )
    }
}