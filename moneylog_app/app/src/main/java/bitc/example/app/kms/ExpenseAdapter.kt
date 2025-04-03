package bitc.example.app.kms

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ExpenseItemRecyclerViewBinding
import bitc.example.app.databinding.IncomItemRecyclerViewBinding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.sagmin.DetailOutcomeActivity
import java.text.NumberFormat

class ExpenseAdapter(val datas: MutableList<ExpenseLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpenseViewHolder(ExpenseItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, index: Int) {
        val binding = (holder as ExpenseViewHolder).binding


        val groupedExpense = datas[index]
        val amount = groupedExpense.expenseMoney?.toIntOrNull() ?: 0
        val formattedAmount = NumberFormat.getNumberInstance().format(amount)
        binding.expenseItemMoneyData.text = "- $formattedAmount 원"


        binding.expenseItemDateData.text = datas[index].expenseDate
        binding.expenseItemCateData.text = datas[index].expenseCate
//        binding.expenseItemMoneyData.text = "- " + datas[index].expenseMoney + "원"
        binding.expenseItemMemoData.text = datas[index].expenseMemo
        binding.expenseItemOptionData.text = datas[index].paymentOption
        binding.expenseItemUseData.text = datas[index].expenseUse
        binding.expenseItemSeqData.text = datas[index].expenseLogSeq.toString()

        binding.linearOutcome.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailOutcomeActivity::class.java).apply{
                putExtra("expenseLogSeq",datas[index].expenseLogSeq)
                putExtra("expenseCate",datas[index].expenseCate)
                putExtra("expenseMoney",datas[index].expenseMoney)
                putExtra("expenseMemo",datas[index].expenseMemo)
                putExtra("paymentOption",datas[index].paymentOption)
                putExtra("expenseUse",datas[index].expenseUse)
                putExtra("expenseDate",datas[index].expenseDate)
            }
            context.startActivity(intent)
        }




    }

}