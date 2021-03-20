package com.bacchoterra.financetrackerv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.FragmentProfileBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil


class ProfileFragment : Fragment() {

    //Layout components
    private lateinit var binder: FragmentProfileBinding

    //SharedPrefs manager
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentProfileBinding.inflate(inflater)
        initSharedPrefs()
        retrieveUserName()


        binder.fragmentProfileBtnSave.setOnClickListener {

            val name = binder.fragmentProfileEditName.text.toString()

            if (name.length < 3) {
                binder.fragmentProfileInputLayoutName.error = getString(R.string.invalid_short_name)
            } else {
                sharedPrefsUtil.saveUserName(name)
                Toast.makeText(requireContext(),"nome salvo",Toast.LENGTH_SHORT).show()
            }
        }

        return binder.root
    }


    private fun initSharedPrefs() {

        sharedPrefsUtil = SharedPrefsUtil(requireActivity())


    }

    private fun retrieveUserName() {
        binder.fragmentProfileEditName.setText(sharedPrefsUtil.getUserName())

    }


}