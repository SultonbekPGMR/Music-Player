package com.sultonbek1547.musicplayer.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sultonbek1547.musicplayer.R
import com.sultonbek1547.musicplayer.adapter.RvAdapter
import com.sultonbek1547.musicplayer.adapter.RvClick
import com.sultonbek1547.musicplayer.databinding.FragmentHomeBinding
import com.sultonbek1547.musicplayer.util.Constants
import com.sultonbek1547.musicplayer.util.Constants.currentPosition
import com.sultonbek1547.musicplayer.util.Constants.isFirstTime
import com.sultonbek1547.musicplayer.util.Constants.musicList
import com.sultonbek1547.musicplayer.util.Constants.songNames


class HomeFragment : Fragment(), RvClick {


    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var rvAdapter: RvAdapter
    private var tempPosition = 0
    private lateinit var tempTextview: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mediaPlayer = MediaPlayer()
        handler = Handler(Looper.getMainLooper())
        binding.tvName.isSelected = true

        rvAdapter = RvAdapter(
            requireContext(),
            songNames,
            this
        )
        binding.myRv.adapter = rvAdapter

        /** returning from MusicFragment */
        if (!isFirstTime){
            mediaPlayer = Constants.mediaPlayer
            tempPosition = currentPosition
            binding.tvName.text = songNames[currentPosition] + "   " + songNames[currentPosition] + "   " + songNames[currentPosition] + "   "
            binding.musicSeekBar.max = mediaPlayer.duration
            binding.musicSeekBar.progress = mediaPlayer.currentPosition
            if (mediaPlayer.isPlaying){
                binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }else{
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
            }
            Handler().postDelayed({
               currentPosition = tempPosition
                rvAdapter.notifyItemChanged(tempPosition)
            },10)

        }


        /** default song */
        if (isFirstTime) {
            isFirstTime = false
            Handler().postDelayed({
                mediaPlayer.setDataSource(requireContext(), musicList[0])
                mediaPlayer.prepare()
                Toast.makeText(context, "isFirst", Toast.LENGTH_SHORT).show()
                binding.tvName.text =
                    songNames[0] + "   " + songNames[0] + "   " + songNames[0] + "   "
                currentPosition = tempPosition
                rvAdapter.notifyItemChanged(0)
                binding.musicSeekBar.max = mediaPlayer.duration
                handler.postDelayed(changeSeekBar, 1)
            }, 1000)

        }

        /** starting || stopping song */
        binding.btnStart.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
            } else {
                mediaPlayer.start()
                binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }
        }

        /** next || previous music */
        binding.apply {
            btnNext.setOnClickListener {
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
                tempTextview.setTextColor(Color.BLACK)
                if (tempPosition < musicList.size - 1) {
                    tempPosition++
                } else {
                    tempPosition = 0
                }
                currentPosition = tempPosition
                rvAdapter.notifyItemChanged(tempPosition)
                mediaPlayer.reset()
                mediaPlayer.setDataSource(requireContext(), musicList[tempPosition])
                mediaPlayer.prepare()
                mediaPlayer.start()


                binding.tvName.text =
                    songNames[tempPosition] + "   " + songNames[tempPosition] + "   " + songNames[tempPosition] + "   "
                Handler().postDelayed({
                    binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)

                }, 150)

            }
            btnPrevious.setOnClickListener {
                binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
                tempTextview.setTextColor(Color.BLACK)

                if (tempPosition > 0) {
                    tempPosition--
                } else {
                    tempPosition = musicList.size - 1
                }
                currentPosition = tempPosition
                rvAdapter.notifyItemChanged(tempPosition)
                mediaPlayer.reset()
                mediaPlayer.setDataSource(requireContext(), musicList[tempPosition])
                mediaPlayer.prepare()
                mediaPlayer.start()


                binding.tvName.text =
                    songNames[tempPosition] + "   " + songNames[tempPosition] + "   " + songNames[tempPosition] + "   "
                Handler().postDelayed({
                    binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)

                }, 150)

            }


        }



        /** getting music from device */
        binding.toolbar.setOnMenuItemClickListener {

            true
        }

        /** navigating to MusicFragment */
        binding.btnToMusic.setOnClickListener {
            Constants.mediaPlayer = mediaPlayer
            currentPosition = tempPosition
            findNavController().navigate(R.id.musicFragment)
        }

        /** disabling seekbar */
        binding.musicSeekBar.setOnTouchListener { _, _ -> true }

        return binding.root

    }

    override fun itemClicked(position: Int, textView: TextView, musicName: String) {

        if (mediaPlayer.isPlaying && position == tempPosition) {
            mediaPlayer.seekTo(0)
            binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
            Handler().postDelayed({
                binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }, 100)
            binding.tvName.text =
                songNames[position] + "   " + songNames[position] + "   " + songNames[position] + "   "


        } else if (!mediaPlayer.isPlaying && position == tempPosition) {
            mediaPlayer.start()
            binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            mediaPlayer.seekTo(0)
            binding.tvName.text =
                songNames[position] + "   " + songNames[position] + "   " + songNames[position] + "   "


        } else {
            binding.btnStart.setImageResource(R.drawable.baseline_play_circle_outline_24)
            mediaPlayer.reset()
            mediaPlayer.setDataSource(requireContext(), musicList[position])
            mediaPlayer.prepare()
            mediaPlayer.start()
            tempTextview.setTextColor(Color.BLACK)
            textView.setTextColor(Color.parseColor("#0097FF"))
            tempPosition = position
            tempTextview = textView
            /**bottom view*/
            Handler().postDelayed({
                binding.btnStart.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            }, 150)
            binding.tvName.text =
                songNames[position] + "   " + songNames[position] + "   " + songNames[position] + "   "


        }
    }

    override fun updateVariable(textView: TextView) {
        tempTextview = textView
        tempTextview.setTextColor(Color.parseColor("#0097FF"))
    }

    private val changeSeekBar = object : Runnable {
        override fun run() {
            binding.musicSeekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(this, 1000)
        }
    }


}