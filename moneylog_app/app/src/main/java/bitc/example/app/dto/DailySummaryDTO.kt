package bitc.example.app.dto

import bitc.example.app.model.SearchListItem
import com.google.gson.annotations.SerializedName

data class DailySummaryDTO (
  @SerializedName("memberId")
  val memberId: String,
  @SerializedName("year")
  val year: Int,
  @SerializedName("month")
  val month: Int,
  @SerializedName("day")
  val day: Int,
  @SerializedName("totalIncome")
  val totalIncome: Int,
  @SerializedName("totalExpense")
  val totalExpense: Int,
  @SerializedName("transactions")
  val transactions: List<SearchListItem>
)
