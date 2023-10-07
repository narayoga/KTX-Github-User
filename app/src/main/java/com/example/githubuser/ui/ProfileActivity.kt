package com.example.githubuser.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.setProfile(username)
        viewModel.getProfile().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@ProfileActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivProfile)
                    showLoading(false)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if(count != null){
                    if (count > 0){
                        binding.favToggle.isChecked = true
                        _isChecked = true
                    }else{
                        binding.favToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.favToggle.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked) {
                if (username != null) {
                    avatarUrl?.let { avatar -> viewModel.addToFavorite(username, id, avatar) }
                }
            } else {
                viewModel.deleteFavorite(id)
            }
            binding.favToggle.isChecked = _isChecked
        }

        val profilePagerAdapter = ProfilePagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = profilePagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

    }

    private fun showLoading(state: Boolean) {
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }

}