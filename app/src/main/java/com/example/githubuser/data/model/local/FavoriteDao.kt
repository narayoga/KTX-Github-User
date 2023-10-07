package com.example.githubuser.data.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_list")
    fun  getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite_list WHERE favorite_list.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_list WHERE favorite_list.id = :id")
    suspend fun deleteUser(id: Int): Int
}