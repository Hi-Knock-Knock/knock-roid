package com.all_the_best.knock_knock.parent.mypage.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.PagerSnapHelper
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.databinding.ActivityParentMyPageBinding
import com.all_the_best.knock_knock.databinding.ItemParentMyPageBabyBinding
import com.all_the_best.knock_knock.parent.faq.viewmodel.ParentFaqViewModel
import com.all_the_best.knock_knock.parent.mypage.adapter.ParentMyPageRcvAdapter
import com.all_the_best.knock_knock.parent.mypage.viewmodel.ParentMyPageViewModel
import com.all_the_best.knock_knock.util.StatusBarUtil

class ParentMyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentMyPageBinding
    private val parentMyPageViewModel: ParentMyPageViewModel by viewModels()
    private val parentFaqViewModel: ParentFaqViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        parentMyPageViewModel.getDefaultUri()
        parentMyPageViewModel.getProfileImgFromStorage()
        binding.parentMyPageRcvBaby.adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        StatusBarUtil.setStatusBar(this, resources.getColor(R.color.white_status_bar, null))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parent_my_page)
        binding.lifecycleOwner = this
        binding.txtEdit = "프로필 수정"
        binding.txtShowMore = "더보기"
        binding.faqViewModel = parentFaqViewModel
        parentMyPageViewModel.getDefaultUri()
        parentMyPageViewModel.getProfileImgFromStorage()
        parentFaqViewModel.setScrapList()
        setScrapListObserve()
        setParentMyPageRcvAdapter()
        setParentMyPageBabyObserve()
        setSnapHelper()
        setOnClickListenerForGoBack()
        setOnClickListenerForEditProfile()
        setOnClickListenerForShowMoreBaby()
        setOnClickListenerForShowMoreScrap()
    }

    private fun setParentMyPageRcvAdapter() {
        val parentMyPageRcvAdapter = ParentMyPageRcvAdapter<ItemParentMyPageBabyBinding>(
            this,
            R.layout.item_parent_my_page_baby
        )
        binding.parentMyPageRcvBaby.adapter = parentMyPageRcvAdapter
    }

    private fun setParentMyPageBabyObserve() {
        parentMyPageViewModel.parentMyPageBabyList.observe(this) { parentMyPageBabyList ->
            parentMyPageBabyList?.let {
                if (binding.parentMyPageRcvBaby.adapter != null) with(binding.parentMyPageRcvBaby.adapter as ParentMyPageRcvAdapter<*>) {
                    submitList(parentMyPageBabyList)
                }
            }
        }
    }

    private fun setSnapHelper() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.parentMyPageRcvBaby)
    }

    private fun setOnClickListenerForGoBack() {
        binding.parentMyPageBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setOnClickListenerForEditProfile() {
        binding.parentMyPageTxtEditProfile.setOnClickListener {
            val intent = Intent(this, ParentEditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setOnClickListenerForShowMoreBaby() {
        binding.parentMyPageTxtShowMoreBaby.setOnClickListener {
            val intent = Intent(this, ParentMyBabyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setOnClickListenerForShowMoreScrap() {
        binding.parentMyPageTxtShowMoreScrap.setOnClickListener {
            val intent = Intent(this, ParentMyScrapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setScrapListObserve(){
        parentFaqViewModel.myScrapList.observe(this){
            binding.parentMyPageTxtFaq1.text = getString(parentFaqViewModel.firstTitle.title)
            binding.parentMyPageTxtFaq2.text = getString(parentFaqViewModel.secondTitle.title)
            binding.parentMyPageTxtFaq3.text = getString(parentFaqViewModel.thirdTitle.title)
        }
    }


}