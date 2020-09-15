package com.irfan.project.testuseradmin.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class RegisterAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val passCheck = etPasswordCheck.text.toString()
            doRegister(username, email, password, passCheck)
        }
    }

    fun doRegister(u : String, e : String, p1 : String, p2 : String){
        if(u.isNotEmpty() || e.isNotEmpty() || p1.isNotEmpty() || p2.isNotEmpty()){
            if(!p1.equals(p2)){
                MethodHelpers.showShortToast(this@RegisterAcitivity, "password tidak cocok")
            }else{
                val executedata = MethodHelpers.doRetrofitExecute()
                executedata.registerUser(u, e, p1, 0)
                    .enqueue(object : Callback<DefaultResponse>{
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("TAGERROR", t.message!!)
                        }

                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {
                           if(response.isSuccessful){
                               MethodHelpers.showShortToast(this@RegisterAcitivity, response.body()!!.message)
                               finish()
                           }else{
                               val objJson = JSONObject(response.errorBody()!!.string())
                               MethodHelpers.showShortToast(this@RegisterAcitivity, objJson.getString("message"))
                           }
                        }

                    })
            }
        }else{
            MethodHelpers.showShortToast(this@RegisterAcitivity, "please fill all empty form")
        }
    }
}