package bitc.example.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.adapter.CategoryAdapter
import bitc.example.app.databinding.DialogCategorySelectionBinding

// 카테고리 검색 페이지에서 카테고리 선택했을때 뜨는 다이얼로그 로직
class CategorySeletionDialog(
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
  private val incomeCategories = listOf("월급", "용돈", "이월", "알바비", "기타")
  private val expenseCategories = listOf("식비", "교통비","문화생활", "생활품","의류", "교육", "의료", "회비", "공과금", "경조사", "카드대금", "가전", "저축", "보험", "세금", "기타")

  private var isIncomeSelected = true // 기본값: 수입 리스트 표시

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

//    다이얼로그 창 크기 조정
    window?.setLayout(
      (context.resources.displayMetrics.widthPixels * 0.8).toInt(),  // 가로: 화면의 80%
      ViewGroup.LayoutParams.WRAP_CONTENT  // 세로: 내용에 맞춤
    )

    val btnIncome = binding.btnIncome
    val btnExpense = binding.btnExpense
    val btnCancel = binding.btnCancel
    val btnConfirm = binding.btnConfirm
    buttonContainer = binding.buttonContainer

    recyclerView = binding.categoryList
    recyclerView.layoutManager = LinearLayoutManager(context)

    // 초기에 수입 리스트 표시
    updateCategoryList()

//    수업 버튼 클릭 이벤트
    btnIncome.setOnClickListener {
      isIncomeSelected = true
      updateCategoryList()
    }

//    지출 버튼 클릭 이벤트
    btnExpense.setOnClickListener {
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
  }

  // 선택된 카테고리 목록 업데이트
  @SuppressLint("NotifyDataSetChanged")
  private fun updateCategoryList() {
    val categories = if (isIncomeSelected) incomeCategories
    else expenseCategories

    adapter = CategoryAdapter(categories, selectedCategories)
    recyclerView.adapter = adapter
    adapter.notifyDataSetChanged()

//    버튼이 안사라지도록
    buttonContainer.visibility = View.VISIBLE
  }
}