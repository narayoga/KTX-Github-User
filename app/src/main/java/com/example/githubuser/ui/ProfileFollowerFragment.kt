package com.example.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.githubuser.R
import com.example.githubuser.databinding.ComponentFollowerBinding

class ProfileFollowerFragment : Fragment(R.layout.component_follower){

    private var followerFragmentBinding: ComponentFollowerBinding? = null
    private val binding get() = followerFragmentBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followerFragmentBinding = ComponentFollowerBinding.bind(view)
    }
}