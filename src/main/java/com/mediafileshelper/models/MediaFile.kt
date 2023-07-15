package com.mediafileshelper.models

abstract class MediaFile(
    val id: Long,
    val name: String,
    val url: String,
    val duration: Int,
    val size: Int,
    val artist: String,
    val album: String,
    val folder: String,
    val date: Long,
    val uri: String,
){
    override fun toString(): String {
        return "MediaFile(id=$id, name='$name', url='$url', duration=$duration, size=$size, artist='$artist', album='$album', folder='$folder', date=$date, uri='$uri')"
    }
}