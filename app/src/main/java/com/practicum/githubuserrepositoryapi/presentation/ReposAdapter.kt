package com.practicum.githubuserrepositoryapi.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.githubuserrepositoryapi.R
import com.practicum.githubuserrepositoryapi.domain.GithubApiModelItem

class ReposAdapter : RecyclerView.Adapter<ViewHolder>() {

    var vacancy = ArrayList<GithubApiModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vacancy[position])
    }

    override fun getItemCount(): Int = vacancy.size
}

class ViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
    ) {

    var name_company: TextView = itemView.findViewById(R.id.text_company)
    var name_logo: ImageView = itemView.findViewById(R.id.image_cover)
    var text_zp: TextView = itemView.findViewById(R.id.text_zp)
    var language: TextView = itemView.findViewById(R.id.language)
    var default_branch: TextView = itemView.findViewById(R.id.default_branch)

    fun bind(model: GithubApiModelItem) {
        Glide.with(itemView)
            .load(model.owner.avatar_url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(name_logo)

        name_company.text = model.name
        text_zp.text = model.full_name
        language.text = "language: ${model.language.toString()}"
        default_branch.text = "default branch: ${model.default_branch}"

        if (language.text.equals("language: null")) {
            language.text = "it have some languages"
        }

    }
}
