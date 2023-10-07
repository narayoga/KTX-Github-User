package com.example.githubuser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.data.model.local.Favorite
import com.example.githubuser.data.model.local.FavoriteDao
import com.example.githubuser.data.model.local.FavoriteDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun getFavorite():LiveData<List<Favorite>>? {
        return userDao?.getFavorite()
    }
}