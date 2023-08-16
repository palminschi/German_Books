package com.palmdev.german_books.presentation.screens.videos.video_player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout.LayoutParams
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.nl.translate.Translator
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentVideoPlayerBinding
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toShortString
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.dialogs.save_word.SaveWordDialogFragment
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.LIMIT_VIDEOS_WATCH
import com.palmdev.german_books.utils.Languages
import com.palmdev.german_books.utils.Translate
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment :
    BaseFragment<FragmentVideoPlayerBinding>(FragmentVideoPlayerBinding::inflate) {

    private val viewModel: VideoPlayerViewModel by viewModels()
    private var mVideoId: String? = null
    private var mTranslator: Translator? = null
    private var mLastOriginalSubtitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mVideoId = it.getString(ARG_VIDEO_ID)
        }
    }

    override fun getScreenOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    override val isNavigationVisible: Boolean
        get() = false

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initControlButtons()
        binding.subtitlesProgress.visibility = View.VISIBLE

        binding.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.tvOriginalSubtitle.clearFocus()
        }

        binding.btnTranslate.setOnClickListener {
            val textView = binding.tvOriginalSubtitle
            if (textView.hasSelection()) {
                val selectedText = textView.text.subSequence(
                    textView.selectionStart, textView.selectionEnd
                ).toString()
                if (selectedText.isEmpty()) {
                    val snackbar = Snackbar.make(
                        binding.root,
                        getText(R.string.selectText),
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setTextColor(resources.getColor(R.color.red, null))
                    snackbar.setBackgroundTint(resources.getColor(R.color.night_background, null))
                    snackbar.show()
                } else {
                    findNavController().navigate(
                        R.id.saveWordDialogFragment, bundleOf(
                            SaveWordDialogFragment.ARG_TEXT_TO_TRANSLATE to selectedText
                        )
                    )
                }
            } else {
                val snackbar = Snackbar.make(
                    binding.root,
                    getText(R.string.selectText),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setTextColor(resources.getColor(R.color.red, null))
                snackbar.setBackgroundTint(resources.getColor(R.color.night_background, null))
                snackbar.show()
            }
        }

        Translate.createTranslator(
            sourceLanguage = Languages.learningLanguage.code,
            targetLanguage = viewModel.userLanguage.code
        ).addOnCompleteListener {
            mTranslator = Translate.getTranslator()
        }

        mVideoId?.let { videoId ->
            viewModel.initVideo(videoId)
            viewModel.video.observe(viewLifecycleOwner) { video ->
                if (video != null) {
                    initiateVideoPlayer(video)
                    binding.videoTitle.text = video.title
                    binding.tvViewsCount.text =
                        video.viewsCount.toShortString() + " " + getString(R.string.views)
                    binding.tvLikesCount.text =
                        video.likesCount.toShortString() + " " + getString(R.string.likes)


                    initRecommendedVideos(videoId = video.videoId)
                    initSubtitlesTranslation()
                    initSaving()
                } else {
                    stopPlayer()
                }


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            stopPlayer()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
        }
        viewModel.video.value?.let { viewModel.saveVideo(it) }
        viewModel.video.postValue(null)
    }


    private fun initSubtitlesTranslation() {
        viewModel.withDoubleSubtitles.observe(viewLifecycleOwner) { with ->
            binding.tvTranslatedSubtitle.visibility = if (with) View.VISIBLE else View.INVISIBLE
        }
        binding.btnSubtitles.setOnClickListener {
            viewModel.withDoubleSubtitles.value = !viewModel.withDoubleSubtitles.value!!
            binding.icBtnTranslator.animate().rotation(-360f).setDuration(300)
                .withEndAction {
                    binding.icBtnTranslator.animate().rotation(0f).setDuration(0).start()
                }
                .start()
        }
    }

    private fun setSubtitles(playerTimeMillis: Long) {
        val originalSubtitle = viewModel.originalSubtitles.value?.firstOrNull {
            it.startTime <= playerTimeMillis && it.endTime > playerTimeMillis
        }
        val translatedSubtitle = viewModel.translatedSubtitles.value?.firstOrNull {
            it.startTime <= playerTimeMillis && it.endTime > playerTimeMillis
        }

        originalSubtitle?.let {
            if (mLastOriginalSubtitle != it.text) {
                binding.tvOriginalSubtitle.text = originalSubtitle.text
                binding.tvTranslatedSubtitle.text = translatedSubtitle?.text ?: ""
                if (translatedSubtitle == null) {
                    mTranslator?.translate(it.text)?.addOnSuccessListener {
                        binding.tvTranslatedSubtitle.text = it
                    }
                }
                mLastOriginalSubtitle = it.text
            }
        }
    }

    private fun initControlButtons() {

        binding.playerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                binding.toggleBtnPlay.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        binding.tvOriginalSubtitle.clearFocus()
                        youTubePlayer.play()
                    } else youTubePlayer.pause()
                    viewModel.isPlaying = isChecked
                }
                binding.btnVideoForward.setOnClickListener {
                    it.animate()
                        .rotation(360f)
                        .setDuration(400)
                        .withEndAction {
                            it.animate().rotation(0f)
                                .setDuration(0)
                                .start()
                        }
                        .start()
                    viewModel.playerCurrentSeconds += 5f
                    setSubtitles(viewModel.playerCurrentSeconds.toLong())
                    youTubePlayer.seekTo(viewModel.playerCurrentSeconds)
                }
                binding.btnVideoBack.setOnClickListener {
                    it.animate()
                        .rotation(-360f)
                        .setDuration(400)
                        .withEndAction {
                            it.animate().rotation(0f)
                                .setDuration(0)
                                .start()
                        }
                        .start()
                    viewModel.playerCurrentSeconds -= 5f
                    setSubtitles(viewModel.playerCurrentSeconds.toLong())
                    youTubePlayer.seekTo(viewModel.playerCurrentSeconds)
                }
            }
        })

        binding.toggleBtnPlay.isChecked = viewModel.isPlaying
    }

    private fun initiateVideoPlayer(video: SavedVideo) {
        val options = IFramePlayerOptions.Builder()
            .ccLoadPolicy(1)
            .controls(0).build()

        val playerListener = object : AbstractYouTubePlayerListener() {

            @SuppressLint("ClickableViewAccessibility")
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val defaultPlayerUiController =
                    DefaultPlayerUiController(binding.playerView, youTubePlayer)
                defaultPlayerUiController.showYouTubeButton(false)
                defaultPlayerUiController.showVideoTitle(false)
                defaultPlayerUiController.showMenuButton(false)
                defaultPlayerUiController.showFullscreenButton(false)

                binding.playerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                youTubePlayer.loadVideo(video.videoId, viewModel.playerCurrentSeconds)
                youTubePlayer.play()
                viewModel.isPlaying = true

                if (this@VideoPlayerFragment.isAdded) {
                    binding.toggleBtnPlay.isChecked = true
                    binding.subtitlesProgress.visibility = View.GONE
                    setContentDimensions()

                    // Click on text
                    binding.tvOriginalSubtitle.setOnFocusChangeListener { v, hasFocus ->
                        if (hasFocus) {
                            viewModel.isPlaying = false
                            binding.toggleBtnPlay.isChecked = false
                            youTubePlayer.pause()
                        }
                    }
                    binding.tvOriginalSubtitle.setOnTouchListener { v, event ->
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            binding.tvOriginalSubtitle.requestFocus()
                        }
                        false
                    }
                }
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                viewModel.playerCurrentSeconds = second
                setSubtitles(second.toLong())
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.PLAYING) {
                    viewModel.isPlaying = true
                    binding.toggleBtnPlay.isChecked = true
                }
                if (state == PlayerConstants.PlayerState.PAUSED) {
                    viewModel.isPlaying = false
                    binding.toggleBtnPlay.isChecked = false
                }
                super.onStateChange(youTubePlayer, state)
            }
        }

        try {
            binding.playerView.initialize(playerListener, options)
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
        }

    }

    private fun stopPlayer() {
        if (binding.playerView.isFullScreen()) {
            binding.playerView.exitFullScreen()
        }
        binding.playerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
                viewModel.isPlaying = false
                binding.toggleBtnPlay.isChecked = false
            }
        })
    }

    private fun initRecommendedVideos(videoId: String) {

        viewModel.setRecommendedVideos(videoId = videoId)
        val adapter = RecommendedVideosAdapter(requireContext())
        binding.listRecommendedVideos.adapter = adapter

        viewModel.recommendedVideos.observe(viewLifecycleOwner) {
            adapter.addVideos(it)
        }

        binding.listRecommendedVideos.setOnItemClickListener { parent, _, position, _ ->
            viewModel.addNewWatchedVideo()
            if (
                !viewModel.isPremiumUser &&
                viewModel.watchedVideosToday.value!! >= LIMIT_VIDEOS_WATCH
            ) {
                findNavController().navigate(R.id.videosLimitDialogFragment)
            } else {
                val video = parent.getItemAtPosition(position) as VideoInfo
                findNavController().navigate(
                    R.id.videoPlayerFragment, bundleOf(
                        ARG_VIDEO_ID to video.videoId
                    )
                )
            }
        }
    }

    private fun initSaving() {
        binding.btnSave.setOnClickListener {
            viewModel.isFavorite.value = !viewModel.isFavorite.value!!
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            viewModel.video.value?.let { viewModel.updateVideoIsFavorite(it) }
            if (isFavorite) {
                binding.tvBtnSave.text = getString(R.string.saved)
                binding.icBtnSave.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_check,
                        null
                    )
                )
            } else {
                binding.tvBtnSave.text = getString(R.string.save)
                binding.icBtnSave.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_plus,
                        null
                    )
                )
            }
        }
    }

    private fun setContentDimensions() {
        val currentOrientation = requireActivity().resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            val playerHeight = binding.playerView.height
            val displayHeight = resources.displayMetrics.heightPixels
            val contentHeight =
                if (displayHeight - playerHeight < 450) 450
                else displayHeight - playerHeight
            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                contentHeight
            )
            binding.contentContainer.layoutParams = params
        } else if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            val displayHeight = resources.displayMetrics.heightPixels
            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                displayHeight
            )
            binding.contentContainer.layoutParams = params
        }

    }

    companion object {
        const val ARG_VIDEO_ID = "ARG_VIDEO_ID"
    }
}