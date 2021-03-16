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

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: colorcar a tela em full screen
        super.onCreate(savedInstanceState)
        binder = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initViews()
        initRecyclerView()
        constructFakeChat()
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

        fakeChatBuilder = FakeChatBuilder(adapter, list, this)

    }

    private fun constructFakeChat() {
        fakeChatBuilder.firstListPopulation()
        fakeChatBuilder.enableNameListener = {
            enableUserToInsertName()
        }

        enableUserToChoosePassword()

        fakeChatBuilder.finishedListener = {

            val view = layoutInflater.inflate(R.layout.activity_chat_done, container)

        }

    }

    private fun enableUserToInsertName() {
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
            }
        }

    }

    private fun enableUserToChoosePassword() {

        fakeChatBuilder.nameInsertedListener = {

            fakeChatBuilder.askForPassword()

            fakeChatBuilder.enablePasswordChoiceListener = {

                val view = layoutInflater.inflate(R.layout.activity_chat_password_choice, container)

                val btnYes = view.findViewById<Button>(R.id.activity_chat_password_choice_btnYes)
                val btnNo = view.findViewById<Button>(R.id.activity_chat_password_choice_btnNo)

                btnYes.setOnClickListener {
                    fakeChatBuilder.enableUserToAddPassword()
                    container.removeAllViews()

                    fakeChatBuilder.enablePasswordListener = {

                        val view2 = layoutInflater.inflate(
                            R.layout.activity_chat_insert_password,
                            container
                        )

                        val editPassword =
                            view2.findViewById<EditText>(R.id.activity_chat_insert_password_editpassword)
                        val btnSend =
                            view2.findViewById<Button>(R.id.activity_chat_insert_password_btnSend)

                        btnSend.setOnClickListener {

                            val passWord = editPassword.text.toString()

                            if (passWord.length < 3) {
                                editPassword.error = getString(R.string.min_3_chars)
                            } else {
                                container.removeAllViews()

                                fakeChatBuilder.passwordAdded(passWord)

                            }

                        }

                    }

                }

                btnNo.setOnClickListener {

                    fakeChatBuilder.dontWantPassword()
                    container.removeAllViews()
                }

            }

        }


    }

}