package com.all_the_best.knock_knock.infant.change.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.all_the_best.knock_knock.infant.change.view.InfantSwitchDamiFragment
import com.all_the_best.knock_knock.infant.change.view.InfantSwitchKnockknockFragment
import com.all_the_best.knock_knock.infant.change.view.InfantSwitchTimiFragment

class InfantSwitchViewPagerAdapter(fm: FragmentManager):
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = when(position){
        0 -> InfantSwitchDamiFragment()
        1 -> InfantSwitchKnockknockFragment()
        2 -> InfantSwitchTimiFragment()

        else -> throw IllegalStateException("Unexpected position $position")
    }

    override fun getCount(): Int = 3
}