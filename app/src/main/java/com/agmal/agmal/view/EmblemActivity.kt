package com.agmal.agmal.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.agmal.agmal.R
import com.agmal.agmal.adapter.ItemAdapter
import com.agmal.agmal.adapter.SubtalentsAdapter
import com.agmal.agmal.adapter.TalentAdapter
import com.agmal.agmal.adapter.TalentModel
import com.agmal.agmal.model.EmblemModel
import com.agmal.agmal.model.ItemsModel
import com.agmal.agmal.model.SubtalentsModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_emblem.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_items.*
import org.json.JSONObject

class EmblemActivity : AppCompatActivity(), TalentAdapter.ItemClickListener {

    private lateinit var mMenu: Menu
    private lateinit var emblemList: ArrayList<EmblemModel>
    private lateinit var emblemKategori:String
    private lateinit var mTalentName: String
    private lateinit var attr1:String
    private lateinit var attr2:String
    private lateinit var attr3:String
    private lateinit var attr4:String
    private lateinit var attr5:String


    private lateinit var subtalentAdapter: SubtalentsAdapter
    private lateinit var talentAdapter: TalentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emblem)

        setSupportActionBar(toolbar_emblem)
        mMenu = navDrawer_emblem.menu
        mMenu.findItem(R.id.nav_heroKategori_menu).setVisible(false)
        mMenu.findItem(R.id.nav_itemKategori_menu).setVisible(false)

        subtalentAdapter = SubtalentsAdapter()
        talentAdapter = TalentAdapter()

        /** DRAWER NAVIGATION **/
        navDrawer_emblem.bringToFront()
        navDrawer_emblem.setCheckedItem(R.id.nav_emblem_menu)
        val menuToggle = ActionBarDrawerToggle(
            this, drawer_layout_emblem, toolbar_emblem,
            R.string.openDrawerNavigation,R.string.closeDrawerNavigation)
        drawer_layout_emblem.addDrawerListener(menuToggle)
        menuToggle.syncState()
        navDrawer_emblem.setNavigationItemSelectedListener(navigationItemSelectedListener())

        btn_subtalent_emblem.setOnClickListener {
            rv_subtalents_emblem.visibility = View.VISIBLE
            btn_subtalent_emblem.setBackgroundColor(resources.getColor(R.color.colorPink3))
            btn_subtalent_emblem.setTextColor(resources.getColor(android.R.color.white))

            rv_talens_emblem.visibility = View.GONE
            btn_talent_emblem.setBackgroundColor(resources.getColor(R.color.colorPink1))
            btn_talent_emblem.setTextColor(resources.getColor(R.color.colorPink3))


            cv_descTalent_emblem.visibility = View.GONE
        }
        btn_talent_emblem.setOnClickListener {
            rv_talens_emblem.visibility = View.VISIBLE
            btn_talent_emblem.setBackgroundColor(resources.getColor(R.color.colorPink3))
            btn_talent_emblem.setTextColor(resources.getColor(android.R.color.white))

            rv_subtalents_emblem.visibility = View.GONE
            btn_subtalent_emblem.setBackgroundColor(resources.getColor(R.color.colorPink1))
            btn_subtalent_emblem.setTextColor(resources.getColor(R.color.colorPink3))

            cv_descTalent_emblem.visibility = View.VISIBLE
        }

        attr1 = "physical_attack"
        attr2 = "hp"
        attr3 = "attack_speed"
        attr4 = "hp_regen_rate"
        attr5 = "critical_strike_chance"
        emblemList = ArrayList()
        emblemKategori = "Physical"
        mTalentName = ""
        getDataEmblem(emblemKategori)
    }

    override fun onStart() {
        super.onStart()
        navDrawer_emblem.setCheckedItem(R.id.nav_emblem_menu)
    }

    private fun navigationItemSelectedListener() =
        object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_meta_menu->{
                        val intent = Intent(this@EmblemActivity,MetaActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
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
                    R.id.nav_physcEmblem->{
                        emblemKategori = "Physical"
                        attr1 = "physical_attack"
                        attr2 = "hp"
                        attr3 = "attack_speed"
                        attr4 = "hp_regen_rate"
                        attr5 = "critical_strike_chance"
                    }
                    R.id.nav_magicalEmblem->{
                        emblemKategori = "Magic"
                        attr1 = "magic_power"
                        attr2 = "hp"
                        attr3 = "mana"
                        attr4 = "hp_regen_rate"
                        attr5 = "cd_reduction"
                    }
                    R.id.nav_tankEmblem->{
                        emblemKategori = "Tank"
                        attr1 = "physical_defense"
                        attr2 = "magic_resistance"
                        attr3 = "hp"
                        attr4 = "hp_regen_rate"
                        attr5 = "cd_reduction"
                    }
                    R.id.nav_jungleEmblem->{
                        emblemKategori = "Jungle"
                        attr1 = "movement_speed"
                        attr2 = "physical_attack"
                        attr3 = "physical_lifesteal"
                        attr4 = "hp"
                        attr5 = "attack_speed"
                    }
                    R.id.nav_assassinEmblem->{
                        emblemKategori = "Assassin"
                        attr1 = "physical_pen"
                        attr2 = "physical_attack"
                        attr3 = "critical_strike_chance"
                        attr4 = "cd_reduction"
                        attr5 = "movement_speed"
                    }
                    R.id.nav_mageEmblem->{
                        emblemKategori = "Mage"
                        attr1 = "movement_speed"
                        attr2 = "magic_power"
                        attr3 = "magic_penetration"
                        attr4 = "spell_vamp"
                        attr5 = "cd_reduction"
                    }
                    R.id.nav_fighterEmblem->{
                        emblemKategori = "Fighter"
                        attr1 = "physical_attack"
                        attr2 = "physical_defense"
                        attr3 = "physical_penetration"
                        attr4 = "magic_resistance"
                        attr5 = "hp"
                    }
                    R.id.nav_marksmanEmblem->{
                        emblemKategori = "Marksman"
                        attr1 = "physical_attack"
                        attr2 = "physical_lifesteal"
                        attr3 = "physical_penetration"
                        attr4 = "attack_speed"
                        attr5 = "critical_strike_chance"
                    }
                    R.id.nav_supportEmblem->{
                        emblemKategori = "Support"
                        attr1 = "hybrid_regen"
                        attr2 = "hybrid_pen"
                        attr3 = "cd_reduction"
                        attr4 = "movement_speed"
                        attr5 = "hp"
                    }
                    R.id.nav_rateus->{
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    }
                }
                getDataEmblem(emblemKategori)
                drawer_layout_emblem.closeDrawer(GravityCompat.START)
                return true
            }
        }

    private fun getDataEmblem(kategori: String){
        val subtalentList = ArrayList<SubtalentsModel>()
        var spanCount = 0
        val talentList = ArrayList<TalentModel>()
        val inputStream = assets.open("emblem-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")
        for (i in 0..data.length()-1){
            val emblems = data.getJSONObject(i)
            val emblemName = emblems.getString("emblem_name")
            if(emblemName == kategori){
                if(emblemName == "Physical" || emblemName == "Magic")
                    spanCount = 2
                else spanCount = 3
                val emblemIcon = emblems.getString("icon")
                val dataAttr = emblems.getJSONArray("modifiers")
                val subtalents = emblems.getJSONArray("data")
                for (m in 0..subtalents.length()-1){
                    val sad = subtalents.getJSONObject(m)
                    val tier1 = sad.getJSONArray("tier1")
                    val tier2 = sad.getJSONArray("tier2")
                    val tier3 = sad.getJSONArray("tier3")
                    for (n in 0..tier1.length()-1){
                        val tier1Data = tier1.getJSONObject(n)
                        val tier2Data = tier2.getJSONObject(n)
                        val subtalentName = tier1Data.getString("name")
                        val subtalentName2 = tier2Data.getString("name")
                        val subtalentIcon = tier1Data.getString("icon")
                        val subtalentIcon2 = tier2Data.getString("icon")
                        subtalentList.add(
                            SubtalentsModel(subtalentName,subtalentIcon)
                        )
                        subtalentList.add(
                            SubtalentsModel(subtalentName2,subtalentIcon2)
                        )

                    }
                    for (k in 0..tier3.length()-1){
                        val tier3Data = tier3.getJSONObject(k)
                        val talentName = tier3Data.getString("name")
                        val talentIcon = tier3Data.getString("icon")
                        val passiveAbility = tier3Data.getString("passive_ability")
                        if(talentName == mTalentName){
                            tv_descTalent_emblem.text = passiveAbility
                        }
                        println("PASIVE => ${passiveAbility}")
                        talentList.add(
                            TalentModel(talentName,talentIcon,passiveAbility)
                        )
                    }
                    println("mamen: ${subtalentList}")
                }
                var attribute1 = ""
                var attribute2 = ""
                var attribute3 = ""
                var attribute4 = ""
                var attribute5 = ""
                for (j in 0..dataAttr.length()-1){
                    val attr = dataAttr.getJSONObject(j)
                    attribute1 = attr.getString(attr1)
                    attribute2 = attr.getString(attr2)
                    attribute3 = attr.getString(attr3)
                    attribute4 = attr.getString(attr4)
                    attribute5 = attr.getString(attr5)
                }
                Picasso.get().load(emblemIcon).into(iv_emblemIcon_emblem)
                tv_emblemName_emblem.text = emblemName

                tv_attr1Isi_emblem.text = attribute1
                tv_attr2Isi_emblem.text = attribute2
                tv_attr3Isi_emblem.text = attribute3
                tv_attr4Isi_emblem.text = attribute4
                tv_attr5Isi_emblem.text = attribute5

                tv_attr1Title_emblem.text = attr1
                tv_attr2Title_emblem.text = attr2
                tv_attr3Title_emblem.text = attr3
                tv_attr4Title_emblem.text = attr4
                tv_attr5Title_emblem.text = attr5
            }
        }
        initRVSubtalent(subtalentList)
        initRVTalent(talentList,spanCount)
    }

    private fun initRVSubtalent(subtalentList: ArrayList<SubtalentsModel>){
        subtalentAdapter.subtalentAdapter(subtalentList)
        rv_subtalents_emblem.apply {
            layoutManager = GridLayoutManager(this@EmblemActivity,3)
            adapter = subtalentAdapter
        }
    }

    private fun initRVTalent(talentList: ArrayList<TalentModel>, spanCount: Int){
        talentAdapter.talentAdapter(talentList,this)
        rv_talens_emblem.apply {
            layoutManager = GridLayoutManager(this@EmblemActivity,spanCount)
            adapter = talentAdapter
        }
    }

    override fun itemClickListener(talentModel: TalentModel, position: Int) {
        val oldTalentName = mTalentName
        mTalentName = talentModel.talentName
        if(oldTalentName != mTalentName){
            getDataEmblem(emblemKategori)
        }
    }
}