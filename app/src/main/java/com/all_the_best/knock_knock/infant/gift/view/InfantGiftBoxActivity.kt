package com.all_the_best.knock_knock.infant.gift.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.infant.home.view.InfantHomeActivity
import kotlinx.android.synthetic.main.activity_infant_gift_box.*

class InfantGiftBoxActivity : AppCompatActivity() {
    private var bgSelect: Int = 1
    private var chSelect: Int = 0
    private var cookieCount: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_gift_box)
        val cookieGiftCount2:TextView=findViewById(R.id.infant_cookie_count_box)

        bgSelect = intent.getIntExtra("bgSelect",1)
        chSelect = intent.getIntExtra("chSelect",0)
        cookieCount = intent.getIntExtra("cookieCount",5)
        cookieGiftCount2.text = cookieCount.toString()

        window.statusBarColor = Color.parseColor("#8A2A6C")

        // 홈으로 돌아가기
        val intentGoHome = Intent(this, InfantHomeActivity::class.java)
        infant_icon_gift_out2.setOnClickListener{
            intentGoHome.putExtra("bgSelect",bgSelect)
            intentGoHome.putExtra("chSelect",chSelect)
            intentGoHome.putExtra("cookieCount",cookieCount)

            startActivity(intentGoHome)
            overridePendingTransition(0, 0)
        }

        // 선물 받는 화면으로 가기
        val intent1 = Intent(this, InfantGiftEndActivity::class.java)
        infant_gift_box.setOnClickListener {
            intent1.putExtra("bgSelect",bgSelect)
            intent1.putExtra("chSelect",chSelect)
            intent1.putExtra("cookieCount",cookieCount)
            startActivity(intent1)
            overridePendingTransition(0, 0)
        }
    }
}