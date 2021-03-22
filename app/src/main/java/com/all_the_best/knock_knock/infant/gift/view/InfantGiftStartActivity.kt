package com.all_the_best.knock_knock.infant.gift.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.infant.home.view.InfantHomeActivity
import kotlinx.android.synthetic.main.activity_infant_gift_start.*

class InfantGiftStartActivity : AppCompatActivity() {
    private var bgSelect: Int = 1
    private var chSelect: Int = 0
    private var cookieCount: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_gift_start)

        val cookieGiftCount1: TextView = findViewById(R.id.infant_gift_start_cookie_count)
        bgSelect = intent.getIntExtra("bgSelect",1)
        chSelect = intent.getIntExtra("chSelect",0)
        cookieCount = intent.getIntExtra("cookieCount",5)
        cookieGiftCount1.text = cookieCount.toString()

        //var count:Int = cookieCount //보유 쿠키 개수
        var count3:Int = 0 //소비한 쿠키 개

        window.statusBarColor = Color.parseColor("#8A2A6C")

        // 선물 상자 받는 화면으로 이동
        val intent1 = Intent(this, InfantGiftBoxActivity::class.java)
        intent1.putExtra("bgSelect",bgSelect)
        intent1.putExtra("chSelect",chSelect)
        intent1.putExtra("cookieCount",cookieCount)


        infant_empty_cookie1.setOnClickListener {
            if(cookieCount!=2){
                cookieCount--
                count3++
            }
            if(count3==3){
                startActivity(intent1)
                overridePendingTransition(0, 0)
            }
            infant_empty_cookie1.setImageResource(R.drawable.img_infant_full_cookie)
            cookieGiftCount1.text = cookieCount.toString()
        }

        infant_empty_cookie2.setOnClickListener {
            if(cookieCount!=2){
                cookieCount--
                count3++
            }
            if(count3==3){
                startActivity(intent1)
                overridePendingTransition(0, 0)
            }
            infant_empty_cookie2.setImageResource(R.drawable.img_infant_full_cookie)
            cookieGiftCount1.text = cookieCount.toString()


        }

        infant_empty_cookie3.setOnClickListener {
            if(cookieCount!=2){
                cookieCount--
                count3++
            }
            if(count3==3){
                cookieGiftCount1.text = cookieCount.toString()
                intent1.putExtra("cookieCount",cookieCount) //줄어든 쿠키갯수 받기
                startActivity(intent1)
                overridePendingTransition(0, 0)
            }
            infant_empty_cookie3.setImageResource(R.drawable.img_infant_full_cookie)
        }

        // 홈화면으로 돌아가기
        val intentGoHome = Intent(this, InfantHomeActivity::class.java)
        infant_icon_gift_out1.setOnClickListener{
            intentGoHome.putExtra("bgSelect",bgSelect)
            intentGoHome.putExtra("chSelect",chSelect)
            intentGoHome.putExtra("cookieCount",cookieCount)
            startActivity(intentGoHome)
            overridePendingTransition(0, 0)
        }

    }
}