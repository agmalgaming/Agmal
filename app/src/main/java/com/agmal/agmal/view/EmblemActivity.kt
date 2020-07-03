package com.agmal.agmal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.agmal.agmal.R
import com.agmal.agmal.adapter.ItemAdapter
import com.agmal.agmal.model.EmblemModel
import com.agmal.agmal.model.ItemsModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_emblem.*
import kotlinx.android.synthetic.main.activity_items.*
import org.json.JSONObject

class EmblemActivity : AppCompatActivity() {

    private lateinit var mMenu: Menu
    private lateinit var emblemList: ArrayList<EmblemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emblem)

        setSupportActionBar(toolbar_emblem)
        mMenu = navDrawer_emblem.menu
        mMenu.findItem(R.id.nav_heroKategori_menu).setVisible(false)
        mMenu.findItem(R.id.nav_itemKategori_menu).setVisible(false)

        /** DRAWER NAVIGATION **/
        navDrawer_emblem.bringToFront()
        val menuToggle = ActionBarDrawerToggle(
            this, drawer_layout_emblem, toolbar_emblem,
            R.string.openDrawerNavigation,R.string.closeDrawerNavigation)
        drawer_layout_emblem.addDrawerListener(menuToggle)
        menuToggle.syncState()
        navDrawer_emblem.setNavigationItemSelectedListener(navigationItemSelectedListener())

        emblemList = ArrayList()
        getDataEmblem()

    }

    private fun navigationItemSelectedListener() =
        object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_hero_menu->{
                        val intent = Intent(this@EmblemActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    R.id.nav_item_menu->{
                        val intent = Intent(this@EmblemActivity, ItemsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }
                drawer_layout_emblem.closeDrawer(GravityCompat.START)
                return true
            }
        }

    private fun getDataEmblem(){
        val inputStream = assets.open("emblem-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")
        for (i in 0..data.length()-1){
            val emblems = data.getJSONObject(i)
            val emblemID = emblems.getString("id")
            val emblemName = emblems.getString("emblem_name")
            val emblemIcon = emblems.getString("icon")
            val emblemRole = emblems.getString("emblem_role")
            val dataAttr = emblems.getJSONArray("modifiers")
            emblemList.add(EmblemModel(emblemID,emblemName,emblemIcon,emblemRole,dataAttr))
        }
    }
}