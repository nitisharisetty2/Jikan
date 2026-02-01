package com.example.jikan

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jikan.adapter.AnimeAdapter
import com.example.jikan.util.Result
import com.example.jikan.viewmodel.AnimeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import com.example.jikan.util.NetworkUtils


@AndroidEntryPoint
class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textNoInternet: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: AnimeAdapter
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout


    private val viewModel: AnimeListViewModel by viewModels()
    private var firstTime = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.animeRecyclerView)
        textNoInternet = view.findViewById(R.id.textNoInternet)
        progressBar = view.findViewById(R.id.progressBar)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)


        if(firstTime){
            viewModel.fetchAnimeList()
            firstTime = false
        }
        adapter = AnimeAdapter { anime ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, AnimeDetailFragment.newInstance(anime))
                .addToBackStack(null)
                .commit()
        }

        swipeRefresh.setOnRefreshListener {
            if(NetworkUtils.isInternetAvailable(requireContext())) {
                viewModel.fetchAnimeList()
            }
        else{
                swipeRefresh.isRefreshing = false
                Toast.makeText(
                    requireContext(),
                    "Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()

            }}

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.animeState.collect { result ->
                    when (result) {

                        is Result.Loading -> {
                            if (!swipeRefresh.isRefreshing) {
                                showLoading()
                            }
                        }

                        is Result.Success -> {
                            swipeRefresh.isRefreshing = false
                            showList()
                            adapter.submitList(result.data.data)
                        }

                        is Result.Error -> {
                            swipeRefresh.isRefreshing = false
                            hideLoading()

                            if (result.message == "NO_DATA") {
                                showNoInternet()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        textNoInternet.visibility = View.GONE
    }

    private fun showList() {
        progressBar.visibility = View.GONE
        textNoInternet.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showNoInternet() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        textNoInternet.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
