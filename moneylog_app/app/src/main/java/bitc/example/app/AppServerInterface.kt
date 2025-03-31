package bitc.example.app

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
  fun postIncome(@Body income: IncomeLogDTO): Call<String>

  @POST("/outcome/process")
  fun postOutcome(@Body outcome: ExpenseLogDTO): Call<String>


  //  메인 수입/지출 총합
  @GET("/main/income")
  fun getMainIncome(@Query("year")year : String, @Query("month")month : String, @Query("memberId")memberId : String) : Call<List<IncomeLogDTO>>
  @GET("/main/expense")
  fun getMainExpense(@Query("year")year : String, @Query("month")month : String, @Query("memberId")memberId : String) : Call<List<ExpenseLogDTO>>
}