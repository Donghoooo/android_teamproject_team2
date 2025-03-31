package bitc.example.app

import CalendarAdapter
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import bitc.example.app.databinding.ActivityMainBinding
import bitc.example.app.model.CalendarData
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calendar = Calendar.getInstance() // 현재 날짜 가져오기
    private var calendarData: MutableList<CalendarData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // WindowInsets 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 초기 UI 세팅
        binding.tvHeader.text = getHeaderDate() // 년/월 표시
        binding.tvScrollMonth.text = getMonthOnly() // 월 표시


        // 달력 데이터 생성 및 표시
        generateCalendarData()
        setupCalendarAdapter()

        // 월 변경 버튼 이벤트
        binding.btnPrevMonth.setOnClickListener { changeMonth(-1) }
        binding.btnNextMonth.setOnClickListener { changeMonth(1) }
    }

    //  달력 데이터 생성 함수
    private fun generateCalendarData() {
        calendar.set(Calendar.DAY_OF_MONTH, 1) // 1일로 설정
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7

        loadIncomeData()
        loadExpenseData()

        calendarData.clear()

        // 첫 주 빈 칸 추가
        for (i in 0 until firstDayOfWeek) {
            calendarData.add(CalendarData(null, false, false, false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)))
        }

        // 날짜 추가
        for (day in 1..daysInMonth) {
            val isWeekend = isWeekend(day)
            calendarData.add(CalendarData(day, isWeekend, false, false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)))
        }

        binding.calendar.adapter?.notifyDataSetChanged()
    }

    //  주말 여부 확인
    private fun isWeekend(day: Int): Boolean {
        val tempCalendar = Calendar.getInstance()
        tempCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        tempCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        tempCalendar.set(Calendar.DAY_OF_MONTH, day)

        val dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
    }

    //  년/월 텍스트 반환
    private fun getHeaderDate(): String {
        return "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
    }

    //  월만 텍스트 반환
    private fun getMonthOnly(): String {
        return (calendar.get(Calendar.MONTH) + 1).toString()
    }

    //  이전/다음 달 변경
    private fun changeMonth(offset: Int) {
        calendar.add(Calendar.MONTH, offset)
        binding.tvHeader.text = getHeaderDate()
        binding.tvScrollMonth.text = getMonthOnly()
        generateCalendarData() // 데이터 갱신
        setupCalendarAdapter()
        loadIncomeData()
        loadExpenseData()
    }

    //  날짜 클릭 이벤트 처리
    private fun onDateClick(day: Int?, month: Int) {
        if (day != null) {
            binding.scrollView.visibility = View.VISIBLE
            binding.tvScrollDay.text = day.toString() // 날짜 업데이트
            binding.tvScrollMonth.text = month.toString() // 월 업데이트

            // 클릭 상태 업데이트
            calendarData = calendarData.map {
                if (it.day == day) it.copy(isClicked = true) else it.copy(isClicked = false)
            }.toMutableList()

            setupCalendarAdapter()
        }
    }

    //  리사이클러뷰에 어댑터 연결
    private fun setupCalendarAdapter() {
        binding.calendar.layoutManager = GridLayoutManager(this, 7)
        binding.calendar.adapter = CalendarAdapter(calendarData) { day, _, _, month, _ ->
            onDateClick(day, month)
        }
    }

    //  Retrofit API 호출 (수입 총합)
    private fun loadIncomeData() {
        val year = calendar.get(Calendar.YEAR).toString()
        val month = (calendar.get(Calendar.MONTH) + 1).toString()
        val date = (calendar.get(Calendar.DATE) + 1). toString()

        //  레트로 핏 API로 데이터를 받아오고 로그인 아이디를 담은 memberId를 매개변수로 서버로 전송
        val api = AppServerClass.instance
        val call = api.getMainIncome(year, month, "test1")

        call.enqueue(object : Callback<List<IncomeLogDTO>>{
            override fun onResponse(p0: Call<List<IncomeLogDTO>>, res: Response<List<IncomeLogDTO>>) {
                if (res.isSuccessful) {
                    val result = res.body()?.toMutableList()
                    Log.d("csy", "result : $result")

                    //  총수입 result 안에 incomeMoney 값을 다 더해서 바인딩
                    var totalIncome = 0
                    for (item in result!!) {
                        totalIncome += item.incomeMoney?.toIntOrNull() ?: 0
                    }
                    Log.d("csy", "totalIncome : $totalIncome")
                    binding.tvHeaderIncome.text = totalIncome.toString() + "원"
                }
                else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<IncomeLogDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })
    }

    //  Retrofit API 호출 (지출 총합)
    private fun loadExpenseData() {
        val year = calendar.get(Calendar.YEAR).toString()
        val month = (calendar.get(Calendar.MONTH) + 1).toString()
        val date = (calendar.get(Calendar.DATE) + 1).toString()

        //  레트로 핏 API로 데이터를 받아오고 로그인 아이디를 담은 memberId를 매개변수로 서버로 전송
        val api = AppServerClass.instance
        val call = api.getMainExpense(year, month, "test1")

        call.enqueue(object : Callback<List<ExpenseLogDTO>>{
            override fun onResponse(p0: Call<List<ExpenseLogDTO>>, res: Response<List<ExpenseLogDTO>>) {
                if (res.isSuccessful) {
                    val result = res.body()?.toMutableList()
                    Log.d("csy", "result : $result")

                    //  총수입 result 안에 expenseMoney 값을 다 더해서 바인딩
                    var totalExpense = 0
                    for (item in result!!) {
                        totalExpense += item.expenseMoney?.toIntOrNull() ?: 0
                    }
                    Log.d("csy", "totalIncome : $totalExpense")
                    binding.tvHeaderExpense.text = totalExpense.toString() + "원"
                }
                else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<ExpenseLogDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })
    }
}
