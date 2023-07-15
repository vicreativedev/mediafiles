package com.mediafileshelper

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.mediafileshelper.models.PhotoFile
import java.io.File
import java.util.ArrayList

class PhotoFilesHelper(private val context: Context) : FilesHelper<PhotoFile>() {

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    override fun getFiles(): List<PhotoFile> {
        val photoFiles = ArrayList<PhotoFile>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DURATION,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.ARTIST,
            MediaStore.Images.Media.ALBUM,
            MediaStore.Images.Media.DATE_ADDED,
        )
        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val urlColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            val durationColumn = cursor.getColumnIndex(MediaStore.Images.Media.DURATION)
            val sizeColumn = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Images.Media.ARTIST)
            val albumColumn = cursor.getColumnIndex(MediaStore.Images.Media.ALBUM)
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
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                    val dataAdded = cursor.getLong(dataAddedColumn)
                    val photoFile = PhotoFile(
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
                    photoFiles.add(photoFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
        return photoFiles
    }
}