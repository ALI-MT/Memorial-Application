package ir.alimatin.memorial.utilities

import android.content.Context
import android.widget.Toast
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

object AppUtilities {

    fun requestBodyBuilder(
        imageMap: HashMap<String, File?> = hashMapOf(),
        uploadCallbacksListener: ProgressRequestBody.UploadCallbacks? = null
    ): RequestBody {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        imageMap.entries.forEach { (k, v) ->
            builder.addFormDataPart(
                k,
                v!!.name,
                ProgressRequestBody(v, uploadCallbacksListener!!)
            )
        }
        return builder.build()
    }

    fun Context.showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}