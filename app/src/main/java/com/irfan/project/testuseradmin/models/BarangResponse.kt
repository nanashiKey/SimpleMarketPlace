package com.irfan.project.testuseradmin.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class BarangResponse : Serializable {
    @SerializedName("status")
    var status : String ?= null

    @SerializedName("message")
    var message : String ?= null

    @SerializedName("data")
    var data : ArrayList<Barang> ?= null
}