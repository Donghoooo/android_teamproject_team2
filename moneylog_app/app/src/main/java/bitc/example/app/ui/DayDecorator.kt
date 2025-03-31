package bitc.example.app.ui

import android.content.Context
import bitc.example.app.R
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade


class DayDecorator(private val context: Context,
                   private val incomeExpenseData: Map<CalendarDay, Pair<Int, Int>>
) : DayViewDecorator {
  private var currentDay: CalendarDay? = null
  init {
    Log.d("DayDecorator", "DayDecorator 생성됨. 데이터 크기: ${incomeExpenseData.size}")
  }

  override fun shouldDecorate(day: CalendarDay): Boolean {
    currentDay = day // 현재 선택된 날짜 저장
    return incomeExpenseData.containsKey(day)
  }

  override fun decorate(view: DayViewFacade) {
    val day = currentDay ?: return // 저장된 날짜 사용
    val (income, expense) = incomeExpenseData[day] ?: Pair(0, 0)

    val incomeColor = ContextCompat.getColor(context, R.color.income)   // 파란색
    val expenseColor = ContextCompat.getColor(context, R.color.expense) // 빨간색
    view.addSpan(StyleSpan(Typeface.BOLD)) // 볼드 적용

    val displayText = when {
      income > 0 && expense > 0 -> "▲$income\n▼$expense"
      income > 0 -> "▲$income"
      expense > 0 -> "▼$expense"
      else -> ""
    }

    val spannable = SpannableString(displayText)
    if (income > 0) {
      val incomeStart = displayText.indexOf("▲")
      val incomeEnd = displayText.indexOf("\n").takeIf { it > 0 } ?: displayText.length
      spannable.setSpan(ForegroundColorSpan(incomeColor), incomeStart, incomeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    if (expense > 0) {
      val expenseStart = displayText.indexOf("▼")
      val expenseEnd = displayText.length
      spannable.setSpan(ForegroundColorSpan(expenseColor), expenseStart, expenseEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    view.addSpan(spannable)
  }
}