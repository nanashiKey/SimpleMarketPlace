package com.irfan.project.testuseradmin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.adapters.BarangAdapter
import com.irfan.project.testuseradmin.models.Barang
import com.irfan.project.testuseradmin.viewmodels.HomeViewModel


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HomeFragment : Fragment() {
    lateinit var rcView : RecyclerView
    lateinit var layoutmanager: LinearLayoutManager
    lateinit var barangAdapter : BarangAdapter
    lateinit var fab : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        rcView = v.findViewById(R.id.rcView)
        fab = v.findViewById(R.id.floatingActionButton)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutmanager = GridLayoutManager(requireActivity().applicationContext, 2)
        rcView.setHasFixedSize(true)
        rcView.layoutManager = layoutmanager
        barangAdapter = BarangAdapter()
        val modelizedata = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        modelizedata.dataExecute.observe(requireActivity(), Observer<ArrayList<Barang>>{
            barangAdapter = BarangAdapter(requireActivity().applicationContext, it)
            rcView.adapter = barangAdapter
        })



    }

}