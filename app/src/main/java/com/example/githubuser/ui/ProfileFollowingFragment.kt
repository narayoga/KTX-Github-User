package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.ComponentFollowerBinding

class ProfileFollowingFragment : Fragment(R.layout.component_follower){

    private var followingFragmentBinding: ComponentFollowerBinding? = null
    private val binding get() = followingFragmentBinding!!
    private lateinit var viewModel:ProfileFollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username:String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(ProfileActivity.EXTRA_USERNAME).toString()

        followingFragmentBinding = ComponentFollowerBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProfileFollowingViewModel::class.java]
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followingFragmentBinding = null
    }

    private fun showLoading(state: Boolean) {
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}