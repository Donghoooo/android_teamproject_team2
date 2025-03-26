package bitc.example.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL = "http://10.100.203.69:8080/"  // 서버 URL

    private var retrofit: Retrofit? = null

    // Retrofit 인스턴스를 반환하는 메서드
    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)  // 서버 URL
                .addConverterFactory(GsonConverterFactory.create()) // Gson으로 JSON 변환
                .build()
        }
        return retrofit!!
    }
}