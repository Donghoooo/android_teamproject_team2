package bitc.example.app.dto

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
  @SerializedName("categorySeq")
  var categorySeq: Int? = null,
  @SerializedName("categoryName")
  var categoryName: String? = null,
  @SerializedName("categoryIsDefault")
  var categoryIsDefault: Boolean? = null,
  @SerializedName("memberId")
  var memberId: String? = null
)
