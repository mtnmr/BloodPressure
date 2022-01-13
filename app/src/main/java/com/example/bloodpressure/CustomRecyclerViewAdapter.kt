package com.example.bloodpressure

import android.content.Intent
import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class CustomRecyclerViewAdapter(realmResults: RealmResults<BloodPress>) :
//1つのデータを生成したview（one_result）に設定する
    RecyclerView.Adapter<ViewHolder>() {

    private var rResults: RealmResults<BloodPress> = realmResults
    //Realmのクエリの実行結果RealmResultsを受け取る

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //新しいviewを作成、parentにはRecyclerViewを指定
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result, parent, false)
        val viewholder = ViewHolder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //resultsからオブジェクトを取得し、holderの持つone_results上のプロパティにオブジェクトの値を設定
        val bloodPress = rResults[position]
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm", bloodPress?.dateTime)
        holder.minMaxText?.text = "${bloodPress?.max.toString()}/${bloodPress?.min.toString()}"
        holder.pulseText?.text = bloodPress?.pulse.toString()
        holder.itemView.setBackgroundColor(if (position%2 == 0) Color.LTGRAY else Color.WHITE)

        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", bloodPress?.id)
            //EditActivityに遷移するときidを渡す
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //データセットの数を返す
        return rResults.size
    }
}