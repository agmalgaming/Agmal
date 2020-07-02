package com.agmal.agmal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.agmal.agmal.R
import com.agmal.agmal.adapter.ItemAdapter
import com.agmal.agmal.model.HeroModel
import com.agmal.agmal.model.ItemsModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_items.*
import org.json.JSONObject

class ItemsActivity : AppCompatActivity() {

    private lateinit var mMenu: Menu
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemsArrayList: ArrayList<ItemsModel>
    private lateinit var mItemCategory: String
    private lateinit var mItemTier: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        setSupportActionBar(toolbar_items)
        mMenu = navDrawer_itemsActv.menu
        mMenu.findItem(R.id.nav_heroKategori_menu).setVisible(false)

        mItemCategory = ""
        mItemTier = ""

        /** DRAWER NAVIGATION **/
        navDrawer_itemsActv.bringToFront()
        val menuToggle = ActionBarDrawerToggle(
            this, drawer_layout_items, toolbar_items,
            R.string.openDrawerNavigation,R.string.closeDrawerNavigation)
        drawer_layout_items.addDrawerListener(menuToggle)
        menuToggle.syncState()
        navDrawer_itemsActv.setNavigationItemSelectedListener(navigationItemSelectedListener())

        itemsArrayList = ArrayList()

        getDataItemsFromJson()
    }

    private fun navigationItemSelectedListener() =
        object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_hero_menu->{
                        val intent = Intent(this@ItemsActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    R.id.nav_item_menu->{
                        mItemCategory = ""
                        mItemTier = ""
                    }
                    R.id.nav_Defenser_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_Movement_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_Roaming_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_Magic_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_Attack_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_Jungling_menu->{
                        mItemCategory = item.title.toString()
                        mItemTier = ""
                    }
                    R.id.nav_tier1_menu->{
                        mItemCategory = ""
                        mItemTier = "1"
                    }
                    R.id.nav_tier2_menu->{
                        mItemCategory = ""
                        mItemTier = "2"
                    }
                    R.id.nav_tier3_menu->{
                        mItemCategory = ""
                        mItemTier = "3"
                    }
                }
                getDataItemsFromJson()
                drawer_layout_items.closeDrawer(GravityCompat.START)
                return true
            }
        }

    private fun getDataItemsFromJson(){
        val inputStream = assets.open("item-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")

        itemsArrayList.clear()

        for (i in 0..data.length()-1){
            val items = data.getJSONObject(i)
            val itemID = items.getString("id")
            val itemName = items.getString("item_name")
            val itemIcon = items.getString("icon")
            val itemTier = items.getString("item_tier")
            val itemCategory = items.getString("item_category")

            if(mItemCategory.isNotEmpty()){
                Log.w("masuk","1")
                if(itemCategory == mItemCategory){
                    itemsArrayList.add(
                        ItemsModel(
                            itemID, itemName, itemIcon, itemTier, itemCategory
                        )
                    )
                }
            }
            else if(mItemTier.isNotEmpty()){
                if(itemTier == mItemTier){
                    itemsArrayList.add(
                        ItemsModel(
                            itemID, itemName, itemIcon, itemTier, itemCategory
                        )
                    )
                }
            }
            else{
                itemsArrayList.add(
                    ItemsModel(
                        itemID, itemName, itemIcon, itemTier, itemCategory
                    )
                )
            }
        }
        settingRecyclerView()
    }

    private fun settingRecyclerView(){
        itemAdapter = ItemAdapter()
        itemAdapter.itemAdapter(itemsArrayList)
        rv_item_items.apply {
            layoutManager = GridLayoutManager(this@ItemsActivity,3)
            this.adapter = itemAdapter
        }
    }

}