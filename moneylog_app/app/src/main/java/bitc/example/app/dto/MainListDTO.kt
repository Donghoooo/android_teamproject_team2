package bitc.example.app.dto

import com.google.gson.annotations.SerializedName

data class MainListDTO(
    @SerializedName("cate")
    var cate: String? = null,
    @SerializedName("usee")
    var usee: String? = null,
    @SerializedName("way")
    var way: String? = null,
    @SerializedName("amount")
    var amount: Int? = null,
    @SerializedName("transactionDate")
    var transactionDate: String? = null,
    @SerializedName("type")
    var type: String? = null
)
