package com.mediafileshelper.models

abstract class Bucket(val name: String, val mediaFiles: List<MediaFile>) {
    override fun toString(): String {
        return "Bucket(name='$name', mediaFiles=$mediaFiles)"
    }
}