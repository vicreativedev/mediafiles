package com.mediafileshelper

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.mediafileshelper.models.VideoFile
import java.io.File
import java.util.ArrayList

class VideoFilesHelper(private val context: Context) : FilesHelper<VideoFile>() {

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }

    override fun getFiles(): List<VideoFile> {
        val videoFiles = ArrayList<VideoFile>()

        //update test

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.ALBUM,
            MediaStore.Video.Media.DATE_ADDED,
        )
        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
            val urlColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            val durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndex(MediaStore.Video.Media.SIZE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Video.Media.ARTIST)
            val albumColumn = cursor.getColumnIndex(MediaStore.Video.Media.ALBUM)
            val dataAddedColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                try {
                    val id = cursor.getLong(idColumn)
                    val url = cursor.getString(urlColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)
                    val album = cursor.getString(albumColumn)
                    var artist = cursor.getString(artistColumn).toString()
                    if (artist == "<unknown>") {
                        artist = "Unknown"
                    }
                    val folder = url.replace("/${File(url).name}","")
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                    )
                    val dataAdded = cursor.getLong(dataAddedColumn)
                    val videoFile = VideoFile(
                        id,
                        name,
                        url,
                        duration,
                        size,
                        artist,
                        album,
                        folder,
                        dataAdded,
                        contentUri.toString()
                    )
                    videoFiles.add(videoFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
        return videoFiles
    }
}