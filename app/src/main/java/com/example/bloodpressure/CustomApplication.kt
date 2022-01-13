package com.example.bloodpressure

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
//        val config = RealmConfiguration.Builder()
//            .deleteRealmIfMigrationNeeded()
//            .build()
        val config=RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}