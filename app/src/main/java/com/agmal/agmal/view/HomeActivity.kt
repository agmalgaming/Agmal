package com.agmal.agmal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.agmal.agmal.R
import com.agmal.agmal.adapter.HeroAdapter
import com.agmal.agmal.model.HeroModel
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var heroList: ArrayList<HeroModel>
    private lateinit var heroAdapter: HeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        heroList = ArrayList()
        getDataFromJson()

    }

    private fun getDataFromJson(){
        val inputStream = assets.open("hero-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")

        for (i in 0..data.length() - 1){
            val heroes = data.getJSONObject(i)
            val heroID = heroes.getString("id")
            val heroName = heroes.getString("hero_name")
            val heroIcon = heroes.getString("hero_icon")
            val heroClass = heroes.getString("class")
            val heroRelease = heroes.getString("release_year")

            if(heroClass == "Fighter"){
                heroList.add(
                    HeroModel(heroID,heroName,heroClass,heroIcon,heroRelease)
                )
            }
        }
        Log.w("LIST HERO",heroList.toString())
        setDataToRecyclerView()
    }

    private fun setDataToRecyclerView(){
        heroAdapter = HeroAdapter()
        heroAdapter.heroAdapter(heroList)
        rv_hero_home.apply {
            layoutManager = GridLayoutManager(this@HomeActivity,4)
            this.adapter = heroAdapter
        }
    }
}