package com.irfan.project.testuseradmin.activities

import android.os.Bundle
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.helpers.APIServices
import com.irfan.project.testuseradmin.helpers.Const
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.models.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val pass = etPassword.text.toString()
            if(username.isNotEmpty() && pass.isNotEmpty()){
                doLogin(username, pass)
            }else{
                MethodHelpers.showShortToast(this@LoginActivity, "username atau password tidak boleh kosong")
            }
        }
        btnRegister.setOnClickListener {
            MethodHelpers.goTo(this@LoginActivity, RegisterAcitivity::class.java)
        }
    }
    fun doLogin(u : String, p : String){
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiAccess = retrofit.create(APIServices::class.java)
        apiAccess.userLogin(u, p)
            .enqueue(object : Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.printStackTrace()
                e("TAGERROR", t.message!!)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val bodyres = response.body()
                    if(bodyres != null){
                        MethodHelpers.showShortToast(this@LoginActivity, bodyres.message!!)
                        MethodHelpers.goTo(this@LoginActivity, MainActivity::class.java)
                    }else{
                        MethodHelpers.showShortToast(this@LoginActivity, response.message())
                    }
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.string())
                    MethodHelpers.showShortToast(this@LoginActivity, jsonObj.getString("message"))
                    e("TAGERROR", response.errorBody()!!.string())
                }
            }

        })
    }
}