package bitc.example.app.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.model.SearchListItem
import java.text.DecimalFormat

class SearchListAdapter(private val searchItems: List<SearchListItem>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvDate: TextView = view.findViewById(R.id.tvDate)
    val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    val tvUse: TextView = view.findViewById(R.id.tvUse)
    val tvSource: TextView = view.findViewById(R.id.tvSource)
    val tvMoney: TextView = view.findViewById(R.id.tvMoney)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.search_item_list, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val searchItem = searchItems[position]

//    금액 부분 숫자만 추출
    val moneyValue = searchItem.money.filter { it.isDigit() }  // 숫자만 추출
    val moneyFormatted = try {
      DecimalFormat("#,###").format(moneyValue.toInt()) + " 원"
    } catch (e: NumberFormatException) {
      "0 원"  // 변환 실패 시 기본값
    }

    holder.tvDate.text = searchItem.date
    holder.tvCategory.text = searchItem.category
    holder.tvUse.text = searchItem.use
    holder.tvSource.text = searchItem.source
    holder.tvMoney.text = moneyFormatted

//    금액 색상을 지출이면 빨간색, 수입이면 파란색
    if (searchItem.type=="expense"){
      holder.tvMoney.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.expense))
    }
    else {
      holder.tvMoney.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.income))
    }
  }

  override fun getItemCount() = searchItems.size

}