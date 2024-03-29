package com.all_the_best.knock_knock.parent.home.view


import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.databinding.ActivityParentHomeBinding
import com.all_the_best.knock_knock.parent.home.adapter.ParentViewPagerAdapter
import com.all_the_best.knock_knock.parent.home.viewmodel.ParentHomeViewModel
import com.all_the_best.knock_knock.util.FragmentOnBackPressed
import com.all_the_best.knock_knock.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_parent_home.*
import android.Manifest
import kotlin.properties.Delegates


class ParentHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentHomeBinding
    private val parentHomeViewModel: ParentHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        StatusBarUtil.setStatusBar(this, resources.getColor(R.color.blue_status_bar, null))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parent_home)

        if (intent.getBooleanExtra("goFaq", false)) {
            Log.d("parent_home", "${intent.getBooleanExtra("goFaq", false)}")
            parentHomeViewModel.setGoFaqFlag(true)
            Log.d("parent_home", "${binding.parentViewpager.currentItem}")
        }
        setViewPagerAdapter()
        setOnItemSelectedListenerForBottomNavigation()
        setOnClickListenerForFloatingBtn()
        setGoFaqFlagObserve()
        requestReadExternalStoragePermission()
    }

    private fun setGoFaqFlagObserve(){
        parentHomeViewModel.goFaqFlag.observe(this){ goFaqFlag ->
            goFaqFlag.let{
                if(goFaqFlag){
                    binding.parentViewpager.currentItem = 2
                }
            }
        }
    }

    private fun setOnClickListenerForFloatingBtn() {
        binding.parentHomeBtnFloating.setOnClickListener {
            binding.parentViewpager.currentItem = 1
        }
    }

    private fun setOnItemSelectedListenerForBottomNavigation() {
        binding.parentBottomNavi.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when (it.itemId) {
                R.id.menu_home -> index = 0
                R.id.menu_talk -> index = 1
                R.id.menu_faq -> index = 2
            }
            binding.parentViewpager.currentItem = index
            true
        }
    }

    private fun setViewPagerAdapter() {
        val viewPagerAdapter = ParentViewPagerAdapter(supportFragmentManager)
        binding.parentViewpager.apply {
            adapter = viewPagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    parent_bottom_navi.menu.getItem(position).isChecked = true
                }
            })
        }
    }

    var time3: Long = 0 //백버튼 두번 누를 때 종료 동작에서 첫번째 누른시간
    override fun onBackPressed() {
        var cutPlace = 0    //문자열 끊을 위치 -> fragmentList[i].toString().substring(0, cutPlace) 여기서 사용됨
        var fragmentName: String = ""   //문자열 비교할 프래그먼트 이름
        when (parent_viewpager.currentItem) {
            0 -> {
                cutPlace = 18
                fragmentName = "ParentHomeFragment"
            }
            1 -> {
                cutPlace = 18
                fragmentName = "ParentTalkFragment"
            }
            2 -> {
                cutPlace = 17
                fragmentName = "ParentFaqFragment"
            }
        }
        //프래그먼트 별로 백버튼 메소드 실행 시 동작 다르게 구현하기 위한 작업.
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            for (i in 0 until fragmentList.size) {
                if (fragmentList[i].toString().substring(0, cutPlace) == fragmentName) {
                    if (fragmentList[i] is FragmentOnBackPressed) { //프래그먼트가 FragmentOnBackPressed 인터페이스 타입인지 확인
                        //코틀린에서는 특정 변수를 자신이 원하는 자료형으로 변환하기 위해서 as 연산자를 사용
                        if (!(fragmentList[i] as FragmentOnBackPressed).onBackPressed()) {
                            //true 일 경우  : 각 프래그먼트들의 메소드 동작
                            //false 일 경우 : 백버튼 2번 누르면 앱 종료
                            val time1 = System.currentTimeMillis()
                            val time2 = time1 - time3
                            if (time2 in 0..2000) {
                                ActivityCompat.finishAffinity(this) //해당 앱의 루트 액티비티를 종료시킨다.

                                System.runFinalization() //현재 작업중인 쓰레드가 다 종료되면, 종료 시키라는 명령어이다.

                                System.exit(0) // 현재 액티비티를 종료시킨다.
                            } else {
                                time3 = time1
                                Toast.makeText(
                                    applicationContext,
                                    "한번 더 누르시면 종료됩니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        return
                    }
                }
            }
        }
    }


    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
        }
    }
}