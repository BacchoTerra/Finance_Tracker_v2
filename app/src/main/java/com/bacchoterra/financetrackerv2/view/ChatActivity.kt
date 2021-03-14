package com.bacchoterra.financetrackerv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.ChatAdapter
import com.bacchoterra.financetrackerv2.databinding.ActivityChatBinding
import com.bacchoterra.financetrackerv2.model.ChatMessage
import kotlin.random.Random

class ChatActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder:ActivityChatBinding
    private lateinit var recyclerView: RecyclerView

    //recyclerView components
    private lateinit var adapter:ChatAdapter
    private lateinit var list:ArrayList<ChatMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initViews()
        populateLIst()
        initRecyclerView()
    }

    private fun initViews(){

        recyclerView = binder.activityChatRecyclerView

    }

    private fun initRecyclerView (){

        val layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

    }

    private fun populateLIst(){

        list = ArrayList()

        (1..20).forEach {

            val chatMessage = ChatMessage(it.toString(),Random.nextInt(0,3))
            list.add(chatMessage)

        }

    }

}