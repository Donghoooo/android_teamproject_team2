package bitc.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ItemRecyclerViewBinding
import bitc.example.app.dto.IncomeLogDTO

class InComeAdapter(val datas: List<IncomeLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InComeHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return datas.size



            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as InComeHolder).binding

        binding.CateDate.text = datas[position].incomeCate
        binding.MoneyDate.text = datas[position].incomeMoney
//        binding.itemData.text
//        binding.bal.text = datas[position]


    }


}