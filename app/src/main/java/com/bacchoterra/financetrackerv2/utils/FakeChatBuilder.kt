package com.bacchoterra.financetrackerv2.utils

import android.app.Activity
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.ChatAdapter
import com.bacchoterra.financetrackerv2.model.ChatMessage
import kotlin.random.Random

class FakeChatBuilder(
    private val adapter: ChatAdapter,
    private val list: ArrayList<ChatMessage>,
    private val activity: Activity,
    private val recyclerView: RecyclerView
) {

    companion object {

        const val ENABLE_NAME_INPUT = 0
        const val NAME_INSERTED = 1
        const val ENABLE_PASSWORD_CHOICE = 2
        const val ENABLE_PASSWORD_INPUT = 3
        const val FINISHED = 4

    }

    //Handler to create a time countdown
    private lateinit var handler: Handler

    //Lambda callback

    private var handlerListener: (() -> Unit)? = null
    var layoutRequestListener: ((action: Int) -> Unit)? = null

    init {
        initComponents()
    }

    private fun initComponents() {

        handler = Handler()

    }

    fun firstListPopulation() {
        createBotChatMessage(activity.getString(R.string.chat_welcome_message))
        createLoadingChatMessage()
        waitForHandler()

        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.chat_request_name_message))
            notifyDataSetAndScroll()
            layoutRequestListener?.invoke(ENABLE_NAME_INPUT)
        }


    }

    fun insertUserName(name: String) {

        createUserChatMessage(name)
        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()
        handlerListener = {
            removeLastListItem()

            createBotChatMessage(
                activity.getString(R.string.certo) + " $name ," + activity.getString(
                    R.string.you_are_close_to_use_our_app
                )

            )

            layoutRequestListener?.invoke(NAME_INSERTED)

        }


    }

    fun invalidNameInput(name: String) {

        createUserChatMessage(name)
        adapter.notifyDataSetChanged()

        if (name.isBlank()) {

            createLoadingChatMessage()
            notifyDataSetAndScroll()

            waitForHandler()
            handlerListener = {
                removeLastListItem()
                createBotChatMessage(activity.getString(R.string.invalid_blanck_name))
                notifyDataSetAndScroll()
            }

        } else {
            createLoadingChatMessage()
            notifyDataSetAndScroll()

            waitForHandler()
            handlerListener = {
                removeLastListItem()
                createBotChatMessage(activity.getString(R.string.invalid_short_name))
                notifyDataSetAndScroll()
            }
            notifyDataSetAndScroll()
        }

    }

    fun askForPassword() {

        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()
        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.want_to_add_password))
            notifyDataSetAndScroll()
            layoutRequestListener?.invoke(ENABLE_PASSWORD_CHOICE)
        }


    }

    fun enableUserToAddPassword() {
        createUserChatMessage(activity.getString(R.string.i_want_to_add_password))
        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()
        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.Insert_password))
            notifyDataSetAndScroll()
            layoutRequestListener?.invoke(ENABLE_PASSWORD_INPUT)
        }

    }

    fun passwordAdded(password: String) {

        createUserChatMessage("●".repeat(password.length))
        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()
        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.password_saved))
            notifyDataSetAndScroll()

            notifyUserOfSecurity()
        }

    }

    fun dontWantPassword() {
        createUserChatMessage(activity.getString(R.string.no))
        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()

        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.cert_start_using_app_now))
            notifyDataSetAndScroll()
            notifyUserOfSecurity()
        }

    }

    private fun notifyUserOfSecurity() {

        createLoadingChatMessage()
        notifyDataSetAndScroll()

        waitForHandler()

        handlerListener = {
            removeLastListItem()
            createBotChatMessage(activity.getString(R.string.chat_security_message_to_ease_user))
            notifyDataSetAndScroll()
            layoutRequestListener?.invoke(FINISHED)
        }


    }

    private fun removeLastListItem() {
        list.removeAt(list.lastIndex)
    }

    private fun createLoadingChatMessage() {
        list.add(ChatMessage("", ChatMessage.TYPE_LOADING))

    }

    private fun getRandomWaitingTime(): Long = Random.nextLong(1200, 1800)

    private fun createBotChatMessage(message: String) {
        list.add(ChatMessage(message, ChatMessage.TYPE_BOT))
    }

    private fun createUserChatMessage(message: String) {
        list.add(ChatMessage(message, ChatMessage.TYPE_USER))
    }

    private fun notifyDataSetAndScroll() {
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(adapter.itemCount - 1)

    }

    private fun waitForHandler() {

        handler.postDelayed({

            handlerListener?.invoke()

        }, getRandomWaitingTime())

    }
}