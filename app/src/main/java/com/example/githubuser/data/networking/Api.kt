package com.example.githubuser.data.networking

import com.example.githubuser.data.model.Profile
import com.example.githubuser.data.model.User
import com.example.githubuser.data.model.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val token = "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"
interface Api {
//    search user
    @GET("search/users")
    @Headers("Authorization: token $token")
    fun getUsers(
        @Query("q") query: String
    ): Call<User>

//    detail user
    @GET("users/{username}")
    @Headers("Authorization: token $token")
    fun getProfile(
        @Path("username") username: String?
    ): Call<Profile>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $token")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserDetail>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $token")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserDetail>>
}

