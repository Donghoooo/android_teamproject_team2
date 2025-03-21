package bitc.example.app.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

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
  var createDate: LocalDateTime? = null,
  @SerializedName("updateDate")
  var updateDate: LocalDateTime? = null,
  @SerializedName("memberEmail")
  var memberEmail: String? = null
)