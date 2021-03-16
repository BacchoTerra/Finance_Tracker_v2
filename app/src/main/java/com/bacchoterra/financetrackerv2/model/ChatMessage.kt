package com.bacchoterra.financetrackerv2.model

data class ChatMessage (val message:String,val type:Int){

    companion object{
        const val TYPE_BOT = 0
        const val TYPE_USER = 1
        const val TYPE_LOADING = 2
    }


}