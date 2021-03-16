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
    private val list: ArrayList<ChatMessage>, private val activity: Activity, private val recyclerView: RecyclerView
) {

    //Handler to create a time countdown
    private lateinit var handler: Handler

    //Lambda callback
    var enableNameListener: (() -> Unit)? = null
    var nameInsertedListener: (() -> Unit)? = null
    var enablePasswordChoiceListener: (() -> Unit)? = null
    var enablePasswordListener: (() -> Unit)? = null
    var finishedListener : (() -> Unit)? = null

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
            notifyDataSetAndScroll()
            enableNameListener?.invoke()

        }, getRandomWaitingTime())


    }

    fun insertUserName(name: String) {

        list.add(createUserChatMessage(name))
        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()

        handler.postDelayed({
            removeLastListItem()
            list.add(
                createBotChatMessage(
                    activity.getString(R.string.certo) + " $name ," + activity.getString(
                        R.string.you_are_close_to_use_our_app
                    )
                )
            )
            notifyDataSetAndScroll()
            nameInsertedListener?.invoke()

        }, getRandomWaitingTime())


    }

    fun invalidNameInput(name: String) {

        list.add(createUserChatMessage(name))
        adapter.notifyDataSetChanged()

        if (name.isBlank()) {

            list.add(createLoadingChatMessage())
            notifyDataSetAndScroll()

            handler.postDelayed({
                removeLastListItem()
                list.add(createBotChatMessage(activity.getString(R.string.invalid_blanck_name)))
                notifyDataSetAndScroll()

            }, getRandomWaitingTime())
        } else {
            list.add(createLoadingChatMessage())
            notifyDataSetAndScroll()

            handler.postDelayed({
                removeLastListItem()
                list.add(createBotChatMessage(activity.getString(R.string.invalid_short_name)))
                notifyDataSetAndScroll()
            }, getRandomWaitingTime())

            notifyDataSetAndScroll()
        }

    }

    fun askForPassword() {

        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()


        handler.postDelayed({

            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.want_to_add_password)))
            notifyDataSetAndScroll()
            enablePasswordChoiceListener?.invoke()

        }, getRandomWaitingTime())


    }

    fun enableUserToAddPassword() {
        list.add(createUserChatMessage(activity.getString(R.string.i_want_to_add_password)))
        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()
        handler.postDelayed({
            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.Insert_password)))
            notifyDataSetAndScroll()
            enablePasswordListener?.invoke()
        },getRandomWaitingTime())

    }

    fun passwordAdded(password: String) {

        list.add(createUserChatMessage("●".repeat(password.length)))
        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()

        handler.postDelayed({
            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.password_saved)))
            notifyDataSetAndScroll()

            notifyUserOfSecurity()
        },getRandomWaitingTime())

    }

    fun dontWantPassword() {
        list.add(createUserChatMessage(activity.getString(R.string.nao)))
        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()

        handler.postDelayed({

            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.cert_start_using_app_now)))
            notifyDataSetAndScroll()
            notifyUserOfSecurity()

        }, getRandomWaitingTime())

    }

    private fun notifyUserOfSecurity() {

        list.add(createLoadingChatMessage())
        notifyDataSetAndScroll()

        handler.postDelayed({

            removeLastListItem()
            list.add(createBotChatMessage(activity.getString(R.string.chat_security_message_to_ease_user)))
            notifyDataSetAndScroll()
            finishedListener?.invoke()

        }, getRandomWaitingTime())


    }

    private fun removeLastListItem() = list.removeAt(list.lastIndex)

    private fun createLoadingChatMessage(): ChatMessage = ChatMessage("", ChatMessage.TYPE_LOADING)

    private fun getRandomWaitingTime(): Long = Random.nextLong(1200, 1800)

    private fun createBotChatMessage(message: String): ChatMessage =
        ChatMessage(message, ChatMessage.TYPE_BOT)

    private fun createUserChatMessage(message: String): ChatMessage =
        ChatMessage(message, ChatMessage.TYPE_USER)

    private fun notifyDataSetAndScroll(){
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(adapter.itemCount -1)

    }
}