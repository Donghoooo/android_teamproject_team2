package bitc.example.app

import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.dto.MemberDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppServerInterface {
  @POST("signUp/process")
  fun postSignUp(@Body member: MemberDTO): Call<String>

  @POST("income/Process")
  fun postIncome(@Body income : IncomeLogDTO): Call<String>
}