package com.irfan.project.testuseradmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irfan.project.testuseradmin.R


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HadiahFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_hadiah, container, false)
        return v
    }
}