package com.all_the_best.knock_knock.parent.faq.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.util.StatusBarUtil

class ParentFaqSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_faq_search)

        StatusBarUtil.setStatusBar(this, resources.getColor(R.color.light_blue_status_bar, null))
    }
}