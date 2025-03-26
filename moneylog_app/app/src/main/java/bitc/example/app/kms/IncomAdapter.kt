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

        val item = datas[index]

        binding.incomItemDateData.text = item.incomeDate
        binding.incomItemCateData.text = item.incomeCate
        binding.incomItemMoneyData.text = item.incomeMoney

        binding.incomItemCateData.setOnClickListener {
            val context = binding.root.context
            val intent = Intent(context, DetailIncomeActivity::class.java).apply {
                putExtra("incomeDate", item.incomeDate)
                putExtra("incomeCate", item.incomeCate)
                putExtra("incomeMoney", item.incomeMoney)
            }
            context.startActivity(intent)
        }
    }
}