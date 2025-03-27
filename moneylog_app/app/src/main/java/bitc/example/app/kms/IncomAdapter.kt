package bitc.example.app.kms

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.IncomItemRecyclerViewBinding
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.sagmin.DetailIncomeActivity

class IncomAdapter(val datas: MutableList<IncomeLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //class IncomAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IncomViewHolder(IncomItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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


        val binding = (holder as IncomViewHolder).binding

        binding.incomItemDateData.text = datas[index].incomeDate
        binding.incomItemCateData.text = datas[index].incomeCate
        binding.incomItemMoneyData.text = datas[index].incomeMoney
        binding.incomItemMemoData.text = datas[index].incomeMemo
        binding.incomItemSourceData.text = datas[index].incomeSource
        binding.incomItemUseData.text = datas[index].incomeUse
        binding.incomItemSeqData.text = datas[index].incomeLogSeq.toString()

        binding.linearIncome.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailIncomeActivity::class.java).apply {
                putExtra("incomeLogSeq", datas[index].incomeLogSeq)
                putExtra("incomeDate", datas[index].incomeDate)
                putExtra("incomeCate", datas[index].incomeCate)
                putExtra("incomeMoney", datas[index].incomeMoney)
                putExtra("incomeSource", datas[index].incomeSource)
                putExtra("incomeMemo", datas[index].incomeMemo)
            }
            context.startActivity(intent)
        }

    }




}