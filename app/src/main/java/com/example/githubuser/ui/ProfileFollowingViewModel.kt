package com.example.githubuser.ui

import com.example.githubuser.data.model.UserDetail
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserDetail>>()

    fun setListFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserDetail>>{
                override fun onResponse(
                    call: Call<ArrayList<UserDetail>>,
                    response: Response<ArrayList<UserDetail>>
                ) {
                    if(response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserDetail>>, t: Throwable) {
                    t.message?.let { Log.d("failure", it) }
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserDetail>>{
        return listFollowing
    }
}

