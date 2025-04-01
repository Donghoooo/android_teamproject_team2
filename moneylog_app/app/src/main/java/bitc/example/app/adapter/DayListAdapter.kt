package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.model.SearchListItem
import bitc.example.app.R
import java.text.NumberFormat
import java.util.Locale

// 날짜 선택시 뜨는 리스트 출력 부분
class DayListAdapter(private var searchItemList: MutableList<SearchListItem>): RecyclerView.Adapter<DayListAdapter.TransactionViewHolder>() {

  class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    val tvUse: TextView = view.findViewById(R.id.tvUse)
    val tvSource: TextView = view.findViewById(R.id.tvSource)
    val tvMoney: TextView = view.findViewById(R.id.tvMoney)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.day_list_item, parent, false)
    return TransactionViewHolder(view)
  }

  override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
    val transaction = searchItemList[position]
    // 카테고리, 내역, 결제 방식
    holder.tvCategory.text = transaction.category
    holder.tvUse.text = transaction.use
    holder.tvSource.text = transaction.source


    // 금액에 3자리마다 쉼표 추가
//    val formattedMoney = NumberFormat.getNumberInstance(Locale.KOREA).format(transaction.money)
    holder.tvMoney.text = "${transaction.money} 원"

    // 수입/지출에 따라 색상 변경
    if (transaction.type == "expense") {
      holder.tvMoney.setTextColor(holder.itemView.resources.getColor(R.color.expense, null))
    } else {
      holder.tvMoney.setTextColor(holder.itemView.resources.getColor(R.color.income, null))
    }
  }
  override fun getItemCount() = searchItemList.size

  // 데이터 업데이트 함수
  fun updateData(newList: List<SearchListItem>) {
//    searchItemList = newList as MutableList<SearchListItem>
    searchItemList.clear()
    searchItemList.addAll(newList)
    notifyDataSetChanged()
  }
}