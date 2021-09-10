package io.android.games.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import io.android.core.domain.model.Game
import io.android.core.util.viewBinding
import io.android.games.R
import io.android.games.databinding.ActivityGameDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GameDetailActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_KEY_GAME = "game"

        @JvmStatic
        fun start(context: Context, game: Game) {
            val starter = Intent(context, GameDetailActivity::class.java)
                .putExtra(INTENT_KEY_GAME, game)
            context.startActivity(starter)
        }
    }

    private val binding by viewBinding(ActivityGameDetailBinding::inflate)
    private val vm by viewModel<GameDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val game = intent.getParcelableExtra<Game>(INTENT_KEY_GAME)
        showGameDetail(game)
    }

    private fun showGameDetail(game: Game?) {
        game?.let {
            Glide.with(this).load(game.image).placeholder(R.color.purple_200)
                .into(binding.imgPoster)
            with(binding.content) {
                tvCharacter.text = game.character
                tvName.text = game.name
                tvGameSeries.text = game.gameSeries
                tvAmiiboSeries.text = game.amiiboSeries
            }

            var statusFavorite = game.isFavorite
            showFavoriteStatus(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                vm.setFavoriteGame(game, statusFavorite)
                showFavoriteStatus(statusFavorite)
            }
        }
    }

    private fun showFavoriteStatus(favorite: Boolean) {
        with(binding) {
            if (favorite) {
                fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@GameDetailActivity,
                        R.drawable.ic_baseline_favorite_24
                    )
                )
                Toast.makeText(
                    this@GameDetailActivity,
                    getString(R.string.add_to_favorite),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@GameDetailActivity,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )
                Toast.makeText(
                    this@GameDetailActivity,
                    getString(R.string.delete_from_favorite),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}