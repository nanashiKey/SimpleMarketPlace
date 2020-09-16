package com.irfan.project.testuseradmin.helpers

import com.irfan.project.testuseradmin.models.BarangResponse
import com.irfan.project.testuseradmin.models.DefaultResponse
import com.irfan.project.testuseradmin.models.HadiahResponse
import com.irfan.project.testuseradmin.models.UserResponse
import retrofit2.Call
import retrofit2.http.*


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
    fun registerUser(
        @Field("username") username : String,
        @Field("email")email : String,
        @Field("password") password : String,
        @Field("point") point : Int) : Call<DefaultResponse>

    /**
     * delUser
     * @param id
     */
    @FormUrlEncoded
    @POST("deluser")
    fun delUser(@Field("id") id : Int) : Call<DefaultResponse>

    /**
     * getAllUser
     */
    @GET("getalluser")
    fun getAllUser() : Call<UserResponse>

    /**
     * getUserbyID
     * @path id
     */
    @GET("user/{id}")
    fun getUserByID(@Path("id") id : Int) : Call<UserResponse>

    /**
     * getallbarang
     */
    @GET("getallbarang")
    fun getAllBarang() : Call<BarangResponse>

    /**
     * uploadbarang
     * @param namabarang
     * @param hargabarang
     * @param stock
     */
    @FormUrlEncoded
    @POST("uploadbarang")
    fun uploadBarang(
        @Field("namabarang") namaBarang : String,
        @Field("hargabarang") harga : Double,
        @Field("stock") stock : Int
    ) : Call<DefaultResponse>

    /**
     * deleteBarang
     * @param id
     */
    @FormUrlEncoded
    @POST("deletebarang")
    fun deleteBarang(@Field("id") id : Int) : Call<DefaultResponse>

    /**
     * beliBarang
     * @param userid
     * @param barangid
     */
    @FormUrlEncoded
    @POST("beli")
    fun beliBarang(@Field("userid") usrid : Int,
                   @Field("barangid") barangid : Int) : Call<DefaultResponse>

    /**
     * updateBarang
     * @path id
     * @param namabarang
     * @param hargabarang
     * @param stock
     */
    @FormUrlEncoded
    @POST("barang/{id}")
    fun updateBarang(
        @Path("id") id : Int,
        @Field("namabarang") namaBarang : String,
        @Field("hargabarang") harga : Double,
        @Field("stock") stock : Int
    ) : Call<DefaultResponse>

    /**
     * getAllHadiah
     */
    @GET("getallhadiah")
    fun getAllHadiah() : Call<HadiahResponse>

    /**
     * uploadHadiah
     * @param namahadiah
     * @param point
     * @param banyakitem
     */
    @FormUrlEncoded
    @POST("uploadhadiah")
    fun uploadHadiah(
        @Field("namahadiah") namaHadiah : String,
        @Field("point") point : Int,
        @Field("banyakitem") banyakItem : Int
    ) : Call<DefaultResponse>

    /**
     * redeemedHadiah
     * @param hadiahid
     * @param userid
     */
    @FormUrlEncoded
    @POST("redeemed")
    fun redeemedHadiah(
        @Field("hadiahid") hadiahId : Int,
        @Field("userid") userid : Int
    ) : Call<DefaultResponse>


}