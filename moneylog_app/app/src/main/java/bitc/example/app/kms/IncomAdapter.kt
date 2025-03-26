package bitc.example.app.kms

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.IncomItemRecyclerViewBinding
<<<<<<< HEAD

class IncomAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
=======
import bitc.example.app.dto.IncomeLogDTO

class IncomAdapter(val datas: MutableList<IncomeLogDTO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //class IncomAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
>>>>>>> origin/khamro1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IncomViewHolder(IncomItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

<<<<<<< HEAD
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d("fullstack503", "onBindViewHolder : $position")

        val binding = (holder as IncomViewHolder).binding

        binding.incomItemData.text = datas[position]
=======
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
>>>>>>> origin/khamro1
    }

}