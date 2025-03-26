package bitc.example.app.kms

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ExpenseItemRecyclerViewBinding
import bitc.example.app.databinding.IncomItemRecyclerViewBinding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.dto.IncomeLogDTO

class ExpenseAdapter(val datas: MutableList<ExpenseLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpenseViewHolder(ExpenseItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, index: Int) {
//        Log.d("fullstack503", "onBindViewHolder : $position")
//        Log.d("csy", "datas : $datas")

//        val incomeDateList = datas.map { it.incomeDate }
//        val incomeCateList = datas.map { it.incomeCate }
//        val incomeMoneyList = datas.map { it.incomeMoney }


        val binding = (holder as ExpenseViewHolder).binding

        binding.expenseItemDateData.text = datas[index].expenseDate
        binding.expenseItemCateData.text = datas[index].expenseCate
        binding.expenseItemMoneyData.text = datas[index].expenseMoney
    }

}