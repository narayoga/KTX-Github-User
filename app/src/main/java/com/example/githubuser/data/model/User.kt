package com.example.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class User(
	@field:SerializedName("items")
	val items: ArrayList<UserDetail>
)

