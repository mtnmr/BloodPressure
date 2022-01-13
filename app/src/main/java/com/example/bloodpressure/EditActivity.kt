package com.example.bloodpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bloodpressure.databinding.ActivityEditBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var binding :ActivityEditBinding
    private lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        //修正削除のために遷移してきた時,intentからid受け取る
        //fabボタンを押して遷移してきたときはid付加前なのでデフォルトの0を渡す
        val bpId = intent.getLongExtra("id", 0L)
        if (bpId > 0L){
            val bloodPress = realm.where<BloodPress>()
                .equalTo("id", bpId).findFirst()
            binding.maxEdit.setText(bloodPress?.max.toString())
            binding.minEdit.setText(bloodPress?.min.toString())
            binding.pulseEdit.setText(bloodPress?.pulse.toString())
            binding.deleteBtn.visibility = View.VISIBLE
        }else{
            binding.deleteBtn.visibility = View.INVISIBLE
        }


        binding.saveBtn.setOnClickListener{
            var max:Long = 0
            var min:Long = 0
            var pulse:Long = 0

            if (!binding.maxEdit.text.isNullOrEmpty()){
                max = binding.maxEdit.text.toString().toLong()
            }
            if (!binding.minEdit.text.isNullOrEmpty()){
                min = binding.minEdit.text.toString().toLong()
            }
            if (!binding.pulseEdit.text.isNullOrEmpty()){
                pulse= binding.pulseEdit.text.toString().toLong()
            }

            when(bpId){
                0L ->{
                    realm.executeTransaction {
                        val maxId = realm.where<BloodPress>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1L
                        val bloodPress = realm.createObject<BloodPress>(nextId)
                        bloodPress.dateTime = Date()
                        bloodPress.max = max
                        bloodPress.min = min
                        bloodPress.pulse = pulse
                    }
                }
                else ->{
                    realm.executeTransaction {
                        val bloodPress = realm.where<BloodPress>()
                            .equalTo("id", bpId).findFirst()
                        bloodPress?.max = max
                        bloodPress?.min = min
                        bloodPress?.pulse = pulse
                    }
                }
            }

            Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.deleteBtn.setOnClickListener{
            realm.executeTransaction {
                val bloodPress = realm.where<BloodPress>()
                    .equalTo("id", bpId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}