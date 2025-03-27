package bitc.example.app

import bitc.example.app.dto.ExpenseLogDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object AppServerClass {
  private val BASE_URL = "http://10.100.203.72:8080/"
  val instance: AppServerInterface by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(AppServerInterface::class.java)
  }

  // 날짜별 지출 데이터 가져오는 메서드
  fun getExpenseData(year: Int, month: Int, day: Int, callback: (List<ExpenseLogDTO>?) -> Unit) {
    val call = instance.getExpenseByDate(year, month, day)
    call.enqueue(object : retrofit2.Callback<List<ExpenseLogDTO>> {
      override fun onResponse(
        call: retrofit2.Call<List<ExpenseLogDTO>>,
        response: retrofit2.Response<List<ExpenseLogDTO>>
      ) {
        if (response.isSuccessful) {
          callback(response.body())
        }
        else {
          callback(null)  // 실패 시 null 반환
        }
      }

      override fun onFailure(call: retrofit2.Call<List<ExpenseLogDTO>>, t: Throwable) {
        callback(null)  // 네트워크 오류 시 null 반환
      }
    })
  }
}