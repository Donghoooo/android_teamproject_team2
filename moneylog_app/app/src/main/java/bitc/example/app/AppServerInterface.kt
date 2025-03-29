package bitc.example.app

import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.dto.MainListDTO
import bitc.example.app.dto.MemberDTO
import bitc.example.app.dto.MonthSumDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServerInterface {
  @POST("signUp/process")
  fun postSignUp(@Body member: MemberDTO): Call<String>

  @POST("income/process")
  fun postIncome(@Body income: IncomeLogDTO): Call<String>

  @POST("/outcome/process")
  fun postOutcome(@Body outcome: ExpenseLogDTO): Call<String>

  @GET("outcome/getByDate/{year}/{month}/{day}")
  fun getMonthSum(
    @Path("year") year: Int,
    @Path("month") month: Int,
  ): MonthSumDTO  // 반환되는 데이터 타입

  //  메인 수입/지출 총합
  @GET("/main/income")
  fun getMainIncome(@Query("year")year : String, @Query("month")month : String, @Query("memberId")memberId : String) : Call<List<IncomeLogDTO>>
  @GET("/main/expense")
  fun getMainExpense(@Query("year")year : String, @Query("month")month : String, @Query("memberId")memberId : String) : Call<List<ExpenseLogDTO>>
}