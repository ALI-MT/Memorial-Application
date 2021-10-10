package ir.alimatin.memorial.model

data class CreatePostsModel(
    val __v: Int,
    val _id: String,
    val caption: String,
    val comment: Int,
    val dateCreated: String,
    val facebook: Int,
    val ghost: Int,
    val latitude: String,
    val location: String,
    val longitude: String,
    val medias: String,
    val privacy: Int,
    val rank: Int,
    val sendLater: String,
    val status: String,
    val telegram: Int,
    val twitter: Int,
    val user: String
)