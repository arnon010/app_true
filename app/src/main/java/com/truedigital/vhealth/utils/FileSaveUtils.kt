package com.truedigital.vhealth.utils

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import com.truedigital.vhealth.R
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileSaveUtils constructor(
        private val context: Context
) {

    companion object {

        private const val MAIN_DIR = "vhealth"
        private const val IMAGE_QUAILITY = 90
    }

    fun storeToGallery(view: View) {
        storeToGallery(getViewShot(view))
    }

    fun storeToGallery(bitmap: Bitmap) {
        val currentDate = Date()
        val fileName = createFileName(currentDate)
        val storeFile = createStoreFile(fileName)
        val savedFile = writeImageToFile(bitmap, storeFile)

        savedFile?.let {
            storeToGallery(
                    file = it,
                    bitmap = bitmap,
                    title = fileName,
                    timestamp = currentDate.time
            )
        }
    }

    private fun getViewShot(screenView: View): Bitmap {
        val widthPixels = Resources.getSystem().displayMetrics.widthPixels
                .also {
                    Timber.d("width: $it")
                }
        val heightPixels = Resources.getSystem().displayMetrics.heightPixels
                .also {
                    Timber.d("height: $it")
                }

        val view = if (screenView is ScrollView) {
            screenView.measure(
                    View.MeasureSpec.makeMeasureSpec(widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            screenView.getChildAt(0)
        } else {
            screenView.measure(
                    View.MeasureSpec.makeMeasureSpec(widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(heightPixels, View.MeasureSpec.EXACTLY)
            )
            screenView
        }

        val width = view.measuredWidth
        val height = view.measuredHeight

        val bitmapImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapImage)
        canvas.drawColor(ContextCompat.getColor(context, R.color.white))
        screenView.layout(0, 0, width, height)
        screenView.draw(canvas)
        return bitmapImage
    }

    private fun storeToGallery(
            file: File,
            bitmap: Bitmap,
            title: String,
            timestamp: Long
    ): Uri? {
        val contentValues = ContentValues()
                .apply {
                    put(MediaStore.Images.Media.TITLE, title)
                    put(MediaStore.Images.Media.DISPLAY_NAME, title)
                    put(MediaStore.Images.Media.DESCRIPTION, "")
                    put(MediaStore.Images.Media.DATE_ADDED, timestamp)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.SIZE, file.length())
                    put(MediaStore.Images.Media.DATA, file.absolutePath)
                }

        var collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$MAIN_DIR")
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1)
            collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }

        val contentResolver = context.contentResolver
        val galleryFileUri = contentResolver.insert(collection, contentValues)

        galleryFileUri?.let { uri ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver
                        .openOutputStream(uri)
                        .use {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUAILITY, it)
                        }
                contentResolver
                        .openFileDescriptor(uri, "rw")
                        ?.use {
                            ExifInterface(it.fileDescriptor).saveAttributes()
                        }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, contentValues, null, null)
            }
        }

        return galleryFileUri
    }

    private fun createFileName(date: Date): String =
            SimpleDateFormat("yyyy_MM_dd HH:mm:ss", Locale.getDefault())
                    .let { "${MAIN_DIR}_${it.format(date)}" }

    private fun getDirectoryPicture(): File? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            else
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    private fun getPublicDirectoryName(): File =
            File(getDirectoryPicture(), MAIN_DIR)
                    .let { file ->
                        if (!file.exists()) {
                            file.mkdir()
                        }
                        file
                    }

    private fun createStoreFile(fileName: String, type: String = "jpg"): File {
        val parent = getPublicDirectoryName().absolutePath
        val child = "${fileName.replace(":", "_")}.$type"
        return File(parent, child)
    }

    private fun writeImageToFile(
            bitmap: Bitmap,
            saveFile: File,
            format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    ): File? {
        return try {
            saveFile.createNewFile()
            val out = FileOutputStream(saveFile)
            bitmap.compress(format, IMAGE_QUAILITY, out)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                bitmap.recycle()
            }
            out.flush()
            out.close()
            saveFile
        } catch (e: Exception) {
            throw Error(e.message)
        }
    }
}
