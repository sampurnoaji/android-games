package io.android.favorite.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.android.core.util.viewBinding
import io.android.favorite.databinding.ActivityGameFavoriteBinding
import io.android.favorite.di.favoriteModule
import io.android.games.R
import io.android.games.detail.GameDetailActivity
import io.android.games.list.GameListAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class GameFavoriteActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityGameFavoriteBinding::inflate)
    private val vm by viewModel<GameFavoriteViewModel>()

    private val gameListAdapter by lazy { GameListAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        loadKoinModules(favoriteModule)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        setupGamesRecyclerView()
        observeGameFavorites()
    }

    private fun setupGamesRecyclerView() {
        with(binding.rvGames) {
            layoutManager = GridLayoutManager(this@GameFavoriteActivity, 2)
            adapter = gameListAdapter
        }
        gameListAdapter.onItemClick = { game ->
            GameDetailActivity.start(this, game)
        }
    }

    private fun observeGameFavorites() {
        vm.favorites.observe(this) {
            gameListAdapter.refreshData(it)
            if (it.isEmpty()) {
                Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}