package com.agmal.agmal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agmal.agmal.R
import com.agmal.agmal.model.ItemsModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item_layout.view.*

class ItemAdapter:RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var itemList: List<ItemsModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(itemList.get(position))
    }

    fun itemAdapter(itemsModel: List<ItemsModel>){
        itemList = itemsModel
    }

    inner class ViewHolder(v:View):RecyclerView.ViewHolder(v){
        val itemName = v.tv_itemName_rvHero
        val itemIcon = v.iv_itemIcon_rvHero
        val itemCategory = v.tv_itemCategory_rvHero
        val itemTier = v.tv_itemTier_rvHero

        fun onBind(itemsModel: ItemsModel){
            val mItemTiear = itemsModel.itemTier

            itemName.text = itemsModel.itemName
            itemCategory.text = itemsModel.itemCategory
            itemTier.text = "Tier $mItemTiear"
            Picasso.get().load(itemsModel.itemIcon).into(itemIcon)
        }
    }
}