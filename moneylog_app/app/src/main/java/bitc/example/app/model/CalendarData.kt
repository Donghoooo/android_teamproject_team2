package bitc.example.app.model

data class CalendarData(
  val day: Int?,
  val isIncome: Boolean,
  val isExpense: Boolean,
  val isEmpty: Boolean = false
)

