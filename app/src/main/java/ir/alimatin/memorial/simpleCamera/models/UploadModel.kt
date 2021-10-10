package ir.alimatin.memorial.simpleCamera.models

data class UploadModel(
    val `data`: String? = null,
    val encoding: String? = null,
    val md5: String? = null,
    val mimetype: String? = null,
    val name: String? = null,
    val size: Int? = null,
    val status: String? = null,
    val tempFilePath: String? = null,
    val truncated: Boolean? = null
)