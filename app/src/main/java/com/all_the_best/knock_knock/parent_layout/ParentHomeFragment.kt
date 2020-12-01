package com.all_the_best.knock_knock.parent_layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.parent_layout.rcv.ParentHomeAdapter
import com.all_the_best.knock_knock.parent_layout.rcv.ParentHomeData
import kotlinx.android.synthetic.main.fragment_parent_home.*

class ParentHomeFragment : Fragment() {
    private lateinit var parentHomeAdapter: ParentHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentHomeAdapter = ParentHomeAdapter(view.context)
        parent_home_rcv.apply{
            adapter = parentHomeAdapter
            layoutManager = LinearLayoutManager(view.context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        }
        parentHomeAdapter.data = mutableListOf(
            ParentHomeData(R.drawable.rcv_parent_home_img),
            ParentHomeData(R.drawable.rcv_parent_home_img),
            ParentHomeData(R.drawable.rcv_parent_home_img)
        )

        //햄버거바 클릭시 DrawerLayout 열
        home_btn_hamburger.setOnClickListener {
            home_drawer_layout.openDrawer(GravityCompat.START) //네비게이션 드로어 열기
        }

        super.onViewCreated(view, savedInstanceState)
    }


}