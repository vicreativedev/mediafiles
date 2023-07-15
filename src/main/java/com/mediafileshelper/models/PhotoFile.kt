package com.mediafileshelper.models

class PhotoFile(
    id: Long,
    name: String,
    url: String,
    duration: Int,
    size: Int,
    artist: String,
    album: String,
    folder: String,
    date: Long,
    uri: String
) : MediaFile(id, name, url, duration, size, artist, album ,folder, date, uri)