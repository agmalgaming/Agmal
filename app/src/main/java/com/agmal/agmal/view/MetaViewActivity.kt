package com.agmal.agmal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agmal.agmal.R
import com.agmal.agmal.model.MetaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_meta_view.*

class MetaViewActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta_view)

        db = Firebase.database.reference

        setSupportActionBar(toolbar_metaView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getMetaDataByID()
    }

    private fun getMetaDataByID(){
        val metaID = intent.getStringExtra("META_ID")
        db.child("meta").child(metaID!!).addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataMeta = snapshot.getValue(MetaModel::class.java)
                    tv_metaTitle_metaView.text = dataMeta!!.metaTitle
                    tv_metaKonten_metaView.text = dataMeta.metaKonten
                    tv_sumber_metaView.text = dataMeta.sumber
                    supportActionBar?.setTitle(dataMeta.metaTitle)
                    if(dataMeta.metaImageURL.isNotEmpty()){
                        Picasso.get().load(dataMeta.metaImageURL).into(iv_metaImage_metaView)
                    }
                }
            }
        )
    }
}