package com.irfan.project.testuseradmin.helpers

import android.content.Context
import android.content.SharedPreferences
import com.irfan.project.testuseradmin.models.User


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class PrefsHelper(ctx : Context) {
    private val appsName = "TestUserAdmin"
    private val STATLOGIN = "LoginStat"
    private val USERNAME = "UserName"
    private val USERID = "userId"
    private val USEREMAIL = "userEmail"
    private val USERPOINT = "userPoint"


    private val prefsHelp : SharedPreferences
    init {
        prefsHelp = ctx.getSharedPreferences(appsName, Context.MODE_PRIVATE)
    }

    private val editMode = prefsHelp.edit()

    fun setLoginStat(status : Boolean){
        editMode.putBoolean(STATLOGIN, status)
        editMode.apply()
    }

    fun getLoginStat() : Boolean{
        return prefsHelp.getBoolean(STATLOGIN, false)
    }

    fun saveUserDetail(user : User){
        editMode.putInt(USERID, user.id)
        editMode.putString(USERNAME, user.username)
        editMode.putString(USEREMAIL, user.email)
        editMode.putInt(USERPOINT, user.point)
        editMode.apply()
    }

    fun getUserID() : Int{
        return prefsHelp.getInt(USERID, 0)
    }

    fun getUserName() : String?{
        return prefsHelp.getString(USERNAME, "")
    }

    fun getUserEmail() : String?{
        return  prefsHelp.getString(USEREMAIL, "")
    }

    fun setUserPoint(point : Int){
        editMode.putInt(USERPOINT, point)
        editMode.apply()
    }

    fun getUserPoint() : Int{
        return prefsHelp.getInt(USERPOINT, 0)
    }


}