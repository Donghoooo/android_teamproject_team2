package bitc.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ItemRecyclerView2Binding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO
import java.text.NumberFormat

class ExpenAdapter(val datas: List<ExpenseLogDTO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    data class GroupedExpenData(val category1: String, val amount1: String)

    private val groupedData1: List<GroupedExpenData> = groupData1(datas)

    // 데이터를 카테고리별로 합산하고 그룹화하는 함수
    private fun groupData1(data: List<ExpenseLogDTO>): List<GroupedExpenData> {
        val groupedMap = mutableMapOf<String, Int>()  // 카테고리별로 합산된 값을 저장할 맵

        // 데이터를 순회하며 카테고리별로 합산
        for (expen in data) {
            val category = expen.expenseCate
            val amount = expen.expenseMoney?.toIntOrNull() ?: 0  // "incomeMoney"를 Int로 변환
            groupedMap[category.toString()] = groupedMap.getOrDefault(category, 0) + amount
        }

        // 그룹화된 데이터 리스트로 변환
        return groupedMap.map { GroupedExpenData(it.key, it.value.toString()) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpenHolder(
            ItemRecyclerView2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return groupedData1.size


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ExpenHolder).binding

        val groupedExpen = groupedData1[position]
        binding.CateDate1.text = groupedExpen.category1
        binding.MoneyDate1.text = groupedExpen.amount1 + "원"

        val amount = groupedExpen.amount1.toInt()
        val formattedAmount = NumberFormat.getNumberInstance().format(amount)
        binding.MoneyDate1.text = "$formattedAmount 원"

    }


}