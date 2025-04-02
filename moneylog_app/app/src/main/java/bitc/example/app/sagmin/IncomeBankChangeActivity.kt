package bitc.example.app.ui.dialog

import SBankAdapter
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ActivityIncomeBankChangeBinding

class IncomeBankChangeActivity(
    context: Context,
    private var selectedBanks: String? = null ,
    private val onConfirm: (String?)  -> Unit
) : Dialog(context) {

    private val binding: ActivityIncomeBankChangeBinding by lazy {
        ActivityIncomeBankChangeBinding.inflate(layoutInflater)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonContainer: LinearLayout
    private lateinit var adapter: SBankAdapter
    private val banks = listOf("현금", "계좌이체")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 다이얼로그 창 크기 조정
        window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnCancel = binding.btnCancel
        val btnConfirm = binding.btnConfirm  // 확인 버튼 참조 추가
        buttonContainer = binding.buttonContainer
        recyclerView = binding.bankList   // 체크박스 리스트
        recyclerView.layoutManager = LinearLayoutManager(context)

        // ✅ 리스트 초기화 (체크박스 선택 시 버튼 상태 업데이트 추가)
        adapter = SBankAdapter(banks, selectedBanks) { updatedBanks ->
            selectedBanks = updatedBanks // 선택된 항목 업데이트
            updateConfirmButtonState(btnConfirm)  // ✅ 버튼 상태 즉시 업데이트
        }
        recyclerView.adapter = adapter

        // ✅ 초기 버튼 상태 설정 (selectedBanks가 비어있다면 비활성화)
        updateConfirmButtonState(btnConfirm)

        // 취소 버튼 클릭 이벤트
        btnCancel.setOnClickListener { dismiss() }

        // 확인 버튼 클릭 이벤트
        btnConfirm.setOnClickListener {
            Log.d("IncomeBankChangeActivity", "확인 버튼 클릭됨, 선택된 은행: $selectedBanks")
            onConfirm(selectedBanks)
            dismiss()
        }
    }

    // ✅ 확인 버튼 상태 업데이트 함수 (선택된 값이 있으면 활성화)
    private fun updateConfirmButtonState(btnConfirm: AppCompatButton) {
        btnConfirm.isEnabled = !selectedBanks.isNullOrEmpty()
    }
}
