package com.irfan.project.testuseradmin.viewmodels

import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.models.Hadiah
import com.irfan.project.testuseradmin.models.HadiahResponse
import com.irfan.project.testuseradmin.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HadiahViewModel : ViewModel() {
    var listHadiah : MutableLiveData<ArrayList<Hadiah>> ?= null
    val dataExecute : LiveData<ArrayList<Hadiah>>
        get() {
            if(listHadiah == null){
                listHadiah = MutableLiveData()
                getAllHadiah()
            }
            return listHadiah!!
        }

    private fun getAllHadiah(){
        MethodHelpers.doRetrofitExecute().getAllHadiah()
            .enqueue(object : Callback<HadiahResponse>{
                override fun onFailure(call: Call<HadiahResponse>, t: Throwable) {
                    e("TAGERROR", t.message!!)
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<HadiahResponse>,
                    response: Response<HadiahResponse>
                ) {
                   if(response.isSuccessful){
                       val resBody = response.body()
                       if(resBody == null){
                           e("TAGERROR", response.message())
                       }else{
                           listHadiah!!.value = resBody.data
                       }
                   }else{
                       e("TAGERROR", response.errorBody()!!.string())
                   }
                }

            })
    }
}