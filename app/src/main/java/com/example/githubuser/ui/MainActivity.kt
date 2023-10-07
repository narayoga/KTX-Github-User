package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.model.UserDetail
import com.example.githubuser.data.model.local.SettingPreferences
import com.example.githubuser.data.model.local.dataStore
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("UseSwitchCompatOrMaterialCode")
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
                    it.putExtra(ProfileActivity.EXTRA_ID, data.id)
                    it.putExtra(ProfileActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        val pref = SettingPreferences.getInstance(application.dataStore)
//       viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
//            .get(UserViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            UserViewModel::class.java
        )

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            searchBtn.setOnClickListener{
                searchUser()
            }

            inputQuery.setOnKeyListener { _, keyCode, event ->
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

        val toolbar = binding.topAppBar
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

//        val buttonToggleTheme = binding.OnOff
//        buttonToggleTheme.setOnClickListener {
//            toggleTheme()
//        }

        val switchTheme = binding.OnOff

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu1 -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun toggleTheme() {
//        if (isDarkTheme) {
//            setTheme(R.style.Night_Theme_GithubUser)
//        } else {
//            setTheme(R.style.Base_Theme_GithubUser)
//        }
//        recreate()
//        isDarkTheme = !isDarkTheme
//    }


}