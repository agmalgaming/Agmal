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
import com.agmal.agmal.R
import com.agmal.agmal.adapter.HeroAdapter
import com.agmal.agmal.model.HeroModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity(), HeroAdapter.ItemClickListener {

    private lateinit var heroList: ArrayList<HeroModel>
    private lateinit var heroAdapter: HeroAdapter
    private lateinit var classHero: String
    private lateinit var titleToolbar: String

    private lateinit var mMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar_home)
        titleToolbar = "Heroes - All Hero"
        supportActionBar?.title = titleToolbar
        mMenu = navDrawer_home.menu
        mMenu.findItem(R.id.nav_itemKategori_menu).setVisible(false)
        classHero = ""

        /** DRAWER NAVIGATION **/
        navDrawer_home.bringToFront()
        val menuToggle = ActionBarDrawerToggle(
            this, drawer_layout_home, toolbar_home,
            R.string.openDrawerNavigation,R.string.closeDrawerNavigation)
        drawer_layout_home.addDrawerListener(menuToggle)
        menuToggle.syncState()
        navDrawer_home.setNavigationItemSelectedListener(navigationItemSelectedListener())

        heroList = ArrayList()
        getDataFromJson()

    }

    private fun navigationItemSelectedListener() =
        object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_item_menu->{
                        val intent = Intent(this@HomeActivity,ItemsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    R.id.nav_hero_menu ->{
                        classHero = ""
                        titleToolbar = "Heroes - All Hero"
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_fighter_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_support_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_mage_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_marksman_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_tank_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                    R.id.nav_assassin_menu ->{
                        classHero = item.title.toString()
                        titleToolbar = "Heroes - "+classHero
                        supportActionBar?.title = titleToolbar
                    }
                }
                getDataFromJson()
                drawer_layout_home.closeDrawer(GravityCompat.START)
                return true
            }
        }

    override fun onStart() {
        super.onStart()
        navDrawer_home.setCheckedItem(R.id.nav_hero_menu)
    }

    private fun getDataFromJson(){
        val inputStream = assets.open("hero-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")
        heroList.clear()
        for (i in 0..data.length() - 1){
            val heroes = data.getJSONObject(i)
            val heroID = heroes.getString("id")
            val heroName = heroes.getString("hero_name")
            val heroIcon = heroes.getString("hero_icon")
            val heroClass = heroes.getString("class")
            val heroRelease = heroes.getString("release_year")

            if(classHero.isNotEmpty()){
                if(heroClass == filterHero()){
                    heroList.add(
                        HeroModel(heroID,heroName,heroClass,heroIcon,heroRelease)
                    )
                }
            }
            else{
                heroList.add(
                    HeroModel(heroID,heroName,heroClass,heroIcon,heroRelease)
                )
            }
        }
        Log.w("LIST HERO",heroList.toString())
        setDataToRecyclerView()
    }

    private fun filterHero(): String{
        return classHero
    }

    private fun setDataToRecyclerView(){
        heroAdapter = HeroAdapter()
        heroAdapter.heroAdapter(heroList,this)
        rv_hero_home.apply {
            layoutManager = GridLayoutManager(this@HomeActivity,4)
            this.adapter = heroAdapter
        }
    }

    override fun itemClickListener(heroModel: HeroModel) {
        val intent = Intent(this,HeroDetailActivity::class.java)
        intent.putExtra("HERO_ID",heroModel.heroID)
        startActivity(intent)
    }
}