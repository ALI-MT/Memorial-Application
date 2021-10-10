package ir.alimatin.memorial.utilities

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ProgressRequestBody(private val mFile: File, private val mListener: UploadCallbacks) :
    RequestBody() {
    private val mPath: String? = null
    override fun contentType(): MediaType? {
        // i want to upload only images
        return MediaType.parse("image/*")
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = FileInputStream(mFile)
        var uploaded: Long = 0
        try {
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (`in`.read(buffer).also { read = it } != -1) {

                // update progress on UI thread
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                handler.post(ProgressUpdater(uploaded, fileLength))
            }
        } finally {
            `in`.close()
        }
    }

    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()
    }

    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long) :
        Runnable {
        override fun run() {
            mListener.onProgressUpdate((100 * mUploaded / mTotal).toInt())
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}