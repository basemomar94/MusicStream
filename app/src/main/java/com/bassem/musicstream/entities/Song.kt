package com.bassem.musicstream.entities

import android.net.Uri

data class Song(
    var name: String = "",
    var audioLink: String = "",
    var coverLink: String = "",
    var description: String = ""
)
