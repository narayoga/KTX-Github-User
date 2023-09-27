package com.example.githubuser.ui

import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.model.User
import com.example.githubuser.data.model.UserDetail
import com.example.githubuser.data.networking.RetrofitClient
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserDetail>>()

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getUsers(query)
            .enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: retrofit2.Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                    t.message?.let { Log.d("failure", it) }
                }

            })
    }

    fun getUsers(): LiveData<ArrayList<UserDetail>>{
        return  listUser
    }
}