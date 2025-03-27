package bitc.example.app.model


data class SearchListItem(
  val date: String, // 날짜
  val category: String, // 카테고리
  val use: String, // 내역
  val source: String, // 지불방식
  val money: String, // 금액
  val type: String // 수입인지 지출인지
)
