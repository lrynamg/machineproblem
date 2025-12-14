package com.example.machineproblem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GroupsAdapter(
    private val groups: List<Group>,
    private val onJoinClick: (Group) -> Unit
) : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupImage: ImageView = itemView.findViewById(R.id.groupImage)
        val groupName: TextView = itemView.findViewById(R.id.groupName)
        val groupDescription: TextView = itemView.findViewById(R.id.groupDescription)
        val joinButton: Button = itemView.findViewById(R.id.joinButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]

        holder.groupName.text = group.name
        holder.groupDescription.text = group.description

        // Load image with Glide (add dependency: implementation 'com.github.bumptech.glide:glide:4.15.1')
        Glide.with(holder.itemView.context)
            .load(group.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.groupImage)

        holder.joinButton.setOnClickListener {
            onJoinClick(group)
        }
    }

    override fun getItemCount() = groups.size
}