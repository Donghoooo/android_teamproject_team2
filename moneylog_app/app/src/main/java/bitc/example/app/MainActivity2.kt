package bitc.example.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.adapter.DayListAdapter
import bitc.example.app.databinding.ActivityMain2Binding
import bitc.example.app.dto.DailySummaryDTO
import bitc.example.app.dto.MonthlySummaryDTO
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.model.SearchListItem
import bitc.example.app.sagmin.AddInfoActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.sdh.MyPageCheckActivity
import bitc.example.app.ui.CateSearchActivity
import bitc.example.app.ui.DayDecorator
import bitc.example.app.ui.SelectedDateDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarDay.today
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
  private val binding: ActivityMain2Binding by lazy {
    ActivityMain2Binding.inflate(layoutInflater)
  }

  private lateinit var memberId: String
  private lateinit var selectedDateDecorator: SelectedDateDecorator // 날짜 선택시 뜨는 원형
  private lateinit var dayDecorator: DayDecorator // 날짜마다 수입지출 금액
  private lateinit var calendarView: MaterialCalendarView
  private lateinit var tvDate: TextView // 날짜 선택시 리사이클러 뷰 위에 표시할 해당 날짜
  private lateinit var tvTotalIncome: TextView  // 선택한 날짜의 총 수입
  private lateinit var tvTotalExpense: TextView // 선택한 날짜의 총 지출
  private lateinit var tvMonthTotalIncome: TextView // 해당 월의 총 수입
  private lateinit var tvMonthTotalExpense: TextView  // 해당 월의 총 지출
  private lateinit var mainDayList: RecyclerView  // 날짜 선택했을 때 하단에 뜨는 리스트 리사이클러 뷰
  private lateinit var adapter: DayListAdapter  // 리스트 리사이클러뷰에 사용할 어댑터
  private val searchItemList = mutableListOf<SearchListItem>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    // SharedPreferences에서 memberId 가져오기
    val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
    val memberId = sharedPreferences.getString("memberId", null)

    if (memberId != null) {
      Log.d("MainActivity2", "로그인한 사용자 ID: $memberId")
      fetchMonthlyData(memberId, today().year, today().month + 1)  // 사용자 아이디를 이용해 데이터 가져오기
    } else {
      Log.e("MainActivity2", "사용자 ID를 불러오지 못함")
    }
    calendarView = binding.calendarView
    tvDate = binding.tvDate
    tvTotalIncome = binding.tvTotalIncome
    tvTotalExpense = binding.tvTotalExpense
    mainDayList = binding.mainDayList
    tvMonthTotalIncome = binding.tvMonthTotalIncome
    tvMonthTotalExpense = binding.tvMonthTotalExpense

    adapter = DayListAdapter(searchItemList)
    mainDayList.layoutManager = LinearLayoutManager(this)
    mainDayList.adapter = adapter

// context를 전달하여 SelectedDateDecorator 초기화
    selectedDateDecorator = SelectedDateDecorator(this)
    calendarView.addDecorator(selectedDateDecorator)


    binding.btnAdd.setOnClickListener {
      val date = tvDate.text.toString()
      val intent = Intent(this,AddInfoActivity::class.java).apply{
        putExtra("tvdate",date)
      }
      startActivity(intent)
    }

    // 오늘 날짜 가져오기
    val today = CalendarDay.today()
    tvDate.text = "${today.year}.${today.month + 1}.${String.format("%02d", today.day)}"
    calendarView.setSelectedDate(today)  // 앱 처음 로딩시 오늘 날짜 선택
    selectedDateDecorator.setSelectedDay(today)
    calendarView.invalidateDecorators()

    val currentYear = today.year
    val currentMonth = today.month + 1  // `CalendarDay`의 월은 0부터 시작 → `+1`
    val currentDay = today.day

    // ✅ 해당 월의 데이터 불러오기 (오늘 날짜 기준)
    fetchMonthlyData(memberId.toString(), currentYear, currentMonth)
    // 오늘 날짜에 대한 데이터 가져오기 및 UI 업데이트(앱 처음 로딩 시 오늘 날짜 기준 정보 출력)
    fetchDailyData(memberId.toString(), currentYear, currentMonth, currentDay)

