package bitc.example.app.model

import java.time.Year

data class CalendarData(
  val day: Int?, // 날짜 (없을 경우 null)
  val isWeekend: Boolean, // 주말 여부
  val isIncome: Boolean,  // 수입 여부
  val isExpense: Boolean, // 지출 여부
  val year: Int,         // 연도
  val month: Int         // 월 (0-based)
)

