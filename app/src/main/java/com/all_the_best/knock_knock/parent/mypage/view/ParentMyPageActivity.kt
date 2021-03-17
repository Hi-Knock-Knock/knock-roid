package com.all_the_best.knock_knock.parent.mypage.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.PagerSnapHelper
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.databinding.ActivityParentMyPageBinding
import com.all_the_best.knock_knock.parent.mypage.adapter.ParentMyPageRcvAdapter
import com.all_the_best.knock_knock.parent.mypage.viewmodel.ParentMyPageViewModel
import com.all_the_best.knock_knock.util.StatusBarUtil

class ParentMyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentMyPageBinding
    private val parentMyPageViewModel: ParentMyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, resources.getColor(R.color.white_status_bar, null))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parent_my_page)
        binding.lifecycleOwner = this
        binding.txtEdit = "프로필 수정"
        binding.txtShowMore = "더보기"
        parentMyPageViewModel.setParentMyPageBabyList()
        setParentMyPageRcvAdapter()
        setParentMyPageBabyObserve()
        setSnapHelper()
    }

    private fun setParentMyPageRcvAdapter() {
        val parentMyPageRcvAdapter = ParentMyPageRcvAdapter()
        binding.parentMyPageRcvBaby.adapter = parentMyPageRcvAdapter
    }

    private fun setParentMyPageBabyObserve() {
        parentMyPageViewModel.parentMyPageBabyList.observe(this) { parentMyPageBabyList ->
            parentMyPageBabyList.let {
                if (binding.parentMyPageRcvBaby.adapter != null) with(binding.parentMyPageRcvBaby.adapter as ParentMyPageRcvAdapter) {
                    submitList(parentMyPageBabyList)
                }
            }
        }
    }

    private fun setSnapHelper() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.parentMyPageRcvBaby)
    }
}