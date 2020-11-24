
package com.all_the_best.knock_knock.infant_layout

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.all_the_best.knock_knock.R
import kotlinx.android.synthetic.main.activity_infant_home.*
import java.text.SimpleDateFormat
import java.util.*


class InfantHomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_home)
        val change_bg:ConstraintLayout = findViewById(R.id.infant_home)

        var day : Long = 0
        day = SystemClock.elapsedRealtime()
        val date = Date(day)
        val mFormat = SimpleDateFormat("HH:mm:ss")
        val time = mFormat.format(date).toString()

        infant_talk1.setOnClickListener{
            Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
        }


        when(time){
            in "10:00:00".."18:34:59" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg1)
            in "18:35:00".."21:34:59" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg2)
            in "21:35:00".."23:59:59" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg3)
            !in "10:00:00".."23:59:59" -> change_bg.setBackgroundResource(R.drawable.infant_home_bg3)
        }

        val intent = Intent(this, InfantSelectFeelActivity::class.java)
        char_talk_btn.setOnClickListener{
            startActivity(intent)
        }
    }
}