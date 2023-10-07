package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.model.UserDetail
import com.example.githubuser.data.model.local.Favorite
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickFunction(object :UserAdapter.OnItemClickCallback {
            override fun onFunctionClicked(data: UserDetail) {
                Intent(this@FavoriteActivity, ProfileActivity::class.java).also {
                    it.putExtra(ProfileActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(ProfileActivity.EXTRA_ID, data.id)
                    it.putExtra(ProfileActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this, {
            if(it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }
    private fun mapList(it: List<Favorite>): ArrayList<UserDetail> {
        val list = ArrayList<UserDetail>()
        for (user in it) {
            val userMapped = UserDetail(
                user.login,
                user.id,
                user.avatar_url
            )
            list.add(userMapped)
        }
        return list
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}