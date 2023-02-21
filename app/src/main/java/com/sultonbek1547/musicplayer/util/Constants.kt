package com.sultonbek1547.musicplayer.util

import android.net.Uri

object Constants {

    val musicList = arrayOf(
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/song"),
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/clean_bandit"),
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/day_dream"),
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/mosaique"),
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/sing_it_back"),
        Uri.parse("android.resource://com.sultonbek1547.musicplayer/raw/car_music")

    )

    val songNames = arrayOf("Worlds Apart", "Clean Bandit", "Day Dream", "Mosaique", "Sing it back","Car Music")

    var isNextSong = false

    var musicDurationList = ArrayList<Int>()
}