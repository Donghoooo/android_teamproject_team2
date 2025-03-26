package bitc.example.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R

class SCategoryAdapter(
    private val categories: List<String>,
    private var selectedCategories: String?,  // 선택된 항목 저장
    private val onCategorySelected: (String) -> Unit

    ) : RecyclerView.Adapter<SCategoryAdapter.ViewHolder>() {

        // 뷰 홀더 정의
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val radioButton: RadioButton = itemView.findViewById(R.id.checkbox_category) // item_category.xml 파일에서 checkBox id 값
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_change, parent, false)
            return ViewHolder(view)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.radioButton.text = category
        holder.radioButton.isChecked = category == selectedCategories

        holder.radioButton.setOnClickListener {
            selectedCategories = category
            Log.d("SBankAdapter", "선택된 은행: $selectedCategories")
            onCategorySelected(category)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = categories.size
}

