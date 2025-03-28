package bitc.example.app

import CalendarAdapter
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.adapter.ListAdapter
import bitc.example.app.databinding.ActivityMainBinding
import bitc.example.app.dto.MainListDTO
import bitc.example.app.dto.MemberDTO
import bitc.example.app.viewmodel.CalendarViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewModel: CalendarViewModel by viewModels()  // ViewModel을 가져옴
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    } // Binding 초기화
    private lateinit var sharedPreferences: SharedPreferences
    var year1: String = "2025"
    var month1: String = "03"
    var day1: String = "01"


    private var dataList: List<MainListDTO> = mutableListOf()
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // ViewBinding을 사용하여 레이아웃을 inflate
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//// SharedPreferences에서 memberId 가져오기
//        sharedPreferences = getSharedPreferences("memberInfo", Context.MODE_PRIVATE)
//        val memberId = sharedPreferences.getString("memberId", "test1") ?: "test1" // 기본값은 "test1"
//
//// MemberDTO 객체 생성
//        val member = MemberDTO().apply {
//            this.memberId = memberId // memberId 설정
//            // 날짜 포맷팅: year, month, day를 이용해 createDate 생성
//            val date = String.format("%04d-%02d-%02dT00:00:00", year1.toInt(), month1.toInt(), day1.toInt())
//            this.createDate = date // createDate 설정
//        }
//
//// API 호출 준비
//        val api = AppServerClass.instance // AppServerClass 인스턴스 호출
//        val call = api.mainList(member) // mainList API 호출, member 객체 전달
//        mainListFunc(call) // API 호출 후 데이터 처리



        // RecyclerView의 LayoutManager 설정
        binding.listRecycle.layoutManager = LinearLayoutManager(this)
        // ListAdapter 초기화 및 RecyclerView에 연결
        val listAdapter: ListAdapter = ListAdapter(mutableListOf())
        binding.listRecycle.adapter = listAdapter
        binding.listRecycle.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        // headerDate
        viewModel.headerDate.observe(this, Observer { date ->
            binding.tvHeader.text = date // headerDate TextView 업데이트
        })
        // scrollMonth를  tvScrollMonth에 표시
        viewModel.scrollMonth.observe(this, Observer { month ->
            binding.tvScrollMonth.text = month // scrollMonth TextView 업데이트
        })
        // calendarData를 RecyclerView에 달력 데이터 표시
        viewModel.calendarData.observe(this) { data ->
            binding.calendar.layoutManager = GridLayoutManager(this, 7) // 한 주에 7일
            binding.calendar.adapter = CalendarAdapter(data) { day, income, expense, month, year ->
                // 날짜 클릭 시 데이터 업데이트
                if (day != null) {
                    binding.scrollView.visibility = View.VISIBLE
                    binding.tvScrollDay.text = day.toString() // 날짜 업데이트
                    year1 = year.toString()
                    month1 = month.toString()
                    day1 = day.toString()
//
//                    binding.tvScrollIncome.text = "+ ${if (income == 1) "수입" else "0원"}"
//                    binding.tvScrollExpense.text = "- ${if (expense == 1) "지출" else "0원"}"
//                    // 해당 월을 업데이트
                    viewModel.scrollMonth.value = month.toString()
                }
            }
        }
        // 이전 월 버튼 클릭 시
        binding.btnPrevMonth.setOnClickListener {
            viewModel.changeMonth(-1)  // -1로 호출하여 한 달 전으로 변경
        }
        // 다음 월 버튼 클릭 시
        binding.btnNextMonth.setOnClickListener {
            viewModel.changeMonth(1)  // +1로 호출하여 한 달 후로 변경
        }
        // 이미지뷰 색상 필터 적용
//        val imageView: ImageView = binding.imageView
//        val colorFilter = PorterDuffColorFilter(
//            ContextCompat.getColor(this, R.color.btn_plus), PorterDuff.Mode.SRC_IN
//        )
//        imageView.setColorFilter(colorFilter)


        // WindowInsets 처리 (전체 화면을 위한 설정)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // mainListFunc 함수에서 데이터를 가져와 어댑터의 setData를 호출하는 부분
    private fun mainListFunc(call: Call<List<MainListDTO>>) {
        call.enqueue(object : Callback<List<MainListDTO>> {
            override fun onResponse(
                p0: Call<List<MainListDTO>>,
                res: Response<List<MainListDTO>>
            ) {
                if (res.isSuccessful) {
                    // 서버에서 전달받은 데이터를 dataList에 할당
                    val newDataList = res.body() ?: emptyList()  // null이 반환될 경우 빈 리스트로 초기화
                    // 어댑터에 새로운 데이터를 전달하여 RecyclerView 갱신
                    listAdapter.setData(newDataList)  // 데이터를 갱신
                    Log.d("fullstack503", "result : $newDataList")
                } else {
                    val errorBody = res.errorBody()?.string()
                    Log.e("fullstack503", "❌ 송신 실패 - HTTP 코드: ${res.code()}, 오류 메시지: $errorBody")
                }
            }

            override fun onFailure(p0: Call<List<MainListDTO>>, t: Throwable) {
                Log.d("fullstack503", "message : ${t.message}")
            }
        })
    }

}