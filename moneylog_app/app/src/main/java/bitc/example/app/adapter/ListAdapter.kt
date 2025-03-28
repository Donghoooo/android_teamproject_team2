package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.dto.MainListDTO
import bitc.example.app.model.ListData

class ListAdapter(private val mainList: MutableList<MainListDTO>) :
  RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
  class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cateTextView: TextView = view.findViewById(R.id.main_list_cate)
    val useeTextView: TextView = view.findViewById(R.id.main_list_usee)
    val wayTextView: TextView = view.findViewById(R.id.main_list_way)
    val amountTextView: TextView = view.findViewById(R.id.main_list_amount)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_list, parent, false)
    return ListViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    val item = mainList[position]
    holder.cateTextView.text = item.cate
    holder.useeTextView.text = item.usee
    holder.wayTextView.text = item.way
    holder.amountTextView.text = "${item.amount}원"
  }

  override fun getItemCount(): Int = mainList.size

  fun setData(newList: List<MainListDTO>) {
    mainList.clear()
    mainList.addAll(newList)
    notifyDataSetChanged()
  }
}

