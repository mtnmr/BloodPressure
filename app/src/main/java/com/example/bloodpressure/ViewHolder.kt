package com.example.bloodpressure

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
    //一つのデータのviewの参照を保持
    var dateText:TextView? = null
    var minMaxText:TextView? = null
    var pulseText:TextView? = null

    //上記プロパティと、レイアウトのviewを対応させる
    init{
        dateText = itemview.findViewById(R.id.dateText)
        minMaxText = itemview.findViewById(R.id.minMaxText)
        pulseText = itemview.findViewById(R.id.pulseText)
    }
}