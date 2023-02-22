package com.sultonbek1547.musicplayer.util

import android.media.MediaPlayer
import android.net.Uri
import android.widget.TextView
import java.awt.font.TextAttribute

object Constants {


    var musicList = ArrayList<Uri>()

    var mediaPlayer = MediaPlayer()
    var currentPosition = 0
    var isFirstTime = true

    val songNames = ArrayList<String>()

    var musicDurationList = ArrayList<Int>()
}