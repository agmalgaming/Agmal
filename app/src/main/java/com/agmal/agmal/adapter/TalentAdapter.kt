package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_talent_layout.view.*

class TalentAdapter:RecyclerView.Adapter<TalentAdapter.ViewHolder>() {

    private lateinit var talentList: List<TalentModel>
    private lateinit var mClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_talent_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return talentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(talentList.get(position),mClickListener,position)
    }

    fun talentAdapter(talentList: List<TalentModel>,clickListener: ItemClickListener){
        this.talentList = talentList
        this.mClickListener = clickListener
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val iv_talent = view.iv_talentIcon_talentLayout
        val tv_talner = view.tv_talentTitle_talentLayout

        fun bind(talentModel: TalentModel, clickListener: ItemClickListener, position: Int){
            tv_talner.text = talentModel.talentName
            Picasso.get().load(talentModel.talentIcon).into(iv_talent)

            itemView.setOnClickListener {
                clickListener.itemClickListener(talentModel,position)
            }
        }
    }

    interface ItemClickListener{
        fun itemClickListener(talentModel: TalentModel, position: Int)
    }
}