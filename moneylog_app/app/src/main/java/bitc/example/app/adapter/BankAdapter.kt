package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R

class BankAdapter (
  private val banks: List<String>,
  private val selectedBanks: MutableMap<String, Boolean>,
  private val onSelectionChanged: (Map<String, Boolean>) -> Unit  // 선택 변경 콜백 추가
) : RecyclerView.Adapter<BankAdapter.ViewHolder>() {

  // 뷰 홀더 정의
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val checkBox: CheckBox = view.findViewById(R.id.checkbox_bank) // item_bank.xml 파일에서 checkBox id 값
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_bank, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val bank = banks[position]

    // 기존 리스너 제거 (스크롤할 때 체크 상태 꼬이는 문제 방지)
    holder.checkBox.setOnCheckedChangeListener(null)

    // 체크박스 설정
    holder.checkBox.text = bank
    holder.checkBox.isChecked = selectedBanks[bank] ?: false

    // 체크박스 선택 이벤트 (선택 상태 저장 & 콜백 호출)
    holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
      selectedBanks[bank] = isChecked
      onSelectionChanged(selectedBanks.toMap())  // 다이얼로그에 변경 사항 반영
    }
  }

  override fun getItemCount(): Int = banks.size


}