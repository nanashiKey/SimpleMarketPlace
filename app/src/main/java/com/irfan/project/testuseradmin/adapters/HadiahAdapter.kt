package com.irfan.project.testuseradmin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.models.Hadiah


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 **/
class HadiahAdapter : RecyclerView.Adapter<HadiahAdapter.HadiahViewHolder>{

    lateinit var ctx : Context
    lateinit var hadiahs : ArrayList<Hadiah>
    constructor()
    constructor(ctx : Context, hadiahs : ArrayList<Hadiah>){
        this.ctx = ctx
        this.hadiahs = hadiahs
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HadiahViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item_hadiah, parent, false)
        return HadiahViewHolder(v)
    }

    override fun getItemCount(): Int {
        return hadiahs.size
    }

    override fun onBindViewHolder(holder: HadiahViewHolder, position: Int) {
        val hadiah = hadiahs.get(position)
        holder.tvItemName.text = hadiah.namahadiah
        holder.tvItemPoint.text = "Point : ${hadiah.point}"
        holder.tvItemStock.text = "Stock : ${hadiah.banyakitem}"
    }

    class HadiahViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tvItemName : TextView
        var tvItemPoint : TextView
        var tvItemStock : TextView
        var btnTukarPoint : Button
        init {
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemPoint = itemView.findViewById(R.id.tvItemPoint)
            btnTukarPoint = itemView.findViewById(R.id.btnHadiah)
            tvItemStock = itemView.findViewById(R.id.tvItemStock)
        }
    }
}