package bitc.example.app.kms

import android.content.Intent
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

        val data = datas[position]
        holder.binding.apply {
            incomItemDateData.text = data.incomeDate
            incomItemCateData.text = data.incomeCate
            incomItemMoneyData.text = data.incomeMoney
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailIncomeActivity::class.java).apply {
                putExtra("incomeDate", data.incomeDate)
                putExtra("incomeCate", data.incomeCate)
                putExtra("incomeMoney", data.incomeMoney)
            }
            context.startActivity(intent)
        }
//        Log.d("fullstack503", "onBindViewHolder : $position")
//        Log.d("csy", "datas : $datas")

//        val incomeDateList = datas.map { it.incomeDate }
//        val incomeCateList = datas.map { it.incomeCate }
//        val incomeMoneyList = datas.map { it.incomeMoney }


        val binding = (holder as IncomViewHolder).binding

        binding.incomItemDateData.text = datas[index].incomeDate
        binding.incomItemCateData.text = datas[index].incomeCate
        binding.incomItemMoneyData.text = datas[index].incomeMoney
    }
    class IncomViewHolder(val binding: IncomItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}