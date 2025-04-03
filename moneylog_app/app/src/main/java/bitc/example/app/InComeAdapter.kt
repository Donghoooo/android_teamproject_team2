package bitc.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ItemRecyclerViewBinding
import bitc.example.app.dto.IncomeLogDTO
import java.text.NumberFormat

class InComeAdapter(val datas: List<IncomeLogDTO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    data class GroupedIncomeData(val category: String, val amount: String)

    private val groupedData: List<GroupedIncomeData> = groupData(datas)

    // 데이터를 카테고리별로 합산하고 그룹화하는 함수
    private fun groupData(data: List<IncomeLogDTO>): List<GroupedIncomeData> {
        val groupedMap = mutableMapOf<String, Int>()  // 카테고리별로 합산된 값을 저장할 맵

        // 데이터를 순회하며 카테고리별로 합산
        for (income in data) {
            val category = income.incomeCate.toString()  // null 방지
            val amount = income.incomeMoney?.toString()?.toIntOrNull() ?: 0  // 변환 가능할 때만 Int로 변환
            groupedMap[category] = groupedMap.getOrDefault(category, 0) + amount
        }

        // 그룹화된 데이터 리스트로 변환
        return groupedMap.map { GroupedIncomeData(it.key, it.value.toString()) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InComeHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return groupedData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as InComeHolder).binding

        val groupedIncome = groupedData[position]
        binding.CateDate.text = groupedIncome.category
        binding.MoneyDate.text = groupedIncome.amount + "원"

        val amount = groupedIncome.amount.toInt()
        val formattedAmount = NumberFormat.getNumberInstance().format(amount)
        binding.MoneyDate.text = "$formattedAmount 원"
    }


}