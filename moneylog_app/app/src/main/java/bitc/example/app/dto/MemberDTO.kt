package bitc.example.app.dto

import com.google.gson.annotations.SerializedName

data class MemberDTO(
  @SerializedName("memberSeq")
  var memberSeq: Int? = null,
  @SerializedName("memberId")
  var memberId: String? = null,
  @SerializedName("memberPw")
  var memberPw: String? = null,
  @SerializedName("memberName")
  var memberName: String? = null,
  @SerializedName("createDate")
  var createDate: String? = null,
  @SerializedName("updateDate")
  var updateDate: String? = null,
  @SerializedName("memberEmail")
  var memberEmail: String? = null
)