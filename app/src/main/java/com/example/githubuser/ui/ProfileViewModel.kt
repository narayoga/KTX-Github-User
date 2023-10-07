package com.example.githubuser.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.model.Profile
import com.example.githubuser.data.model.local.Favorite
import com.example.githubuser.data.model.local.FavoriteDao
import com.example.githubuser.data.model.local.FavoriteDatabase
import com.example.githubuser.data.networking.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application):AndroidViewModel(application) {
    val user = MutableLiveData<Profile>()

    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?
    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

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

    fun addToFavorite(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            var users = Favorite(
                username,
                id,
                avatar_url
            )
            userDao?.addFavorite(users)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteUser(id)
        }
    }
}