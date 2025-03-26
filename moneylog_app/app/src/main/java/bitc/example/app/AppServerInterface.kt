package bitc.example.app

import bitc.example.app.dto.MemberDTO
import retrofit2.Call
import retrofit2.http.Body
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
}