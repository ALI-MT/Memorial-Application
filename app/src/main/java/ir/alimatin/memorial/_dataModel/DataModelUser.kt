package ir.alimatin.memorial._dataModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataModelUser {
    @SerializedName("_id")
    @Expose
    private val id: String? = null

    @SerializedName("firstName")
    @Expose
    private val firstName: String? = null

    @SerializedName("lastName")
    @Expose
    private val lastName: String? = null

    @SerializedName("userName")
    @Expose
    private val userName: String? = null

    @SerializedName("passWord")
    @Expose
    private val passWord: String? = null

    @SerializedName("phone")
    @Expose
    private val phone: String? = null

    @SerializedName("bio")
    @Expose
    private val bio: String? = null

    @SerializedName("avatar")
    @Expose
    private val avatar: String? = null

    @SerializedName("lastIp")
    @Expose
    private val lastIp: String? = null

    @SerializedName("email")
    @Expose
    private val email: String? = null

    @SerializedName("dateCreated")
    @Expose
    private val dateCreated: String? = null

    @SerializedName("__v")
    @Expose
    private val v: Int? = null
}