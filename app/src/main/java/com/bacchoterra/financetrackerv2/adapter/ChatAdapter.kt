package com.bacchoterra.financetrackerv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.ChatMessage
import com.github.ybq.android.spinkit.SpinKitView
import java.lang.Exception

class ChatAdapter(val list: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_BOT = 0
        private const val TYPE_USER = 1
        private const val TYPE_LOADING = 2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            TYPE_BOT -> BotViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_chat_bot_message, parent, false)
            )
            TYPE_USER -> UserViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_chat_user_message, parent, false)
            )
            TYPE_LOADING -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_chat_typing, parent, false)
            )

            else -> throw Exception()
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chatMessage = list[position]

        if (holder is BotViewHolder) {
            bindBotViewHolder(holder, chatMessage)
        } else if (holder is UserViewHolder){
            bindUserViewHolder(holder, chatMessage)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        val chatMessage = list[position]

        return if (chatMessage.type == ChatMessage.TYPE_BOT) {
            TYPE_BOT
        } else if (chatMessage.type == ChatMessage.TYPE_USER) {
            TYPE_USER
        } else {
            TYPE_LOADING
        }

    }


    class BotViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtMessage = view.findViewById<TextView>(R.id.row_chat_bot_txtMessage)

    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtMessage = view.findViewById<TextView>(R.id.row_chat_user_txtMessage)

    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val progressBar = view.findViewById<SpinKitView>(R.id.row_chat_typing_progress)

    }

    private fun bindBotViewHolder(holder: BotViewHolder, chatMessage: ChatMessage) {
        holder.txtMessage.text = chatMessage.message
    }

    private fun bindUserViewHolder(holder: UserViewHolder, chatMessage: ChatMessage) {
        holder.txtMessage.text = chatMessage.message
    }

}