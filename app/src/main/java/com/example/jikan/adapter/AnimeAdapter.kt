package com.example.jikan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jikan.R
import com.example.jikan.model.AnimeData

class AnimeAdapter(
    private val onItemClick: (AnimeData) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private val animeList = mutableListOf<AnimeData>()

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.imagePoster)
        private val title: TextView = itemView.findViewById(R.id.textTitle)
        private val episodes: TextView = itemView.findViewById(R.id.textEpisodes)
        private val rating: TextView = itemView.findViewById(R.id.textRating)

        fun bind(anime: AnimeData) {

            val imageUrl =
                if (anime.images?.jpg?.largeImageUrl.isNullOrEmpty())
                    R.drawable.ic_placeholder
                else
                    anime.images?.jpg?.largeImageUrl

            title.text = anime.title ?: "N/A"
            episodes.text = "Episodes: ${anime.numberOfEpisodes ?: "-"}"
            rating.text = "⭐ ${anime.rating ?: "-"}"

            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(poster)

            itemView.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size

    fun submitList(newList: List<AnimeData>) {
        animeList.clear()
        animeList.addAll(newList)
        notifyDataSetChanged()
    }
}
