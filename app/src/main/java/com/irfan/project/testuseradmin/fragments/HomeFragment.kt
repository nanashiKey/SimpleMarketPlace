package com.irfan.project.testuseradmin.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
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
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import com.irfan.project.testuseradmin.models.Barang
import com.irfan.project.testuseradmin.models.DefaultResponse
import com.irfan.project.testuseradmin.viewmodels.HomeViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
//        layoutmanager = GridLayoutManager(requireActivity().applicationContext, 2)
        layoutmanager = LinearLayoutManager(requireContext())
        rcView.setHasFixedSize(true)
        rcView.layoutManager = layoutmanager
        barangAdapter = BarangAdapter()
        val modelizedata = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        modelizedata.dataExecute.observe(requireActivity(), Observer<ArrayList<Barang>>{
            barangAdapter = BarangAdapter(requireActivity(), it)
            rcView.adapter = barangAdapter
        })

        val id = PrefsHelper(requireActivity()).getUserID()
        if(id != 1){
            fab.visibility = View.GONE
        }

        fab.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.setContentView(R.layout.pop_uploadbarang)
            dialog.setCancelable(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT )
            val etNamaBarang : EditText = dialog.findViewById(R.id.etNamaBarang)
            val etHargaBarang : EditText = dialog.findViewById(R.id.etHargaBarang)
            val etStock : EditText = dialog.findViewById(R.id.etStock)
            val btnUpload : Button = dialog.findViewById(R.id.btnUploadB)
            val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnUpload.setOnClickListener {
                val executedata = MethodHelpers.doRetrofitExecute()
                executedata.uploadBarang(
                    etNamaBarang.text.toString(),
                    etHargaBarang.text.toString().toDouble(),
                    etStock.text.toString().toInt()
                ).enqueue(object : Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        t.printStackTrace()
                        e("TAGERROR", t.message!!)
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if(response.isSuccessful){
                            requireActivity().startActivity(requireActivity().intent)
                            requireActivity().finish()
                            requireActivity().overridePendingTransition(0,0)
                            MethodHelpers.showShortToast(requireActivity(), response.body()!!.message)
                            dialog.dismiss()
                        }else{
                            val jsonObj = JSONObject(response.errorBody()!!.string())
                            MethodHelpers.showShortToast(requireActivity(), jsonObj.getString("message"))
                            dialog.dismiss()
                        }
                    }

                })
            }

            dialog.show()
        }

    }

}