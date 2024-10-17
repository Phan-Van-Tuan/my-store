package com.jpatrick.mystore.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, private val tabTitles: Array<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment {
        return OrderListFragment.newInstance(tabTitles[position]) // Gửi tên tab để lấy dữ liệu phù hợp
    }
}
