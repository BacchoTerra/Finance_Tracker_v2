package com.bacchoterra.financetrackerv2.utils

import android.app.Activity
import android.os.Handler
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.ChatAdapter
import com.bacchoterra.financetrackerv2.model.ChatMessage
import kotlin.random.Random

class FakeChatBuilder(
    private val adapter: ChatAdapter,
    private val list: ArrayList<ChatMessage>, private val activity: Activity
) {

    //Handler to create a time countdown
    private lateinit var handler: Handler

    //Lambda callback
    var enableNameListener: (() -> Unit)? = null
    var enablePasswordListener: (() -> Unit)? = null

    init {
        initComponents()
    }

    private fun initComponents() {

        handler = Handler()

    }

    fun firstListPopulation() {
        list.add(createBotChatMessage(activity.getString(R.string.chat_welcome_message)))
        list.add(createLoadingChatMessage())
        handler.postDelayed({
            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.chat_request_name_message)))
            adapter.notifyDataSetChanged()
            enableNameListener?.invoke()

        }, getRandomWaitingTime())


    }

    fun insertUserName(name: String) {

        list.add(createUserChatMessage(name))
        list.add(createLoadingChatMessage())
        adapter.notifyDataSetChanged()

        handler.postDelayed({
            list.removeAt(list.lastIndex)
            list.add(
                createBotChatMessage(
                    activity.getString(R.string.certo) + " $name ," + activity.getString(
                        R.string.you_are_close_to_use_our_app
                    )
                )
            )
            adapter.notifyDataSetChanged()

            askForPassword()

        }, getRandomWaitingTime())


    }

    private fun askForPassword() {

        list.add(createLoadingChatMessage())
        adapter.notifyDataSetChanged()


        handler.postDelayed({

            list.removeAt(list.lastIndex)
            list.add(createBotChatMessage(activity.getString(R.string.want_to_add_password)))
            adapter.notifyDataSetChanged()
            enablePasswordListener?.invoke()

        }, getRandomWaitingTime())


    }

    fun invalidNameInput(name: String) {

        list.add(createUserChatMessage(name))
        adapter.notifyDataSetChanged()

        if (name.isBlank()) {

            list.add(createLoadingChatMessage())
            adapter.notifyDataSetChanged()

            handler.postDelayed({
                list.removeAt(list.lastIndex)
                list.add(createBotChatMessage(activity.getString(R.string.invalid_blanck_name)))
                adapter.notifyDataSetChanged()

            }, getRandomWaitingTime())
        } else {
            list.add(createLoadingChatMessage())
            adapter.notifyDataSetChanged()

            handler.postDelayed({
                list.removeAt(list.lastIndex)
                list.add(createBotChatMessage(activity.getString(R.string.invalid_short_name)))
                adapter.notifyDataSetChanged()
            }, getRandomWaitingTime())

            adapter.notifyDataSetChanged()
        }

    }

    private fun removeLastListItem() = list.removeAt(list.lastIndex)

    private fun createLoadingChatMessage(): ChatMessage = ChatMessage("", ChatMessage.TYPE_LOADING)

    private fun getRandomWaitingTime(): Long = Random.nextLong(1200, 1800)

    private fun createBotChatMessage(message: String): ChatMessage =
        ChatMessage(message, ChatMessage.TYPE_BOT)

    private fun createUserChatMessage(message: String): ChatMessage =
        ChatMessage(message, ChatMessage.TYPE_USER)
}