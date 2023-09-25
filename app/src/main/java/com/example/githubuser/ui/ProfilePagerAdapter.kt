package com.example.githubuser.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.R

class ProfilePagerAdapter(private val mCtx: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ProfileFollowerFragment()
            }
            1 -> {
                fragment = ProfileFollowingFragment()
            }
        }
        return fragment as Fragment
    }  override fun getPageTitle(position: Int): CharSequence?{ return mCtx.resources.getString(TAB_TITLES[position])}
}

