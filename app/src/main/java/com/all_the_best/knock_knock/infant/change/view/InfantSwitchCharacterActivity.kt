package com.all_the_best.knock_knock.infant.change.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.infant.home.view.InfantHomeActivity
import com.all_the_best.knock_knock.infant.change.adapter.InfantSwitchViewPagerAdapter
import com.all_the_best.knock_knock.util.FragmentOnBackPressed
import kotlinx.android.synthetic.main.activity_infant_switch_character.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InfantSwitchCharacterActivity : AppCompatActivity() {

    private var musicPlay:Int=0
    private var bgSelect: Int = 1
    private var chSelect: Int = 0
    private var cookieCount: Int = 5
    private var giftSelect:Int=0

    private lateinit var switchViewPagerAdapter: InfantSwitchViewPagerAdapter
    //private val infantSelectChViewModel: InfantSelectChViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_switch_character)
        bgSelect = intent.getIntExtra("bgSelect",1)
        chSelect = intent.getIntExtra("chSelect",0)
        cookieCount = intent.getIntExtra("cookieCount",5)
        giftSelect = intent.getIntExtra("giftSelect",0)
        musicPlay = intent.getIntExtra("musicPlay",0)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val formatted = current.format(formatter)


        when(formatted){
            in "08:00:000".."13:59:999" -> {
                window.statusBarColor = Color.parseColor("#57DDFF")
                when (bgSelect) {
                    1 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_morning_bg)
                    2 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_flower1)
                    3 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_sea1)
                    4 -> {
                        infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_space1)
                        window.statusBarColor = Color.parseColor("#0F0E15")
                    } // 우주
                }

            }
            in "14:00:000".."19:59:999" -> {
                window.statusBarColor = Color.parseColor("#FF6471")
                when (bgSelect) {
                    1 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_after_bg)
                    2 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_flower2)
                    3 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_sea2)
                    4 -> {
                        infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_space2)
                        window.statusBarColor = Color.parseColor("#0F0E15")
                    } // 우주

                }

            }
            in "20:00:00".."23:59:999" -> {
                when (bgSelect) {
                    1 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_night_bg)
                    2 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_flower3)
                    3 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_sea3)
                    4 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_space3) // 우주

                }
                window.statusBarColor = Color.parseColor("#0F0E15")
            }
            !in "08:00:00".."23:59:999" -> {
                when (bgSelect) {
                    1 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_night_bg)
                    2 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_flower3)
                    3 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_sea3)
                    4 -> infant_switch_character.setBackgroundResource(R.drawable.img_infant_home_bg_space3) // 우주
                }
                window.statusBarColor = Color.parseColor("#0F0E15")
            }
        }

        switchViewPagerAdapter =
            InfantSwitchViewPagerAdapter(
                supportFragmentManager
            )

        infant_viewpager_switch.adapter = switchViewPagerAdapter
//        dots_indicator.setViewPager(infant_viewpager_switch)
        infant_viewpager_switch.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    chSelect = 0
                }else if(position == 1){
                    chSelect = 1
                }else if (position == 2){
                    chSelect = 2
                }else{
                    chSelect = 0
                }
            }
        })

        val intent = Intent(this, InfantHomeActivity::class.java)
        switch_btn_back.setOnClickListener {
            intent.putExtra("bgSelect", bgSelect)
            intent.putExtra("chSelect",chSelect)
            intent.putExtra("cookieCount",cookieCount)
            intent.putExtra("giftSelect",giftSelect)
            intent.putExtra("musicPlay",musicPlay)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }

    }

    override fun onBackPressed() {
        var cutPlace = 0    //문자열 끊을 위치 -> fragmentList[i].toString().substring(0, cutPlace) 여기서 사용됨
        var fragmentName: String = ""   //문자열 비교할 프래그먼트 이름
        when (infant_viewpager_switch.currentItem) {
            0 -> {
                cutPlace = 24
                fragmentName = "InfantSwitchDamiFragment"
            }
            1 -> {
                cutPlace = 30
                fragmentName = "InfantSwitchKnockKnockFragment"
            }
            2 -> {
                cutPlace = 24
                fragmentName = "InfantSwitchTimiFragment"
            }
        }
        //프래그먼트 백버튼 막기
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            for (i in 0 until fragmentList.size) {
                if (fragmentList[i].toString().substring(0, cutPlace) == fragmentName) {
                    if (fragmentList[i] is FragmentOnBackPressed) {
                        if ((fragmentList[i] as FragmentOnBackPressed).onBackPressed()) {
                            return
                        }
                    }
                }
            }
        }
    }
}