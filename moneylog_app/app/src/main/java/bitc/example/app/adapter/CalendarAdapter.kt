package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.databinding.ItemCalendarDayBinding
import bitc.example.app.model.CalendarData

class CalendarAdapter(private val calendarData: List<CalendarData>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val data = calendarData[position]

        // 빈 칸 처리 (day가 null인 경우)
        if (data.day == null) {
            holder.binding.tvDay.text = ""
            holder.binding.tvExpense.visibility = View.GONE // 빈 칸인 경우에는 지출을 숨김
            holder.binding.tvIncome.visibility = View.GONE // 빈 칸인 경우에는 수입을 숨김
        } else {
            holder.binding.tvDay.text = data.day.toString()

            // 지출 데이터 표시
            if (data.isExpense) {
                holder.binding.tvExpense.text = "- 지출"
                holder.binding.tvExpense.visibility = View.VISIBLE
            } else {
                holder.binding.tvExpense.text = ""
                holder.binding.tvExpense.visibility = View.GONE
            }

            // 수입 데이터 표시
            if (data.isIncome) {
                holder.binding.tvIncome.text = "+ 수입"
                holder.binding.tvIncome.visibility = View.VISIBLE
            } else {
                holder.binding.tvIncome.text = ""
                holder.binding.tvIncome.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = calendarData.size
}
