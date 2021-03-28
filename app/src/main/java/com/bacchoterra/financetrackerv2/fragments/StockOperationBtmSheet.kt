package com.bacchoterra.financetrackerv2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bacchoterra.financetrackerv2.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StockOperationBtmSheet : BottomSheetDialogFragment() {

    companion object {

        const val KEY_ARGUMENTS = "ARGUMENTS"
        const val DELETE_OPERATION = 0

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflateChosenLayout(inflater,container)
    }

    private fun inflateChosenLayout(inflater: LayoutInflater, container: ViewGroup?): View? {

        return when (arguments?.get(KEY_ARGUMENTS)) {


            DELETE_OPERATION -> return inflater.inflate(
                R.layout.activity_chat_password_choice,
                container,
                false
            )

            else -> null
        }

    }


}