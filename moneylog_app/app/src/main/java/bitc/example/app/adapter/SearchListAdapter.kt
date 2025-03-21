package bitc.example.app.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.model.SearchListItem

class SearchListAdapter(private val searchItemList: List<SearchListItem>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvDate: TextView = view.findViewById(R.id.tvDate)
    val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    val tvDetail: TextView = view.findViewById(R.id.tvDetail)
    val tvMethod: TextView = view.findViewById(R.id.tvMethod)
    val tvAmount: TextView = view.findViewById(R.id.tvAmount)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.search_item_list, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val searchItem = searchItemList[position]
    holder.tvDate.text = searchItem.date
    holder.tvCategory.text = searchItem.category
    holder.tvDetail.text = searchItem.detail
    holder.tvMethod.text = searchItem.method
    holder.tvAmount.text = searchItem.amount

//    금액 색상을 지출이면 빨간색, 수입이면 파란색
    if (searchItem.isExpense){
      holder.tvAmount.setTextColor(Color.RED)
    }
    else {
      holder.tvAmount.setTextColor(Color.BLUE)
    }
  }

  override fun getItemCount() = searchItemList.size

}