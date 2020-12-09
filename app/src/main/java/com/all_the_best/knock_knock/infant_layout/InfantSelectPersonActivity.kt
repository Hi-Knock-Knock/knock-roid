package com.all_the_best.knock_knock.infant_layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.all_the_best.knock_knock.R
import kotlinx.android.synthetic.main.activity_infant_select_person.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InfantSelectPersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_select_person)
        val change_bg: ConstraintLayout = findViewById(R.id.infant_select_person)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val formatted = current.format(formatter)


        when(formatted){
            in "08:00:000".."13:59:999" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg1)
            in "14:00:000".."19:59:999" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg2)
            in "20:00:00".."23:59:999" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg3)
            !in "08:00:00".."23:59:999" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg3)
        }

        val intent = Intent(this, InfantTalkStartActivity::class.java)
        infant_emj_person_5.setOnClickListener{
            startActivity(intent)
        }
    }
}