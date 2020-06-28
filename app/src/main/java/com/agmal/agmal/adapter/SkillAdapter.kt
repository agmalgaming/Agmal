package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.agmal.agmal.model.HeroModel
import com.agmal.agmal.model.SkillModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_skill_layout.view.*

class SkillAdapter: RecyclerView.Adapter<SkillAdapter.ViewHolder>() {

    private lateinit var skillItems: List<SkillModel>
    private lateinit var mItemClickListener: ItemClickListener

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val iconSkill = v.iv_skillIcon_rvSkill

        fun bind(skillModel: SkillModel, clickListener: ItemClickListener,position: Int){
            Picasso.get().load(skillModel.skillIcon).into(iconSkill)

            itemView.setOnClickListener {
                clickListener.itemClickListener(skillModel,position)
            }
        }
    }

    fun skillAdapter(skillModel: List<SkillModel>, clickListener: ItemClickListener){
        skillItems = skillModel
        mItemClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_skill_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(skillItems.get(position),mItemClickListener,position)
    }

    override fun getItemCount(): Int {
        return skillItems.size
    }

    interface ItemClickListener{
        fun itemClickListener(skillModel: SkillModel, position: Int)
    }

}