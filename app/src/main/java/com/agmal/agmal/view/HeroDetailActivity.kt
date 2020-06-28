package com.agmal.agmal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.agmal.agmal.R
import com.agmal.agmal.adapter.SkillAdapter
import com.agmal.agmal.model.SkillModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hero_detail.*
import org.json.JSONObject

class HeroDetailActivity : AppCompatActivity(), SkillAdapter.ItemClickListener {

    private lateinit var heroIDExtra: String
    private lateinit var skillList: ArrayList<SkillModel>
    private lateinit var skillAdapter: SkillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)

        heroIDExtra = intent.getStringExtra("HERO_ID").toString()
        skillList = ArrayList()

        getHeroByID()
    }

    private fun getHeroByID(){
        val inputStream = assets.open("hero-meta-final.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = JSONObject(json).getJSONArray("data")
        for (i in 0..data.length() - 1){
            val heroes = data.getJSONObject(i)
            val heroId = heroes.getString("id")
            if(heroId == heroIDExtra){
                val heroAttributes = heroes.getJSONArray("base_attributes").getJSONObject(0)

                val heroName = heroes.getString("hero_name")
                val heroClass = heroes.getString("class")
                val heroIconUri = heroes.getString("hero_icon")
                val movement_speed = heroAttributes.getString("movement_speed")
                val physical_attack = heroAttributes.getString("physical_attack")
                val physical_defense = heroAttributes.getString("physical_defense")
                val magic_power = heroAttributes.getString("magic_power")
                val magic_resistance = heroAttributes.getString("magic_resistance")
                val hp = heroAttributes.getString("hp")
                val mana = heroAttributes.getString("mana")
                val attack_speed = heroAttributes.getString("attack_speed")
                val hp_regen_rate = heroAttributes.getString("hp_regen_rate")
                val mana_regen_rate = heroAttributes.getString("mana_regen_rate")

                tv_heroName_heroDetail.text = heroName
                tv_heroClass_detailHero.text = heroClass
                tv_mvspeed_heroDetail.text = movement_speed
                tv_pAttack_heroDetail.text = physical_attack
                tv_pDefense_heroDetail.text = physical_defense
                tv_mPower_heroDetail.text = magic_power
                tv_mResistance_heroDetail.text = magic_resistance
                tv_hp_heroDetail.text = hp
                tv_mana_heroDetail.text = mana
                tv_attckSpeed_heroDetail.text = attack_speed
                tv_hpRegen_heroDetail.text = hp_regen_rate
                tv_manaRegen_heroDetail.text = mana_regen_rate

                Picasso.get().load(heroIconUri).into(iv_heroIcon_heroDetail)

                val skillsArray = heroes.getJSONArray("skills")

                for(j in 0..skillsArray.length()-1){
                    val skills = skillsArray.getJSONObject(j)
                    val skillName = skills.getString("skill_name")
                    val skillIcon = skills.getString("skill_icon")
                    val skillType = skills.getString("type")
                    val skillCooldown = skills.getString("cooldown")
                    val skillManacost = skills.getString("manacost")
                    val skillDescription = skills.getString("description")
                    skillList.add(
                        SkillModel(skillName,skillIcon,skillType,skillCooldown,skillManacost,skillDescription)
                    )
                }
            }
        }
        setSkillDataToRecyclerview()
    }

    private fun setSkillDataToRecyclerview(){
        skillAdapter = SkillAdapter()
        skillAdapter.skillAdapter(skillList,this)
        rv_skill_heroDetail.apply {
            layoutManager = LinearLayoutManager(this@HeroDetailActivity,LinearLayoutManager.HORIZONTAL,false)
            this.adapter = skillAdapter
        }
    }

    override fun itemClickListener(skillModel: SkillModel, position: Int) {
        when(position){
            position->{
                lin_skillDetail_heroDetail.visibility = View.VISIBLE
                tv_skillName_heroDetail.text = skillModel.skillName
                tv_type_heroDetail.text = skillModel.skillType
                tv_manacost_heroDetail.text = skillModel.skillManacost
                tv_cooldown_heroDetail.text = skillModel.skillColdown
                tv_skillDesc_heroDetail.text = skillModel.skillDescription
            }
        }
    }
}