// 달력에서 달이 변경될 때마다 해당 월의 수입/지출 업데이트
    calendarView.setOnMonthChangedListener { widget, date ->
      val selectedYear = date.year
      val selectedMonth = date.month + 1  // CalendarDay는 0부터 시작하므로 +1 필요
      fetchMonthlyData(memberId.toString(), selectedYear, selectedMonth)
    }

    // 캘린더 날짜 선택 시 리스트 업데이트
    calendarView.setOnDateChangedListener { widget, date, selected ->
      if(selected) {
        val selectedYear = date.year
        val selectedMonth = date.month + 1  // `CalendarDay`는 0부터 시작하는 월 사용 → `+1` 필요
        val selectedDay = date.day

        // 날짜 선택 시 선택된 날짜를 decorator에 반영
        selectedDateDecorator.setSelectedDay(date)
        calendarView.invalidateDecorators()  // 데코레이터 갱신

        // UI에 선택한 날짜 표시
        tvDate.text = "$selectedYear.$selectedMonth.${String.format("%02d", selectedDay)}"

        Log.d("SelectedDate", "선택된 날짜: ${date.year}-${date.month + 1}-${date.day}")
////         해당 날짜의 총 수입/지출을 불러와서 UI 업데이트
//        val monthTotalIncome = ""
//        tvMonthTotalIncome.text = "수입: ${monthTotalIncome}원"
//        val monthTotalExpense = ""
//        tvMonthTotalExpense.text = "지출: ${monthTotalExpense}원"

        // 해당 날짜의 지출 및 수입 리스트 업데이트 (리사이클러뷰)
        adapter.updateData(searchItemList)
        fetchDailyData(memberId.toString(), selectedYear, selectedMonth, selectedDay)
      }
    }


    binding.calendarIcon.setOnClickListener{}

    binding.chartIcon.setOnClickListener {  val intent = Intent(this, Analyze_List::class.java)
      startActivity(intent) }

    binding.userIcon.setOnClickListener { val intent = Intent(this, MyPageActivity::class.java)
      startActivity(intent)  }

    binding.listIcon.setOnClickListener {
      val intent = Intent(this, MonthlyListActivity::class.java)
      startActivity(intent)
    }

    binding.searchIcone.setOnClickListener {
      val intent = Intent(this, CateSearchActivity::class.java)
      startActivity(intent)
    }

  }


  private fun fetchMonthlyData(memberId: String, year: Int, month: Int) {
// 서버에서 해당 월의 총 수입, 지출 및 날짜별 데이터 가져오기
    AppServerClass.instance.getMonthlySummary(memberId, year, month)
      .enqueue(object : Callback<MonthlySummaryDTO> {
        override fun onResponse(
          call: Call<MonthlySummaryDTO>,
          response: Response<MonthlySummaryDTO>
        ) {
          if (response.isSuccessful) {
            val summary = response.body()
            summary?.let {
              Log.d("fetchMonthlyData", "서버 응답: ${it.dailySummary}")
              // 월 총 수입/지출 출력
              tvMonthTotalIncome.text = "수입: ${it.totalIncome} 원"
              tvMonthTotalExpense.text = "지출: ${it.totalExpense} 원"

              // 캘린더 업데이트
              updateCalendarWithIncomeExpense(it.dailySummary)

              // 현재 선택된 날짜가 있으면, 해당 날짜의 총 수입/지출을 업데이트
              val selectedDate = calendarView.selectedDate
              selectedDate?.let { date ->
                updateDailyTotal(date, it.dailySummary)
              }
            }
          }
        }
        override fun onFailure(call: Call<MonthlySummaryDTO>, t: Throwable) {
          Toast.makeText(this@MainActivity2, "데이터 불러오기 실패", Toast.LENGTH_SHORT).show()
        }
      })
  }

  // 선택한 날짜의 총 수입/지출 업데이트 함수 추가
  private fun updateDailyTotal(date: CalendarDay, dailySummary: List<DailySummaryDTO>) {
    val selectedDate = "${date.year}-${String.format("%02d", date.month + 1)}-${String.format("%02d", date.day)}"

    // 해당 날짜의 데이터를 찾아서 UI 업데이트
    val summaryForDate = dailySummary.find {
      it.year == date.year && it.month == date.month + 1 && it.day == date.day
    }

    if (summaryForDate != null) {
      tvTotalIncome.text = "수입: ${summaryForDate.totalIncome} 원"
      tvTotalExpense.text = "지출: ${summaryForDate.totalExpense} 원"
    } else {
      tvTotalIncome.text = "수입: 원"
      tvTotalExpense.text = "지출: 원"
    }
  }

  private fun updateCalendarWithIncomeExpense(dailySummary: List<DailySummaryDTO>) {
    val incomeExpenseMap = mutableMapOf<CalendarDay, Pair<Int, Int>>()

    for (summary in dailySummary) {
      val date = CalendarDay.from(summary.year, summary.month, summary.day)
      incomeExpenseMap[date] = Pair(summary.totalIncome, summary.totalExpense)
    }
    // Decorator를 적용한 후 갱신 필요
    calendarView.addDecorator(DayDecorator(this, incomeExpenseMap))
    calendarView.invalidateDecorators()  // 데코레이터 즉시 반영
    Log.d("DayDecorator", "데코레이터 데이터: $incomeExpenseMap")
    }

  private fun fetchDailyData(memberId: String, year: Int, month: Int, day: Int){
    AppServerClass.instance.getDailySummary(memberId, year, month, day)
      .enqueue(object : Callback<DailySummaryDTO> {
        override fun onResponse(
          call: Call<DailySummaryDTO>,
          response: Response<DailySummaryDTO>
        ) {
          if (response.isSuccessful) {
            val summary = response.body()
            summary?.let {
              // 날짜별 총 수입/지출 UI 업데이트
              tvTotalIncome.text = "+ ${it.totalIncome} 원"
              tvTotalExpense.text = "- ${it.totalExpense} 원"

              // 해당 날짜의 거래 리스트 갱신
              searchItemList.clear()
              // 각 거래 항목에 날짜 추가
              it.transactions?.forEach { transaction ->
                // `SearchListItem`에 날짜 추가
                val selectedDate =
                  "${year}-${String.format("%02d", month)}-${String.format("%02d", day)}"
                transaction.date = selectedDate // 날짜를 추가

                searchItemList.add(transaction)
              }

              Log.d("DataCheck", "데이터 목록: $searchItemList")
              Log.d("fetchDailyData", "검색된 거래 리스트 크기: ${searchItemList.size}")
//              mainDayList.visibility = View.VISIBLE

              // 리스트가 비어 있어도 RecyclerView가 보이도록 설정
              if (searchItemList.isEmpty()) {
                mainDayList.visibility = View.VISIBLE  // RecyclerView 보이게 설정
              } else {
                mainDayList.visibility = View.VISIBLE
              }
//              if (searchItemList.isEmpty()) {
//                mainDayList.visibility = View.VISIBLE  // RecyclerView 보이게 설정
//              }
              // 데이터 갱신 적용
              adapter.updateData(searchItemList)
              Log.d("RecyclerView", "데이터가 갱신되었습니다.")

            }
          }
        }
        override fun onFailure(call: Call<DailySummaryDTO>, t: Throwable) {
          Toast.makeText(this@MainActivity2, "데이터 불러오기 실패", Toast.LENGTH_SHORT).show()
        }
      })
  }

}