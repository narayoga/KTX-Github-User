package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.model.UserDetail
import com.example.githubuser.databinding.ComponentUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<UserDetail>()
    private var onItemClicked : OnItemClickCallback? = null

    fun setOnItemClickFunction (onItemClicked: OnItemClickCallback){
        this.onItemClicked = onItemClicked
    }

    fun setList(users: ArrayList<UserDetail>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ComponentUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (user: UserDetail) {
            binding.root.setOnClickListener{
                onItemClicked?.onFunctionClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = user.login
                tvId.text = user.id.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ComponentUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onFunctionClicked (data: UserDetail)
    }


}