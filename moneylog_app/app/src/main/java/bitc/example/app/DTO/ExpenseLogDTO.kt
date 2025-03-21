package bitc.example.app.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ExpenseLogDTO(
  @SerializedName("expenseLogSeq")
  var expenseLogSeq: Int? = null,
  @SerializedName("memberId")
  var memberId: String? = null,
  @SerializedName("expense")
  var expense: Int? = null,
  @SerializedName("expenseDate")
  var expenseDate: LocalDateTime? = null,
  @SerializedName("expenseCate")
  var expenseCate: String? = null,
  @SerializedName("expenseMemo")
  var expenseMemo: String? = null,
  @SerializedName("expenseSource")
  var expenseSource: String? = null
)
