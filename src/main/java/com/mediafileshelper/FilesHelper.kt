package com.mediafileshelper

import com.mediafileshelper.models.ArtistBucket
import com.mediafileshelper.models.FolderBucket
import com.mediafileshelper.models.MediaFile
import java.io.File
import java.lang.Exception

abstract class FilesHelper<T> {

    abstract fun getFiles(): List<T>

    fun getArtists(mediaFiles: List<MediaFile>): List<ArtistBucket> {
        val artistBucket = ArrayList<ArtistBucket>()
        val artistsSet = HashSet<String>()
        artistsSet.addAll(mediaFiles.map { it.artist })
        artistsSet.forEach { artist ->
            artistBucket.add(ArtistBucket(artist, mediaFiles.filter { it.artist == artist }))
        }
        return artistBucket
    }

    fun getFolders(mediaFiles: List<MediaFile>): List<FolderBucket> {
        val folderBucket = ArrayList<FolderBucket>()
        val folderBucketSet = HashSet<String>()
        folderBucketSet.addAll(mediaFiles.map { it.folder })
        folderBucketSet.forEach { folder ->
            try {
                folderBucket.add(FolderBucket(folder.replace((File(folder).parent as String) + "/", ""), mediaFiles.filter { it.folder == folder }))
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return folderBucket
    }

}