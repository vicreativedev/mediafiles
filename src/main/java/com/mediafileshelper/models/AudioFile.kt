package com.mediafileshelper.models

class AudioFile(
    id: Long,
    name: String,
    url: String,
    duration: Int,
    size: Int,
    artist: String,
    album: String,
    folder: String,
    date: Long,
    uri: String,
    val albumId: Long
) : MediaFile(id, name, url, duration, size, artist, album ,folder, date, uri){
    override fun toString(): String {
        return "AudioFile(albumId=$albumId) ${super.toString()}"
    }
}