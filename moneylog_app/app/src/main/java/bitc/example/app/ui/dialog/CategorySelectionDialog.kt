package bitc.example.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.adapter.CategoryAdapter
import bitc.example.app.databinding.DialogCategorySelectionBinding

// 검색 페이지에서 카테고리 선택했을때 뜨는 다이얼로그 로직
class CategorySelectionDialog(
  context: Context,
  private val selectedCategories: MutableMap<String, Boolean>,
  private val onConfirm: (Map<String, Boolean>) -> Unit
) : Dialog(context) {

  private val binding: DialogCategorySelectionBinding by lazy {
    DialogCategorySelectionBinding.inflate(layoutInflater)
  }

  private lateinit var recyclerView: RecyclerView
  private lateinit var buttonContainer: LinearLayout
  private lateinit var adapter: CategoryAdapter
  private lateinit var selectAllCheckBox: CheckBox  // 전체 선택 체크박스 추가
  private val incomeCategories = listOf("월급", "용돈", "이월", "알바비", "기타")
  private val expenseCategories = listOf("식비", "교통비","문화생활", "생활품","의류", "교육", "의료", "회비", "공과금", "경조사", "카드대금", "가전", "저축", "보험", "세금", "기타")

  private var isIncomeSelected = true // 기본값: 수입 리스트 표시

  private lateinit var tvIncome: TextView
  private lateinit var tvExpense: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    // '전체선택' 체크박스 초기 선택 상태를 false로 설정 (선택되지 않도록)
    if (selectedCategories.isEmpty()) {
      incomeCategories.forEach { selectedCategories[it] = false }
      expenseCategories.forEach {
        selectedCategories[it] = false
      }
    }

//    다이얼로그 창 크기 조정
    window?.setLayout(
      (context.resources.displayMetrics.widthPixels * 0.8).toInt(),  // 가로: 화면의 80%
      ViewGroup.LayoutParams.WRAP_CONTENT  // 세로: 내용에 맞춤
    )

    tvIncome = binding.tvIncome
    tvExpense = binding.tvExpense
    val btnCancel = binding.btnCancel
    val btnConfirm = binding.btnConfirm
    buttonContainer = binding.buttonContainer

    recyclerView = binding.categoryList
    selectAllCheckBox = binding.checkboxSelectAll  // 전체선택 체크박스 연결
    selectAllCheckBox.isChecked = false // '전체선택' 체크박스 기본값을 선택 안됨으로 설정

    recyclerView.layoutManager = LinearLayoutManager(context)

    //    리스트 초기화
    updateCategoryList()

    // "전체 선택" 체크박스 클릭 이벤트 추가
    selectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->
      setAllItemsChecked(isChecked)
    }

//    기본 선택 : 수입
    selectIncome()
    updateCategoryList()

//    수업 버튼 클릭 이벤트
    tvIncome.setOnClickListener {
//      수입 버튼 클릭
      selectIncome()
//      수입 리스트 출력
      isIncomeSelected = true
      updateCategoryList()
    }

//    지출 버튼 클릭 이벤트
    tvExpense.setOnClickListener {
//      지출 버튼 클릭
      selectExpense()
//      지출 리스트 출력
      isIncomeSelected = false
      updateCategoryList()
    }

//    취소 버튼 클릭 이벤트
    btnCancel.setOnClickListener { dismiss() }

//    확인 버튼 클릭 이벤트
    btnConfirm.setOnClickListener {
      onConfirm(selectedCategories)
      dismiss()
    }
    updateSelectAllCheckBox()  // 초기 체크 상태 설정
  }

//  수입 버튼 클릭 시 버튼 색상 변경
  private fun selectIncome() {
  tvIncome.setBackgroundResource(R.drawable.bg_income_selected)
  tvExpense.setBackgroundResource(R.drawable.bg_expense_unselected)
}

//  지출 버튼 클릭 시 버튼 색상 변경
  private fun selectExpense() {
    tvIncome.setBackgroundResource(R.drawable.bg_expense_unselected)
    tvExpense.setBackgroundResource(R.drawable.bg_expense_selected)
  }

  // 선택된 카테고리 목록 업데이트
  @SuppressLint("NotifyDataSetChanged")
  private fun updateCategoryList() {
    val categories = if (isIncomeSelected) incomeCategories
    else expenseCategories

    adapter = CategoryAdapter(categories, selectedCategories) { updatedCategories ->
      selectedCategories.putAll(updatedCategories)
      updateSelectAllCheckBox()
    }
    recyclerView.adapter = adapter
    adapter.notifyDataSetChanged()

    // 전체 선택 체크박스 업데이트
    updateSelectAllCheckBox()

//    버튼이 안사라지도록
    buttonContainer.visibility = View.VISIBLE
  }

  //  "전체 선택" 체크박스 상태 업데이트
    private fun updateSelectAllCheckBox() {
    val categories = if (isIncomeSelected) incomeCategories else expenseCategories
    // 기존 리스너 제거 (중복 호출 방지)
    selectAllCheckBox.setOnCheckedChangeListener(null)
  //  현재 리스트의 모든 항목이 선택되어 있으면 true, 아니면 false
    selectAllCheckBox.isChecked = categories.all { selectedCategories[it] == true }
    // 리스너 다시 추가 (체크박스 변경 이벤트 적용)
    selectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->
      setAllItemsChecked(isChecked)
    }
  }

  //   전체 선택 기능
  private fun setAllItemsChecked(isChecked: Boolean) {
    val categories = if (isIncomeSelected) incomeCategories else expenseCategories
    categories.forEach { selectedCategories[it] = isChecked }
    adapter.notifyDataSetChanged()
  }

}