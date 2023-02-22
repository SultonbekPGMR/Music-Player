package com.sultonbek1547.musicplayer.fragment

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sultonbek1547.musicplayer.R
import com.sultonbek1547.musicplayer.databinding.FragmentMusicBinding
import com.sultonbek1547.musicplayer.util.Constants
import com.sultonbek1547.musicplayer.util.Constants.currentPosition
import com.sultonbek1547.musicplayer.util.Constants.songNames


class MusicFragment : Fragment() {

    private val binding by lazy { FragmentMusicBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mediaPlayer = Constants.mediaPlayer
        binding.seekBar.max = mediaPlayer.duration
        binding.seekBar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)

            /**volume...*/
            val audioManager =
                requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            val maxVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val progress = (currentVolume.toFloat() / maxVolume * 100).toInt()
            binding.volumeSeekbar.progress = progress

        }
        binding.tvName.isSelected = true
        binding.tvName.text = songNames[currentPosition]

        handler = Handler(Looper.getMainLooper())

        /** tv progress */
        if (mediaPlayer.currentPosition / 1000 % 60 < 10) binding.tvCount.text =
            "${mediaPlayer.currentPosition / 1000 / 60}:0${mediaPlayer.currentPosition / 1000 % 60}"
        else binding.tvCount.text =
            "${mediaPlayer.currentPosition / 1000 / 60}:${mediaPlayer.currentPosition / 1000 % 60}"


        /** tv_max */
        binding.tvMax.text =
            "${mediaPlayer.duration / 1000 / 60}:${mediaPlayer.duration / 1000 % 60}"


        /** when Music ends */
        mediaPlayer.setOnCompletionListener {
            moveToNext()
        }
        /** dealing with volume */
        binding.volumeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val audioManager =
                    requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                val volume: Int = p1 * maxVolume / 100 // Scale the progress to the volume range
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })


        /** changing seekBar as music goes */
        handler.postDelayed(object : Runnable {
            override fun run() {
                binding.seekBar.progress = mediaPlayer.currentPosition
                if (mediaPlayer.currentPosition / 1000 % 60 < 10) binding.tvCount.text =
                    "${mediaPlayer.currentPosition / 1000 / 60}:0${mediaPlayer.currentPosition / 1000 % 60}"
                else binding.tvCount.text =
                    "${mediaPlayer.currentPosition / 1000 / 60}:${mediaPlayer.currentPosition / 1000 % 60}"

                handler.postDelayed(this, 1000)
            }
        }, 1000)


        /** starting || stopping song */
        binding.btnStart.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
                mediaPlayer.pause()
            } else {
                binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
                mediaPlayer.start()
            }
        }

        /** next || previous music */
        binding.apply {
            btnNext.setOnClickListener {
                moveToNext()
            }
            btnPrevious.setOnClickListener {
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)

                if (currentPosition > 0) {
                    currentPosition--
                } else {
                    currentPosition = Constants.musicList.size - 1
                }
                mediaPlayer.reset()
                mediaPlayer.setDataSource(requireContext(), Constants.musicList[currentPosition])
                mediaPlayer.prepare()
                mediaPlayer.start()
                binding.seekBar.progress = 0
                binding.tvName.text = songNames[currentPosition]
                binding.tvMax.text =
                    "${mediaPlayer.duration / 1000 / 60}:${mediaPlayer.duration / 1000 % 60}"

                Handler().postDelayed({
                    binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)

                }, 150)

            }


        }


        /** changing music using seekBar */
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer.seekTo(p1)
                    if (mediaPlayer.currentPosition / 1000 % 60 < 10) binding.tvCount.text =
                        "${mediaPlayer.currentPosition / 1000 / 60}:0${mediaPlayer.currentPosition / 1000 % 60}"
                    else binding.tvCount.text =
                        "${mediaPlayer.currentPosition / 1000 / 60}:${mediaPlayer.currentPosition / 1000 % 60}"

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })


        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }

    fun moveToNext() {
        binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
        if (currentPosition < Constants.musicList.size - 1) {
            currentPosition++
        } else {
            currentPosition = 0
        }
        mediaPlayer.reset()
        mediaPlayer.setDataSource(requireContext(), Constants.musicList[currentPosition])
        mediaPlayer.prepare()
        mediaPlayer.start()
        binding.seekBar.progress = 0
        binding.tvName.text = songNames[currentPosition]
        binding.tvMax.text =
            "${mediaPlayer.duration / 1000 / 60}:${mediaPlayer.duration / 1000 % 60}"
        Handler().postDelayed({
            binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)

        }, 150)

    }

}