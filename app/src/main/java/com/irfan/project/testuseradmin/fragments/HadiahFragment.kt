package com.irfan.project.testuseradmin.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.adapters.HadiahAdapter
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import com.irfan.project.testuseradmin.models.DefaultResponse
import com.irfan.project.testuseradmin.models.Hadiah
import com.irfan.project.testuseradmin.viewmodels.HadiahViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        val id = PrefsHelper(requireActivity()).getUserID()
        if(id != 1){
            fab.visibility = View.GONE
        }

        fab.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.setContentView(R.layout.pop_uploadhadiah)
            dialog.setCancelable(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT )
            val etNamaHadiah : EditText = dialog.findViewById(R.id.etNamaHadiah)
            val etPoint : EditText = dialog.findViewById(R.id.etPoint)
            val etBanyakItem : EditText = dialog.findViewById(R.id.etBanyakItem)
            val btnUpload : Button = dialog.findViewById(R.id.btnUploadH)
            val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnUpload.setOnClickListener {
                val executedata = MethodHelpers.doRetrofitExecute()
                executedata.uploadHadiah(
                    etNamaHadiah.text.toString(),
                    etPoint.text.toString().toInt(),
                    etBanyakItem.text.toString().toInt()
                ).enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        t.printStackTrace()
                        Log.e("TAGERROR", t.message!!)
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if(response.isSuccessful){
                            requireActivity().startActivity(requireActivity().intent)
                            requireActivity().finish()
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