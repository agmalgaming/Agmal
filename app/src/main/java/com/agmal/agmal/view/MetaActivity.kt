package com.agmal.agmal.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.agmal.agmal.R
import com.agmal.agmal.adapter.MetaAdapter
import com.agmal.agmal.model.MetaModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_meta.*

class MetaActivity : AppCompatActivity(), MetaAdapter.ItemClickListener {

    private lateinit var mMenu: Menu
    private lateinit var db: DatabaseReference
    private lateinit var metaAdapter: MetaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta)

        db = Firebase.database.reference
        metaAdapter = MetaAdapter()

        setSupportActionBar(toolbar_meta)
        supportActionBar?.title = "Info Meta"
        mMenu = navDrawer_meta.menu
        mMenu.findItem(R.id.nav_itemKategori_menu).setVisible(false)
        mMenu.findItem(R.id.nav_emblemKategori_menu).setVisible(false)
        mMenu.findItem(R.id.nav_heroKategori_menu).setVisible(false)

        navDrawer_meta.bringToFront()
        val menuToggle = ActionBarDrawerToggle(
            this, drawer_layout_meta, toolbar_meta,
            R.string.openDrawerNavigation,R.string.closeDrawerNavigation)
        drawer_layout_meta.addDrawerListener(menuToggle)
        menuToggle.syncState()
        navDrawer_meta.setNavigationItemSelectedListener(navigationItemSelectedListener())


        dataMeta()
    }

    override fun onStart() {
        super.onStart()
        navDrawer_meta.setCheckedItem(R.id.nav_meta_menu)
    }

    private fun dataMeta(){
        db.child("meta").addValueEventListener(
            object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val metaList = ArrayList<MetaModel>()
                    for (metaSnapshot in snapshot.children){
                        val metaData = metaSnapshot.getValue(MetaModel::class.java)
                        metaList.add(metaData!!)
                    }
                    setDataKeRecyclerView(metaList)
                }
            }
        )
    }

    private fun setDataKeRecyclerView(metaList: List<MetaModel>){
        metaAdapter.metaAdapter(metaList,this)
        rv_meta_meta.apply {
            layoutManager = LinearLayoutManager(this@MetaActivity)
            adapter = metaAdapter
        }
    }

    private fun navigationItemSelectedListener() =
        object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_hero_menu -> {
                        val intent = Intent(this@MetaActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    R.id.nav_item_menu -> {
                        val intent = Intent(this@MetaActivity, ItemsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    R.id.nav_emblem_menu -> {
                        val intent = Intent(this@MetaActivity, EmblemActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
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
                drawer_layout_meta.closeDrawer(GravityCompat.START)
                return true
            }
        }

    override fun itemClickListener(metaModel: MetaModel, position: Int) {
        val intent = Intent(this, MetaViewActivity::class.java)
        intent.putExtra("META_ID",metaModel.metaID)
        startActivity(intent)
    }
}