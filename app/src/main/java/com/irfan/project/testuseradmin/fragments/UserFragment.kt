package com.irfan.project.testuseradmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.activities.LoginActivity
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import kotlinx.android.synthetic.main.fragment_users.*


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_users, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUsername.text = "Username : " +PrefsHelper(requireActivity()).getUserName()
        tvEmail.text = "Email : " +PrefsHelper(requireActivity()).getUserEmail()
        tvPoint.text = "Point : "+PrefsHelper(requireActivity()).getUserPoint().toString()
        btnLogout.setOnClickListener {
            MethodHelpers.goTo(requireActivity(), LoginActivity::class.java)
            PrefsHelper(requireActivity()).setLoginStat(false)
            requireActivity().finish()
        }
    }
}