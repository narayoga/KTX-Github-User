package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.ComponentFollowerBinding

class ProfileFollowerFragment : Fragment(R.layout.component_follower){

    private var followerFragmentBinding: ComponentFollowerBinding? = null
    private val binding get() = followerFragmentBinding!!
    private lateinit var viewModel:ProfileFollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username:String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(ProfileActivity.EXTRA_USERNAME).toString()

        followerFragmentBinding = ComponentFollowerBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProfileFollowerViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollower().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followerFragmentBinding = null
    }

    private fun showLoading(state: Boolean) {
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}