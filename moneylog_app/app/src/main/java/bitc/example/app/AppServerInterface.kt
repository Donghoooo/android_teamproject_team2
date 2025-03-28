package bitc.example.app

import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.dto.MemberDTO
import bitc.example.app.dto.SearchDTO
import bitc.example.app.dto.ExpenseLogDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppServerInterface {
  @POST("signUp/process")
  fun postSignUp(@Body member: MemberDTO): Call<String>

  @POST("logIn/Process")
  fun postLogIn(@Body member: MemberDTO): Call<Boolean>

  @GET("isMemberId")
  fun isMemberId(@Query("Id") Id: String): Call<Boolean>

  @GET("isMemberName")
  fun isMemberName(@Query("Name") Name: String): Call<Boolean>

  @POST("memberInfo")
  fun memberInfo(@Body member: MemberDTO): Call<MemberDTO>

  @POST("memberDelete")
  fun memberDelete(@Query("Id") Id: String): Call<Void>

  @POST("memberUpdate")
  fun memberUpdate(@Body member: MemberDTO): Call<MemberDTO>

  //  수입 저장
  @POST("income/process")
  fun postIncome(@Body income: IncomeLogDTO): Call<String>

  //  수입 수정
  @POST("income/update")
  fun updateIncome(@Body incomeLog: IncomeLogDTO): Call<Int>

  // 수입 삭제
  @DELETE("income/delete")
  fun deleteIncome(@Query("incomeLogSeq") incomeLogSeq: Int): Call<Int>

  // 지출 저장
  @POST("/outcome/process")
  fun postOutcome(@Body outcome: ExpenseLogDTO): Call<String>

  // 지출 수정
  @POST("/outcome/update")
  fun updateOutcome(@Body outcomeLog: ExpenseLogDTO): Call<Int>

  //지출 삭제
  @DELETE("outcome/delete")
  fun deleteOutcome(@Query("outcomeLogSeq") outcomeLogSeq: Int): Call<Int>

  //  검색 페이지
  @GET("search/process")
  fun getSearchList(
    @Query("category") cate: List<String>?, // 카테고리
    @Query("source") source: List<String>?, // 자산방식
    @Query("startDate") startDate: String,  // 시작날짜
    @Query("endDate") endDate: String,  // 끝 날짜
    @Query("keyword") keyword: String?,  // 키워드
    @Query("sortBy") sortBy: String // 정렬 기준
  ): Call<List<SearchDTO>>

  //  수입/지출 리스트
  @GET("/list/income")
  fun getIncomeList(@Query("memberId")memberId : String): Call<List<IncomeLogDTO>>

  @GET("/list/expense")
  fun getExpenseList(@Query("memberId")memberId : String): Call<List<ExpenseLogDTO>>
//
//  @GET("/analyze")
//  fun getanalyze(@Body member: MemberDTO): Call<List<IncomeLogDTO>>

  @GET("/analyze")
  fun getanalyze(@Query("startDate")startDate : String, @Query("endDate")endDate : String, @Query("memberId")memberId : String): Call<List<IncomeLogDTO>>


  @GET("/analyze1")
  fun getanalyze1(@Query("startDate")startDate : String, @Query("endDate")endDate : String, @Query("memberId")memberId : String): Call<List<ExpenseLogDTO>>

//
//  @GET("/getTimeData")
//  fun getTimeData(@Path("timeStart") timeStart: String, @Path("timeEnd") timeEnd: String): Call<List<IncomeLogDTO>>


}