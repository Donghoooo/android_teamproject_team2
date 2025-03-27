package bitc.example.app

import CalendarAdapter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import bitc.example.app.databinding.ActivityMainBinding
import bitc.example.app.model.ListData
import bitc.example.app.viewmodel.CalendarViewModel

class MainActivity : AppCompatActivity() {
  private val viewModel: CalendarViewModel by viewModels()  // ViewModel을 가져옴
  private lateinit var binding: ActivityMainBinding  // Binding 초기화
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // ViewBinding을 사용하여 레이아웃을 inflate
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mainList = listOf(
      ListData("식비", "몰라몰라", "현금", 5000),
      ListData("식비", "몰라몰라", "현금", 5000),
      ListData("식비", "몰라몰라", "현금", 5000),
      ListData("식비", "몰라몰라", "현금", 5000),
      ListData("식비", "몰라몰라", "현금", 5000),
      ListData("식비", "몰라몰라", "현금", 5000)
    )
    // RecyclerView의 LayoutManager 설정
    //        binding.listRecycle.layoutManager = LinearLayoutManager(this)
    //
    //        // ListAdapter 초기화 및 RecyclerView에 연결
    //        val listAdapter = ListAdapter(mainList)
    //        binding.listRecycle.adapter = listAdapter  // 수정된 부분
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
      binding.calendar.adapter = CalendarAdapter(data) { day, income, expense, month ->
        // 날짜 클릭 시 데이터 업데이트
        if (day != null) {
          binding.scrollView.visibility = View.VISIBLE
          binding.tvScrollDay.text = day.toString() // 날짜 업데이트
          binding.tvScrollIncome.text = "+ ${if (income == 1) "수입" else "0원"}"
          binding.tvScrollExpense.text = "- ${if (expense == 1) "지출" else "0원"}"
          // 해당 월을 업데이트
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
    val imageView: ImageView = binding.imageView  // binding을 통해 이미지뷰 접근
    val colorFilter = PorterDuffColorFilter(
      ContextCompat.getColor(this, R.color.btn_plus), PorterDuff.Mode.SRC_IN
    )
    imageView.setColorFilter(colorFilter)
    // WindowInsets 처리 (전체 화면을 위한 설정)
    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }
}
//private lateinit var sharedPreferences: SharedPreferences
//override fun onCreate(savedInstanceState: Bundle?) {
//    sharedPreferences = getSharedPreferences("memberInfo", Context.MODE_PRIVATE)
//    val memberId = sharedPreferences.getString("memberId", "")
//    var member = MemberDTO()
//    member.memberId = memberId
//    // 달력에서 찍은 날짜를 createDate 에 넣으면 됨
//    // String 타입으로 2025-03-26T00:00 이런식으로 넣으면됨
//    member.createDate = null
//    val api = AppServerClass.instance
//    val call = api.mainList(member)
//    mainListFunc(call)
//}
//
//private fun mainListFunc(call: Call<List<MainListDTO>>) {
//    call.enqueue(object : Callback<List<MainListDTO>> {
//        override fun onResponse(p0: Call<List<MainListDTO>>, res: Response<List<MainListDTO>>) {
//            if (res.isSuccessful) {
//                // 서버에서 전달받은 데이터만 변수로 저장
//                val result = res.body()
//                Log.d("fullstack503", "result : $result")
//            }
//            else {
//                Log.d("fullstack503", "송신 실패")
//            }
//        }
//
//        override fun onFailure(p0: Call<List<MainListDTO>>, t: Throwable) {
//            Log.d("fullstack503", "message : $t.message")
//        }
//    })
//}
