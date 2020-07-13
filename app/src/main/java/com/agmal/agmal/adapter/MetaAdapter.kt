package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.agmal.agmal.model.MetaModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_meta_layout.view.*

class MetaAdapter:RecyclerView.Adapter<MetaAdapter.ViewHolder>() {

    private lateinit var metaList: List<MetaModel>
    private lateinit var itemClick: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_meta_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return metaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(metaList.get(position),itemClick,position)
    }

    fun metaAdapter(metaList: List<MetaModel>, clickListener: ItemClickListener){
        this.metaList = metaList
        itemClick = clickListener
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val iv_metaImage = view.iv_metaImage_metaLayout
        val tv_metaTitle = view.tv_metaTitle_metaLayout
        val tv_metaKonten = view.tv_metaKonten_metaLayout

        fun bind(metaModel: MetaModel, clickListener: ItemClickListener,position: Int){
            tv_metaTitle.text = metaModel.metaTitle
            tv_metaKonten.text = metaModel.metaKonten
            if(metaModel.metaImageURL.isNotEmpty()){
                Picasso.get().load(metaModel.metaImageURL).resize(500,500).into(iv_metaImage)
            }

            itemView.setOnClickListener {
                clickListener.itemClickListener(metaModel,position)
            }
        }
    }

    interface ItemClickListener{
        fun itemClickListener(metaModel: MetaModel, position: Int)
    }
}