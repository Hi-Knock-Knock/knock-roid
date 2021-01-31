
package com.all_the_best.knock_knock.infant_layout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.all_the_best.knock_knock.R
import kotlinx.android.synthetic.main.activity_infant_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class InfantHomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_home)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val formatted = current.format(formatter)

        infant_talk1.setOnClickListener{
            Log.d("time", formatted)
        }

        when(formatted){
            in "08:00:000".."13:59:999" -> {
                infant_home.setBackgroundResource(R.drawable.img_infant_home_day_bg)
                char_deco_btn.setImageResource(R.drawable.ic_infant_deco_btn_1)
                char_talk_btn.setImageResource(R.drawable.ic_infant_chat_btn_1)
                char_change_btn.setImageResource(R.drawable.ic_infant_change_btn_1)
            }
            in "14:00:000".."19:59:999" -> {
                infant_home.setBackgroundResource(R.drawable.img_infant_home_sunset_bg)
                char_deco_btn.setImageResource(R.drawable.ic_infant_deco_btn_2)
                char_talk_btn.setImageResource(R.drawable.ic_infant_chat_btn_2)
                char_change_btn.setImageResource(R.drawable.ic_infant_change_btn_2)
            }
            in "20:00:00".."23:59:999" -> {
                infant_home.setBackgroundResource(R.drawable.img_infant_home_night_bg)
                char_deco_btn.setImageResource(R.drawable.ic_infant_deco_btn_3)
                char_talk_btn.setImageResource(R.drawable.ic_infant_chat_btn_3)
                char_change_btn.setImageResource(R.drawable.ic_infant_change_btn_3)
            }
            !in "08:00:00".."23:59:999" -> {
                infant_home.setBackgroundResource(R.drawable.img_infant_home_night_bg)
                char_deco_btn.setImageResource(R.drawable.ic_infant_deco_btn_3)
                char_talk_btn.setImageResource(R.drawable.ic_infant_chat_btn_3)
                char_change_btn.setImageResource(R.drawable.ic_infant_change_btn_3)
            }
        }


        val intent1 = Intent(this, InfantSelectFeelActivity::class.java)
        char_talk_btn.setOnClickListener{
            startActivity(intent1)
        }
        val intent2 = Intent(this, InfantSwitchCharacterActivity::class.java)
        char_change_btn.setOnClickListener{
            startActivity(intent2)
        }

        val intent3 = Intent(this, InfantGiftStartActivity::class.java)
        infant_icon_gift.setOnClickListener{
            startActivity(intent3)
        }

        val intent4 = Intent(this, InfantDecoActivity::class.java)
        char_deco_btn.setOnClickListener{
            startActivity(intent4)
        }

    }
}
