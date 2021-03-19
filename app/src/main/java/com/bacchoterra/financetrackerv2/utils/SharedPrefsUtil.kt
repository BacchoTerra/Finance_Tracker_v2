package com.bacchoterra.financetrackerv2.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.bacchoterra.financetrackerv2.R
import org.jetbrains.annotations.Nullable

class SharedPrefsUtil (private val activity: Activity){

    private var sharedPreferences:SharedPreferences =
        activity.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE)


    private var editor:SharedPreferences.Editor = sharedPreferences.edit()


    fun saveUserName(name:String){
        editor.putString(getString(R.string.saved_user_name,),name).apply()
    }

    fun saveUserPassword(passWord:String){

        editor.putString(getString(R.string.saved_user_password),passWord).apply()

    }

    fun getUserName () :String?{

        return sharedPreferences.getString(getString(R.string.saved_user_name),null)

    }

    @Nullable
    fun getPassword():String?{
        return sharedPreferences.getString(getString(R.string.saved_user_password),null)
    }

    private fun getString(key:Int):String{

        return activity.getString(key)

    }

    fun nukeSharedPrefs(){

        editor.clear().apply()

    }

}