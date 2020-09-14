package com.irfan.project.testuseradmin.helpers

import com.irfan.project.testuseradmin.models.DefaultResponse
import com.irfan.project.testuseradmin.models.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
interface APIServices {
    /**
     * userLogin
     * @param username
     * @param password
     */
    @FormUrlEncoded
    @POST("masuk")
    fun userLogin( @Field("username") username : String,
                   @Field("password") password : String) : Call<UserResponse>

    /**
     * registerUser
     * @param username
     * @param email
     * @param password
     * @param point = default 0
     */
    @FormUrlEncoded
    @POST("register")
    fun registerUser(username : String, email : String, password : String, point : Int) : Call<DefaultResponse>

    /**
     * delUser
     * @param id
     */
    @FormUrlEncoded
    @POST("deluser")
    fun delUser(id : Int) : Call<DefaultResponse>

    /**
     * getAllUser
     */
    @GET("getalluser")
    fun getAllUser() : Call<UserResponse>
}