package bitc.example.app.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ExpenseLogDTO(
  @SerializedName("expenseLogSeq")
  var expenseLogSeq: Int? = null,
  @SerializedName("memberId")
  var memberId: String? = null,
  @SerializedName("expense")
  var expenseMoney: String? = null,
  @SerializedName("expenseDate")
  var expenseDate: String? = null,
  @SerializedName("expenseCate")
  var expenseCate: String? = null,
  @SerializedName("expenseMemo")
  var expenseMemo: String? = null,
  @SerializedName("paymentOption")
  var paymentOption: String? = null,
  @SerializedName("expenseUse")
  var expenseUse: String? = null
)
