package com.irfan.project.testuseradmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.adapters.HadiahAdapter
import com.irfan.project.testuseradmin.models.Hadiah
import com.irfan.project.testuseradmin.viewmodels.HadiahViewModel


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HadiahFragment : Fragment() {
    lateinit var rcView : RecyclerView
    lateinit var fab : FloatingActionButton
    lateinit var hadiahAdapter: HadiahAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_hadiah, container, false)
        rcView = v.findViewById(R.id.rcView)
        fab = v.findViewById(R.id.floatingActionButton)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        rcView.setHasFixedSize(true)
        rcView.layoutManager = layoutManager
        hadiahAdapter = HadiahAdapter()
        val modelize = ViewModelProviders.of(requireActivity())
            .get(HadiahViewModel::class.java)

        modelize.dataExecute.observe(requireActivity(), Observer<ArrayList<Hadiah>>{
            hadiahAdapter = HadiahAdapter(requireActivity(), it)
            rcView.adapter = hadiahAdapter
        } )

    }
}