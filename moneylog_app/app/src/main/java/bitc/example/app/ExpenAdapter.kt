package bitc.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ItemRecyclerView2Binding
import bitc.example.app.dto.ExpenseLogDTO

class ExpenAdapter(val datas: List<ExpenseLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    private val itemList = datas.take(5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpenHolder(ItemRecyclerView2Binding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return datas.size



            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ExpenHolder).binding

        binding.CateDate1.text = datas[position].expenseCate
        binding.MoneyDate1.text = datas[position].expenseMoney
//        binding.bal.text = datas[position]


    }


}