package com.all_the_best.knock_knock.infant.cookie.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.infant.deco.viewmodel.InfantDecoViewModel
import com.all_the_best.knock_knock.infant.home.view.InfantHomeActivity
import kotlinx.android.synthetic.main.activity_infant_cookie_save.*
import kotlinx.android.synthetic.main.activity_infant_deco.*

class InfantCookieSaveActivity : AppCompatActivity() {

    private var bgSelect: Int = 1
    private var chSelect: Int = 0

    //private var i:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_cookie_save)

        val cookieCount: TextView =findViewById(R.id.cookie_save_count_txt)

        bgSelect = intent.getIntExtra("bgSelect",1)
        chSelect = intent.getIntExtra("chSelect",0)

        window.statusBarColor = Color.parseColor("#FCC364")
        //var getCookieId= getResources().getIdentifier("ic_cookie_false$i","id", packageName)
        // 홈화면 쿠키 개수 불러오기
        if(intent.hasExtra("cookieCount")){
            cookieCount.text = intent.getStringExtra("cookieCount")
//            val str = cookieCount.text.toString()
//            val countInt = Integer.parseInt(str)
//            if (countInt==2){
//                ic_cookie_false1.setImageResource(R.drawable.ic_cookies_true)
//                ic_cookie_false2.setImageResource(R.drawable.ic_cookies_true)
//            }else{
//
//            }
        }else{
            Toast.makeText(this, "잘못 전달되었습니다.", Toast.LENGTH_SHORT).show()
        }
        ic_cookie_false1.setImageResource(R.drawable.ic_cookies_true)
        ic_cookie_false2.setImageResource(R.drawable.ic_cookies_true)
        //쿠키 채우기
//        for (i in 1..countInt){
//            Toast.makeText(this, "요기까지는 전달됨.$str", Toast.LENGTH_SHORT).show()
//            //var getCookieId= getResources().getIdentifier("ic_cookie_false$i","id", packageName)
//            //ic_cookie_false[i].setImageResource(R.drawable.ic_cookies_true)
//        }

        //홈 화면으로 돌아가기
        val intent = Intent(this, InfantHomeActivity::class.java)
        infant_icon_out_cookie.setOnClickListener{
            intent.putExtra("bgSelect",bgSelect)
            intent.putExtra("chSelect",chSelect)
            intent.putExtra("cookieCount",cookieCount.text)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}