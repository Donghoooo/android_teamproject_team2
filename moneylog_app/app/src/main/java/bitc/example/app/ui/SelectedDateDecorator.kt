package bitc.example.app.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import bitc.example.app.R
import com.prolificinteractive.materialcalendarview.CalendarDay

class SelectedDateDecorator(val context: Context) : DayViewDecorator {
  private var selectedDate: CalendarDay? = null

  // 선택된 날짜를 설정하는 함수
  fun setSelectedDay(date: CalendarDay) {
    selectedDate = date
  }

  override fun shouldDecorate(day: CalendarDay): Boolean {
    // 선택된 날짜에 대해서만 꾸밈 처리
    return day == selectedDate
  }

  override fun decorate(view: DayViewFacade) {
    // 선택된 날짜의 배경색을 변경
    val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.custom_selected_date_background)
    drawable?.let {
      view.setBackgroundDrawable(it)
    }
    view.addSpan(ForegroundColorSpan(Color.BLACK))
  }
}