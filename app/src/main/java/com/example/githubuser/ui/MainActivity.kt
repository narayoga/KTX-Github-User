package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.model.UserDetail
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickFunction(object : UserAdapter.OnItemClickCallback{
            override fun onFunctionClicked(data: UserDetail){
                Intent(this@MainActivity, ProfileActivity::class.java).also {
                    it.putExtra(ProfileActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            searchBtn.setOnClickListener{
                searchUser()
            }

            inputQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                loadingAnimation(false)
            }
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = inputQuery.text.toString()
            if(query.isEmpty()) return
            loadingAnimation(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun loadingAnimation(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}