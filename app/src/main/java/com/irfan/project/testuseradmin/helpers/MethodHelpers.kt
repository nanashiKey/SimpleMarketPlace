package com.irfan.project.testuseradmin.helpers

import android.content.Context
import android.content.Intent
import android.widget.Toast


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
    }
}