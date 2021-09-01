package io.android.games.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.android.core.data.Resource
import io.android.core.util.viewBinding
import io.android.games.R
import io.android.games.databinding.ActivityMainBinding
import io.android.games.detail.GameDetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val vm by viewModel<GameListViewModel>()

    private val gameListAdapter by lazy { GameListAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupGamesRecyclerView()
        observeGames()
    }

    private fun setupGamesRecyclerView() {
        with(binding.rvGames) {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = gameListAdapter
        }
        gameListAdapter.onItemClick = { game ->
            GameDetailActivity.start(this, game)
        }
    }

    private fun observeGames() {
        vm.games.observe(this) {
            when (it) {
                is Resource.Loading -> binding.pgbGames.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.pgbGames.visibility = View.GONE
                    gameListAdapter.refreshData(it.data)
                }
                is Resource.Error -> {
                    binding.pgbGames.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}