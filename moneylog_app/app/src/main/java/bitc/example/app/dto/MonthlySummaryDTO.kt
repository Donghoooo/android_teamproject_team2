package bitc.example.app.dto

import com.google.gson.annotations.SerializedName


data class MonthlySummaryDTO (
  @SerializedName("totalIncome")
  val totalIncome: Int,  // 총 수입
  @SerializedName("totalExpense")
  val totalExpense: Int,  // 총 지출
  @SerializedName("dailySummary")
  val dailySummary: List<DailySummaryDTO> // 날짜별 요약 리스트
)