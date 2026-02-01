package com.example.jikan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.jikan.model.AnimeData
import com.example.jikan.model.AnimeDetail
import com.example.jikan.viewmodel.AnimeListViewModel
import com.example.jikan.util.Result
import com.google.android.material.appbar.MaterialToolbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {

    private val viewModel: AnimeListViewModel by viewModels()
    private lateinit var anime: AnimeData

    private lateinit var posterImage: ImageView
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var titleText: TextView
    private lateinit var ratingText: TextView
    private lateinit var episodesText: TextView
    private lateinit var genresText: TextView
    private lateinit var synopsisText: TextView
    private lateinit var genresTextTitle: TextView
    private lateinit var synopsisTextTitle: TextView
    private lateinit var noDetailMessage: TextView
    private lateinit var loader: View
    private lateinit var contentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anime = requireArguments().getParcelable(ARG_ANIME)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<MaterialToolbar>(R.id.toolbar)
            .setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        bindViews(view)
        populateInitialData()
        observeDetailState()

        viewModel.fetchAnimeDetail(anime.id)
    }

    private fun bindViews(view: View) {
        posterImage = view.findViewById(R.id.posterImage)
        youtubePlayerView = view.findViewById(R.id.youtube_player_view)
        titleText = view.findViewById(R.id.titleText)
        ratingText = view.findViewById(R.id.ratingText)
        episodesText = view.findViewById(R.id.episodesText)
        genresText = view.findViewById(R.id.genresText)
        synopsisText = view.findViewById(R.id.synopsisText)
        genresTextTitle = view.findViewById(R.id.genresTextTitle)
        synopsisTextTitle = view.findViewById(R.id.synopsisTextTitle)
        noDetailMessage = view.findViewById(R.id.noDetailMessage)
        loader = view.findViewById(R.id.loader)
        contentContainer = view.findViewById(R.id.contentContainer)

        lifecycle.addObserver(youtubePlayerView)
    }

    private fun populateInitialData() {
        titleText.text = anime.title
        ratingText.text = "⭐ ${anime.rating ?: "N/A"}"
        episodesText.text = "Episodes: ${anime.numberOfEpisodes ?: "?"}"

        Glide.with(this)
            .load(anime.images?.jpg?.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(posterImage)
    }

    private fun observeDetailState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.animeDetailState.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            loader.visibility = View.VISIBLE
                            contentContainer.visibility = View.INVISIBLE
                        }

                        is Result.Success -> {
                            loader.visibility = View.GONE
                            contentContainer.visibility = View.VISIBLE
                            noDetailMessage.visibility = View.GONE
                            bindDetailData(result.data)
                        }

                        is Result.Error -> {
                            loader.visibility = View.GONE
                            contentContainer.visibility = View.VISIBLE

                            genresText.visibility = View.GONE
                            genresTextTitle.visibility = View.GONE
                            synopsisText.visibility = View.GONE
                            synopsisTextTitle.visibility = View.GONE

                            noDetailMessage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun bindDetailData(detail: AnimeDetail) {
        titleText.setOnClickListener {
            detail.url?.let { openInBrowser(it) }
        }

        val youtubeId = detail.trailer?.embedUrl
        if (!youtubeId.isNullOrEmpty()) {
            showTrailer(youtubeId)
        } else {
            showPoster()
        }

        if (detail.genres.isNullOrEmpty()) {
            genresText.visibility = View.GONE
            genresTextTitle.visibility = View.GONE
        } else {
            genresText.visibility = View.VISIBLE
            genresTextTitle.visibility = View.VISIBLE
            genresText.text = detail.genres.joinToString(", ") { it.name }
        }

        if (detail.synopsis.isNullOrBlank()) {
            synopsisText.visibility = View.GONE
            synopsisTextTitle.visibility = View.GONE
        } else {
            synopsisText.visibility = View.VISIBLE
            synopsisTextTitle.visibility = View.VISIBLE
            synopsisText.text = detail.synopsis
        }
    }

    private fun showTrailer(youtubeId: String) {
        posterImage.visibility = View.GONE
        youtubePlayerView.visibility = View.VISIBLE

        youtubePlayerView.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(youtubeId, 0f)
                }
            }
        )
    }

    private fun showPoster() {
        youtubePlayerView.visibility = View.GONE
        posterImage.visibility = View.VISIBLE
    }

    private fun openInBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    companion object {
        private const val ARG_ANIME = "arg_anime"

        fun newInstance(anime: AnimeData) =
            AnimeDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ANIME, anime)
                }
            }
    }
}
