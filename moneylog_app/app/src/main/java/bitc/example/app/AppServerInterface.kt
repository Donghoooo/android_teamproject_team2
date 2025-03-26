package bitc.example.app

import bitc.example.app.dto.SearchDTO
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.dto.MemberDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppServerInterface {
  @POST("signUp/process")
  fun postSignUp(@Body member: MemberDTO): Call<String>

  @POST("income/process")
  fun postIncome(@Body income : IncomeLogDTO): Call<String>

  @POST("/outcome/process")
  fun postOutcome(@Body outcome : ExpenseLogDTO) : Call<String>

//  검색 페이지
  @GET("search/process")
  fun getSearchList(
  @Query("category") cate: List<String>?,
  @Query("source") source: List<String>?,
  @Query("startDate") startDate: String,
  @Query("endDate") endDate: String
  ): Call<List<SearchDTO>>
}