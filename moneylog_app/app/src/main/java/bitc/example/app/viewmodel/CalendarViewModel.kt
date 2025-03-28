//package bitc.example.app.viewmodel
//
//import android.icu.util.Calendar
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import bitc.example.app.AppServerClass
//import bitc.example.app.model.CalendarData
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class CalendarViewModel : ViewModel() {
//
//  private val calendar = Calendar.getInstance()  // 현재 날짜를 가져옵니다.
//
//  val calendarData = MutableLiveData<List<CalendarData>>()  // 달력 데이터 (각 날짜의 데이터)
//  val headerDate = MutableLiveData<String>()  // 년도와 월을 표시
//  val scrollMonth = MutableLiveData<String>()  // 월만 표시
//
//  init {
//    generateCalendarData()  // 초기화 시 달력 데이터 생성
////    loadExpenseData() //지출 데이터 가져오기
//  }
//
//  // 달력 데이터 생성 함수
//  private fun generateCalendarData() {
//    calendar.set(Calendar.DAY_OF_MONTH, 1) // 1일로 설정
//    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // 한 달의 총 일수
//    val firstDayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7  // 첫 주의 요일 (일요일이 1이기 때문에 이를 조정)
//
//    // 상단 월 표시 (년도 + 월)
//    headerDate.value = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
//
//    var year : String = (calendar.get(Calendar.YEAR)).toString()
//    var month : String = (calendar.get(Calendar.MONTH) + 1).toString()
//
//    Log.d("month", month)
//    Log.d("month", year)
//
//    //  변수 memberId에 로그인 되어있는 값(test1)을 String타입으로 할당해줌
////    memberId1 = requireContext().getSharedPreferences("memberInfo", MODE_PRIVATE)
////    memberId = memberId1.getString("memberId", "아이디").toString()
//
//    //  레트로 핏 API로 데이터를 받아오고 로그인 아이디를 담은 memberId를 매개변수로 서버로 전송
//    val api = AppServerClass.instance
//    val call = api.getMainIncome(year, month, "test1")
//
//    call.enqueue(object : Callback<Int> {
//      override fun onResponse(p0: Call<Int> , res: Response<Int>) {
//        if (res.isSuccessful) {
//          val result = res.body()
//          Log.d("csy", "result : $result")
//        }
//        else {
//          Log.d("csy", "송신실패")
//        }
//      }
//
//      override fun onFailure(p0: Call<Int> , t: Throwable) {
//        Log.d("csy", "message : ${t.message}")
//      }
//    })
//
//
//
//
//    // 월만 표시 (숫자)
//    if (scrollMonth.value == null) {
//      scrollMonth.value = (calendar.get(Calendar.MONTH) + 1).toString()
//    }
//
//    val data = mutableListOf<CalendarData>()
//
//    // 첫 주 빈 칸 추가 (월요일이 아닌 날에 빈 칸 추가)
//    for (i in 0 until firstDayOfWeek) {
//      data.add(CalendarData(null, false, false, false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)))
//    }
//
//    // 날짜 추가 (1일부터 해당 월의 마지막 날까지)
//    for (day in 1..daysInMonth) {
//      val isWeekend = isWeekend(day)  // 주말인지 확인
//      // 수입과 지출은 기본값으로 false로 설정 (나중에 변경 가능)
//      data.add(CalendarData(day, isWeekend, false, false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)))
//    }
//
//    // 달력 데이터 설정
//    calendarData.value = data
//  }
//
//  // 주말 여부를 확인하는 함수
//  private fun isWeekend(day: Int): Boolean {
//    val calendarInstance = Calendar.getInstance()
//    calendarInstance.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
//    calendarInstance.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
//    calendarInstance.set(Calendar.DAY_OF_MONTH, day)
//
//    val dayOfWeek = calendarInstance.get(Calendar.DAY_OF_WEEK)
//
//    // 토요일(SATURDAY: 7) 또는 일요일(SUNDAY: 1)
//    return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
//  }
//
//
//  // 월 변경 함수 (이전/다음 월로 이동)
//  fun changeMonth(offset: Int) {
//    calendar.add(Calendar.MONTH, offset)  // 한 달 단위로 날짜를 변경
//    generateCalendarData()  // 데이터 갱신
//  }
//
////   클릭 날짜 월 변경
//  fun onDateClick(day: Int, month: Int) {
//    scrollMonth.value = month.toString()
//
////   날짜 클릭 상태 변경
//  val updatedData = calendarData.value?.map {
//    if (it.day == day) {
//      it.copy(isClicked = true) // 클릭된 날짜
//    } else {
//      it.copy(isClicked = false) // 다른 날짜는 클릭 해제
//    }
//  }
//  calendarData.value = updatedData
//  }
//
//
//}
