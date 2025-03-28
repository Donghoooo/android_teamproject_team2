import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.databinding.ItemCalendarDayBinding
import bitc.example.app.model.CalendarData
import java.util.Calendar

class CalendarAdapter(
    private val calendarData: List<CalendarData>,
    private val onDayClick: (Int?, Int, Int, Int, Int) -> Unit // 날짜, 수입, 지출, 월을 전달하는 클릭 이벤트
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(val binding: ItemCalendarDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val data = calendarData[position]
        val context = holder.itemView.context

        // 날짜가 존재 하면 표시
        data.day?.let { day ->
            holder.binding.tvDay.text = day.toString()

//            // 지출 정보 표시
//            if (data.isExpense) {
//                holder.binding.tvExpense.text = "- 지출" // 지출 표시
//                holder.binding.tvExpense.visibility = View.VISIBLE
//            } else {
//                holder.binding.tvExpense.visibility = View.GONE
//            }
//
//            // 수입 정보 표시
//            if (data.isIncome) {
//                holder.binding.tvIncome.text = "+ 수입" // 수입 표시
//                holder.binding.tvIncome.visibility = View.VISIBLE
//            } else {
//                holder.binding.tvIncome.visibility = View.GONE
//            }

//            // 클릭된 날짜일 경우 스타일 적용
//            if (data.isClicked) {
//                holder.binding.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)  // 텍스트 크기 변경
//                holder.binding.tvDay.setTypeface(null, Typeface.BOLD)  // 텍스트 굵게 변경
//            } else {
//                holder.binding.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)  // 기본 크기
//                holder.binding.tvDay.setTypeface(null, Typeface.NORMAL)  // 기본 굵기
//            }

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
//            // 지출 표시
//            holder.binding.tvExpense.text = if (data.expense > 0) "- ₩${data.expense}" else ""
//            holder.binding.tvExpense.visibility = if (data.expense > 0) View.VISIBLE else View.GONE

            // 수입 표시
//            holder.binding.tvIncome.text = if (data.income > 0) "+ ₩${data.income}" else ""
//            holder.binding.tvIncome.visibility = if (data.income > 0) View.VISIBLE else View.GONE


            holder.binding.tvDay.setOnClickListener {
                val selectedMonth = (data.month ?: 0) + 1
                // 클릭한 날짜를 isClicked로 설정하고, 나머지 날짜는 해제
                val year = data.year
                onDayClick(data.day, if (data.isIncome) 1 else 0, if (data.isExpense) 1 else 0, selectedMonth, year)
            }
        }
    }

    override fun getItemCount() = calendarData.size
}
