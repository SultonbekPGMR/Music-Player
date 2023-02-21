package com.sultonbek1547.musicplayer.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.musicplayer.R
import com.sultonbek1547.musicplayer.databinding.FragmentHomeBinding
import com.sultonbek1547.musicplayer.databinding.FragmentMusicBinding


class MusicFragment : Fragment() {

    private val binding by lazy { FragmentMusicBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.song)
        binding.seekBar.max = mediaPlayer.duration
        handler = Handler(Looper.getMainLooper())

        /** tv_max */
        binding.tvMax.text =
            "${mediaPlayer.duration / 1000 / 60}:${mediaPlayer.duration / 1000 % 60}"


        /** setting max volume */
     /*   val audioManager = requireActivity().getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)*/


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
//        binding.btnStart.setOnClickListener {
//            if (mediaPlayer.isPlaying) {
//                mediaPlayer.pause()
//                binding.btnStart.text = "Play"
//            } else {
//                mediaPlayer.start()
//                binding.btnStart.text = "Pause"
//            }
//        }

        /** downloading music from internet */


        /** changing music using seekBar */
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        return binding.root}

}