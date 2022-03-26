package com.bassem.musicstream.entities

import android.net.Uri
import java.io.Serializable

data class Book(
    var name: String = "",
    var audioLink: String = "",
    var coverLink: String = "",
    var description: String = ""
) : Serializable
