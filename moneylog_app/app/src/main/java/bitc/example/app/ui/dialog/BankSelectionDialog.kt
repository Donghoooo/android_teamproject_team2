package bitc.example.app.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.adapter.BankAdapter
import bitc.example.app.databinding.ActivityBankSelectionDialogBinding

// 검색 페이지에서 자산방식 선택했을때 뜨는 다이얼로그 로직

class BankSelectionDialog(
  context: Context,
  private val selectedBanks: MutableMap<String, Boolean>,
  private val onConfirm: (Map<String, Boolean>) -> Unit
) : Dialog(context) {

  private val binding: ActivityBankSelectionDialogBinding by lazy {
    ActivityBankSelectionDialogBinding.inflate(layoutInflater)
  }

  private lateinit var recyclerView: RecyclerView
  private lateinit var buttonContainer: LinearLayout
  private lateinit var adapter: BankAdapter
  private lateinit var selectAllCheckBox: CheckBox  // 전체 선택 체크박스 추가
  private val banks = listOf("현금", "이체", "체크카드", "신용카드")

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
    if (selectedBanks.isEmpty()) {
      banks.forEach { selectedBanks[it] = false }
    }
//    다이얼로그 창 크기 조정
    window?.setLayout(
      (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
      ViewGroup.LayoutParams.WRAP_CONTENT
    )

    val btnCancel = binding.btnCancel
    val btnConfirm = binding.btnConfirm
    buttonContainer = binding.buttonContainer
    recyclerView = binding.bankList   // 체크박스 리스트
    selectAllCheckBox = binding.checkboxSelectAll  // 전체선택 체크박스 연결
    selectAllCheckBox.isChecked = false // '전체선택' 체크박스 기본값을 선택 안됨으로 설정
    recyclerView.layoutManager = LinearLayoutManager(context)

//    리스트 초기화
    adapter = BankAdapter(banks, selectedBanks) { updatedBanks ->
      selectedBanks.putAll(updatedBanks)  // 선택된 항목을 업데이트
      updateSelectAllCheckBox() // 리스트 변경 시 전체 선택 체크박스 상태 업데이트
    }
    recyclerView.adapter = adapter

    // "전체 선택" 체크박스 클릭 이벤트 추가
    selectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->
      setAllItemsChecked(isChecked)
    }

    //    취소 버튼 클릭 이벤트
    btnCancel.setOnClickListener { dismiss() }

//    확인 버튼 클릭 이벤트
    btnConfirm.setOnClickListener {
      onConfirm(selectedBanks)
      dismiss()
    }

    updateSelectAllCheckBox()  // 초기 체크 상태 설정
  }

  //  전체선택 체크박스 상태 업데이트
  private fun updateSelectAllCheckBox() {
    selectAllCheckBox.setOnCheckedChangeListener(null) // ✅ 기존 리스너 제거 (중복 실행 방지)
    selectAllCheckBox.isChecked = selectedBanks.values.all { it }  // ✅ 모든 값이 true이면 체크
    selectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->  // ✅ 리스너 다시 추가
      setAllItemsChecked(isChecked)
    }

  }

  //  항목 전부 선택하거나 전부 해제하는 함수
  private fun setAllItemsChecked(isChecked: Boolean) {
    banks.forEach { selectedBanks[it] = isChecked }
    adapter.notifyDataSetChanged()  // RecyclerView 업데이트
  }
}
