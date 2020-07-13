package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.agmal.agmal.model.SubtalentsModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_subtalent_layout.view.*

class SubtalentsAdapter:RecyclerView.Adapter<SubtalentsAdapter.ViewHolder>() {

    private lateinit var subtalentList: List<SubtalentsModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_subtalent_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return subtalentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subtalentList.get(position))
    }

    fun subtalentAdapter(subtalentList: List<SubtalentsModel>){
        this.subtalentList = subtalentList
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val iv_subtalent = view.iv_subtalentIcon_subtalLayout
        val tv_subtalner = view.tv_subtalentTitle_subtalLayout

        fun bind(subtalentsModel: SubtalentsModel){
            tv_subtalner.text = subtalentsModel.subtalentName
            Picasso.get().load(subtalentsModel.subtalentIcon).into(iv_subtalent)
        }
    }
}