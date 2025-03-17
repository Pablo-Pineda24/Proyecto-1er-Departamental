package com.example.proyecto01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto01.db.UserModel

class UserAdapter(private val userList: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUserId: TextView = itemView.findViewById(R.id.textViewUserId)
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewEdad: TextView = itemView.findViewById(R.id.textViewEdad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textViewUserId.text = "#${currentUser.id}"
        holder.textViewNombre.text = currentUser.nombre
        holder.textViewEdad.text = "Edad: ${currentUser.edad}"
    }

    override fun getItemCount() = userList.size

    fun updateData(newUserList: ArrayList<UserModel>) {
        userList.clear()
        userList.addAll(newUserList)
        notifyDataSetChanged()
    }
}