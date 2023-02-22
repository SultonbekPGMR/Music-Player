package com.sultonbek1547.musicplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.musicplayer.databinding.ActivityMainBinding
import com.sultonbek1547.musicplayer.util.Constants
import com.sultonbek1547.musicplayer.util.Constants.musicList
import com.sultonbek1547.musicplayer.util.Constants.songNames

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        musicList.add(Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/worlds_apart"),)
        songNames.add("Worlds Apart")
        getAllSongs()
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

    private fun getAllSongs() {
        val selection =
            MediaStore.Audio.Media.IS_MUSIC + "!= 0" + " AND " + MediaStore.Audio.Media.MIME_TYPE + "='audio/mpeg'"

        val projection = arrayOf(
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA
        )

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                songNames.add(cursor.getString(0))
                musicList.add(Uri.parse(cursor.getString(1)))
            }
            cursor.close()
        }
    }
}