package io.android.games.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.android.core.domain.model.Game
import io.android.games.databinding.ItemListGameBinding

class GameListAdapter(private var games: List<Game>): RecyclerView.Adapter<GameListAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun refreshData(games: List<Game>?) {
        if (games != null) {
            this.games = games
            notifyDataSetChanged()
        }
    }

    class ContentViewHolder(private val binding: ItemListGameBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(viewGroup: ViewGroup): ContentViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ItemListGameBinding.inflate(inflater, viewGroup, false)
                return ContentViewHolder(binding)
            }
        }

        fun bind(game: Game) {
            with(binding) {
                Glide.with(root.context)
                    .load(game.image)
                    .into(imgPoster)
                tvCharacter.text = game.character
                tvSeries.text = game.amiiboSeries
            }
        }
    }
}