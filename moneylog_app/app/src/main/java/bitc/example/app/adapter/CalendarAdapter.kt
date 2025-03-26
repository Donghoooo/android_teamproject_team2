package bitc.example.app.adapter

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.databinding.ItemCalendarDayBinding
import bitc.example.app.model.CalendarData

class CalendarAdapter(
    private val calendarData: List<CalendarData>,
    private val onDayClick: (Int?, Int, Int, Int) -> Unit // 날짜, 수입, 지출, 월을 전달하는 클릭 이벤트
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(val binding: ItemCalendarDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val data = calendarData[position]
        val context = holder.itemView.context

        // 날짜가 존재하면 표시
        data.day?.let { day ->
            holder.binding.tvDay.text = day.toString()

            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, data.year)
                set(Calendar.MONTH, data.month)
                set(Calendar.DAY_OF_MONTH, day)
            }

            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            when (dayOfWeek) {
                Calendar.SATURDAY -> holder.binding.tvDay.setTextColor(ContextCompat.getColor(context, R.color.blue))
                Calendar.SUNDAY -> holder.binding.tvDay.setTextColor(ContextCompat.getColor(context, R.color.red))
                else -> holder.binding.tvDay.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }

        // 빈 칸 처리
        if (data.day == null) {
            holder.binding.tvDay.text = ""
            holder.binding.tvExpense.visibility = View.GONE
            holder.binding.tvIncome.visibility = View.GONE
        } else {
            holder.binding.tvExpense.text = if (data.isExpense) "- 지출" else ""
            holder.binding.tvExpense.visibility = if (data.isExpense) View.VISIBLE else View.GONE

            holder.binding.tvIncome.text = if (data.isIncome) "+ 수입" else ""
            holder.binding.tvIncome.visibility = if (data.isIncome) View.VISIBLE else View.GONE

            holder.binding.tvDay.setOnClickListener {
                val selectedMonth = (data.month ?: 0) + 1
                onDayClick(data.day, if (data.isIncome) 1 else 0, if (data.isExpense) 1 else 0, selectedMonth)
            }
        }
    }

    override fun getItemCount() = calendarData.size
}
