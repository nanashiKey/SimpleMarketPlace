package com.irfan.project.testuseradmin.models

import java.io.Serializable


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class Barang(var id : Int,
             var namabarang : String,
             var hargabarang : Double,
             var stock : Int) : Serializable