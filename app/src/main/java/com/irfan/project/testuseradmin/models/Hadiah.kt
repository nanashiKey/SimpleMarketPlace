package com.irfan.project.testuseradmin.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class Hadiah : Serializable {
    @SerializedName("id")
    var id : Int ?= null

    @SerializedName("namahadiah")
    var namahadiah : String ?= null

    @SerializedName("point")
    var point : Int ?= null

    @SerializedName("banyakitem")
    var banyakitem : Int ?= null
}