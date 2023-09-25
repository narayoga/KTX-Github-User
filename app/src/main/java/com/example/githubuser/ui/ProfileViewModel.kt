package com.example.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.model.Profile
import com.example.githubuser.data.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:ViewModel() {
    val user = MutableLiveData<Profile>()

    fun setProfile(username: String?){
        RetrofitClient.apiInstance
            .getProfile(username)
            .enqueue(object : Callback<Profile>{
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    if(response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getProfile(): LiveData<Profile> {
        return user
    }
}