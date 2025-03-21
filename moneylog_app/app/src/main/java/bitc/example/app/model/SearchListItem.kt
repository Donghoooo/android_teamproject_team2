package bitc.example.app.model


data class SearchListItem(
  val date: String, // 날짜
  val category: String, // 카테고리
  val detail: String, // 내역
  val method: String, // 지불방식
  val amount: String, // 금액
  val isExpense: Boolean  // 수입인지 지출인지
)
