package bitc.example.app.kms

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ItemRecyclerViewBinding

class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("fullstack503", "onBindViewHolder : $position")

        val binding = (holder as MyViewHolder).binding

        binding.itemData.text = datas[position]

        binding.itemData.setOnClickListener {
            Log.d("fullstack503", "item data click : $position")
        }
    }

}