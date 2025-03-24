package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R

class CategoryAdapter (
  private val categories: List<String>,
  private val selectedCategories: MutableMap<String, Boolean>,  // 선택된 항목 저장
  private val onSelectionChanged: (Map<String, Boolean>) -> Unit  // 선택 변경 콜백 추가
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

  // 뷰 홀더 정의
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val checkBox: CheckBox = view.findViewById(R.id.checkbox_category) // item_category.xml 파일에서 checkBox id 값
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_category, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val category = categories[position] // 현재 카테고리

    // 기존 리스너 제거 (스크롤할 때 체크 상태 꼬이는 문제 방지)
    holder.checkBox.setOnCheckedChangeListener(null)

    holder.checkBox.text = category
    holder.checkBox.isChecked = selectedCategories[category] ?: false

//    체크박스 선택 이벤트
    holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
      selectedCategories[category] = isChecked
      onSelectionChanged(selectedCategories.toMap())  // 다이얼로그에 변경 사항 반영
    }
  }

  override fun getItemCount() = categories.size
  }


