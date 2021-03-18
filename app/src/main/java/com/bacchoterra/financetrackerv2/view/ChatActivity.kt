package com.bacchoterra.financetrackerv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.ChatAdapter
import com.bacchoterra.financetrackerv2.databinding.ActivityChatBinding
import com.bacchoterra.financetrackerv2.model.ChatMessage
import com.bacchoterra.financetrackerv2.utils.FakeChatBuilder
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil

class ChatActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder: ActivityChatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var container: ViewGroup

    //recyclerView components
    private lateinit var adapter: ChatAdapter
    private lateinit var list: ArrayList<ChatMessage>

    //fake chat builder
    private lateinit var fakeChatBuilder: FakeChatBuilder

    //Shared preferences manager
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initObjects()
        initViews()
        initRecyclerView()
        constructFakeChat()
    }

    private fun initObjects(){

        sharedPrefsUtil = SharedPrefsUtil(this)

    }

    private fun initViews() {

        recyclerView = binder.activityChatRecyclerView
        container = binder.activityChatContainer

    }

    private fun initRecyclerView() {

        val layoutManager = LinearLayoutManager(this)
        list = ArrayList()
        adapter = ChatAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)


    }

    private fun constructFakeChat() {
        fakeChatBuilder = FakeChatBuilder(adapter, list, this, recyclerView)

        fakeChatBuilder.firstListPopulation()
        handleLayoutRequests()

    }

    private fun showAndHandleNameInput() {
        val view = layoutInflater.inflate(R.layout.activity_chat_insert_name, container)

        val btnSend = view.findViewById<Button>(R.id.activity_chat_insert_name_btnSend)
        val editName = view.findViewById<EditText>(R.id.activity_chat_insert_name_editName)


        btnSend.setOnClickListener {

            val name = editName.text.toString().trim()

            if (name.length < 3) {
                fakeChatBuilder.invalidNameInput(name)


            } else {
                fakeChatBuilder.insertUserName(editName.text.toString())
                container.removeAllViews()
                saveUserName(name)
            }
        }

    }

    private fun saveUserName (name:String){

        sharedPrefsUtil.saveUserName(name)

    }

    private fun saveUserPassword(password:String){

        sharedPrefsUtil.saveUserPassword(password)

    }

    private fun enableUserToChoosePassword() {


        val view = layoutInflater.inflate(R.layout.activity_chat_password_choice, container)

        val btnYes = view.findViewById<Button>(R.id.activity_chat_password_choice_btnYes)
        val btnNo = view.findViewById<Button>(R.id.activity_chat_password_choice_btnNo)

        btnYes.setOnClickListener {
            fakeChatBuilder.enableUserToAddPassword()
            container.removeAllViews()

        }

        btnNo.setOnClickListener {

            fakeChatBuilder.dontWantPassword()
            container.removeAllViews()
        }


    }

    private fun showAndHandlePasswordInput(){
        val view = layoutInflater.inflate(
            R.layout.activity_chat_insert_password,
            container
        )

        val editPassword =
            view.findViewById<EditText>(R.id.activity_chat_insert_password_editpassword)
        val btnSend =
            view.findViewById<Button>(R.id.activity_chat_insert_password_btnSend)

        btnSend.setOnClickListener {

            val password = editPassword.text.toString()

            if (password.length < 3) {
                editPassword.error = getString(R.string.min_3_chars)
            } else {
                container.removeAllViews()

                fakeChatBuilder.passwordAdded(password)
                saveUserPassword(password)

            }


        }

    }

    private fun showFinishedLayout () {
        val view = layoutInflater.inflate(R.layout.activity_chat_done, container)

        val btn = view.findViewById<Button>(R.id.activity_chat_done_btnDone)

        btn.setOnClickListener {

        }
    }

    private fun handleLayoutRequests() {


        fakeChatBuilder.layoutRequestListener = {


            when (it) {

                FakeChatBuilder.ENABLE_NAME_INPUT -> showAndHandleNameInput()
                FakeChatBuilder.NAME_INSERTED ->  fakeChatBuilder.askForPassword()
                FakeChatBuilder.ENABLE_PASSWORD_CHOICE -> enableUserToChoosePassword()
                FakeChatBuilder.ENABLE_PASSWORD_INPUT -> showAndHandlePasswordInput()
                FakeChatBuilder.FINISHED -> showFinishedLayout()


            }

        }


    }

}