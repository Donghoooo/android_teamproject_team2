package bitc.example.app.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class IncomeLogDTO(
  @SerializedName("incomeLogSeq")
  var incomeLogSeq: Int? = null,
  @SerializedName("memberId")
  var memberId: String? = null,
  @SerializedName("incomeMoney")
  var incomeMoney: Int? = null,
  @SerializedName("incomeDate")
  var incomeDate: String? = null,
  @SerializedName("incomeCate")
  var incomeCate: String? = null,
  @SerializedName("incomeMemo")
  var incomeMemo: String? = null,
  @SerializedName("incomeSource")
  var incomeSource: String? = null,
  @SerializedName("incomeUse")
  var incomeUse: String? = null
)
