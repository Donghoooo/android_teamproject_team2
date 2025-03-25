package bitc.example.app.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bitc.example.app.model.CalendarData

class CalendarViewModel : ViewModel() {

  // 전역 변수로 Calendar 선언
  private val calendar = Calendar.getInstance()

  val calendarData = MutableLiveData<List<CalendarData>>()
  val headerDate = MutableLiveData<String>()

  init {
    generateCalendarData()
  }

  // 달력 데이터 생성 함수
  private fun generateCalendarData() {
    calendar.set(Calendar.DAY_OF_MONTH, 1) // 1일로 설정
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7

    // 상단 월 표시
    headerDate.value = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
    val data = mutableListOf<CalendarData>()

    // 첫 주 빈칸 추가
    for (i in 0 until firstDayOfWeek) {
      data.add(CalendarData(null, false, false, true))
    }

    // 날짜 추가
    for (day in 1..daysInMonth) {
      val isIncome = day % 1 == 0
      val isExpense = day % 1 == 0
      data.add(CalendarData(day, isIncome, isExpense))
    }

    calendarData.value = data
  }

  // 월 변경 함수
  fun changeMonth(offset: Int) {
    calendar.add(Calendar.MONTH, offset) // 월 변경
    generateCalendarData() // 데이터 다시 생성
  }
}
