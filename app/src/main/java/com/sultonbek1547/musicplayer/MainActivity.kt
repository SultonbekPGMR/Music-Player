package com.sultonbek1547.musicplayer

import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.musicplayer.databinding.ActivityMainBinding
import com.sultonbek1547.musicplayer.util.Constants

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getMusicDurationList()


    }

    private fun getMusicDurationList() {
        val durationList = ArrayList<Int>()
        val mediaPlayer = MediaPlayer()
        AsyncTask.execute {
            for (i in Constants.musicList.indices) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(this, Constants.musicList[i])
                mediaPlayer.prepare()
                durationList.add(mediaPlayer.duration)
            }
        }

        Constants.musicDurationList = durationList
    }


}