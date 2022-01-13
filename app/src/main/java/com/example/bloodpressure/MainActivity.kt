package com.example.bloodpressure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bloodpressure.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.Sort

class MainActivity: AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var binding:ActivityMainBinding

    private lateinit var adapter:CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        realm = Realm.getDefaultInstance()

        binding.fab.setOnClickListener{ view ->
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val realmResults = realm.where(BloodPress::class.java)
            .findAll()
            .sort("id", Sort.DESCENDING)
        layoutManager = LinearLayoutManager(this)
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = CustomRecyclerViewAdapter(realmResults)
        recyclerView.adapter = this.adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}