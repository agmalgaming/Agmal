package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.agmal.agmal.model.HeroModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_hero_layout.view.*

class HeroAdapter: RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

    private lateinit var heroItems: List<HeroModel>

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val heroIcon = v.iv_heroIcon_rvHero
        val heroName = v.tv_heroName_rvHero
        val heroClass = v.tv_heroClass_rvHero

        fun bind(heroModel: HeroModel){
            heroName.text = heroModel.heroName
            heroClass.text = heroModel.heroClass
            Picasso.get().load(heroModel.heroIcon).into(heroIcon)
        }
    }

    fun heroAdapter(heroModel: List<HeroModel>){
        heroItems = heroModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_hero_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(heroItems.get(position))
    }

    override fun getItemCount(): Int {
        return heroItems.size
    }

}