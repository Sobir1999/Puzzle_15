package com.example.puzzle_15

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MyBase(context: Context) {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor


    init {
        sharedPreferences = context.getSharedPreferences("SHARED_PREF",MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun savelaststep(int: Int){
        editor.putInt("laststep",int).commit()
    }

    fun getlaststep():Int{
        return sharedPreferences.getInt("laststep",0)
    }

    fun savelasttime(int: Int){
        editor.putInt("lasttime",int).commit()
    }

    fun getlasttime():Int{
        return sharedPreferences.getInt("lasttime",0)
    }


    fun savebeststep(int: Int){
        editor.putInt("beststep",int).commit()
    }

    fun getbeststep():Int{
        return sharedPreferences.getInt("beststep",0)
    }

    fun savebesttime(int: Int){
        editor.putInt("besttime",int).commit()
    }

    fun getbesttime():Int{
        return sharedPreferences.getInt("besttime",0)
    }



    fun savebeststep2(int: Int){
        editor.putInt("beststep2",int).commit()
    }

    fun getbeststep2():Int{
        return sharedPreferences.getInt("beststep2",0)
    }

    fun savebesttime2(int: Int){
        editor.putInt("besttime2",int).commit()
    }

    fun getbesttime2():Int{
        return sharedPreferences.getInt("besttime2",0)
    }

    fun clear(){
        editor.clear().commit()
    }

}