package bitc.example.app.dto

import com.google.gson.annotations.SerializedName

data class SearchDTO (
  @SerializedName("type")
  val type: String,         // 수입(income) 또는 지출(expense)
  @SerializedName("seq")
  val seq: Int,              // 거래 ID
  @SerializedName("category")
  val category: String,     // 카테고리
  @SerializedName("source")
  val source: String,     // 결제 수단
  @SerializedName("money")
  val money: Int,          // 금액
  @SerializedName("date")
  val date: String, // 거래 날짜
  @SerializedName("use")
  val use: String,// 내역
  @SerializedName("memberId")
  val memberId:String
)