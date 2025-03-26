package bitc.example.app.ui.dialog

import SBankAdapter
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.databinding.ActivityIncomeBankChangeBinding

// 검색 페이지에서 자산방식 선택했을때 뜨는 다이얼로그 로직

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
    private val banks = listOf("현금", "계좌이체", "카카오페이", "무통장입금")

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
            (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnCancel = binding.btnCancel
        val btnConfirm = binding.btnConfirm
        buttonContainer = binding.buttonContainer
        recyclerView = binding.bankList   // 체크박스 리스트
        recyclerView.layoutManager = LinearLayoutManager(context)

//    리스트 초기화
        adapter = SBankAdapter(banks, selectedBanks) { updatedBanks ->
            selectedBanks = updatedBanks // 선택된 항목을 업데이트
        }
        recyclerView.adapter = adapter




        //    취소 버튼 클릭 이벤트
        btnCancel.setOnClickListener { dismiss() }

//    확인 버튼 클릭 이벤트
        btnConfirm.setOnClickListener {
            Log.d("IncomeBankChangeActivity", "확인 버튼 클릭됨, 선택된 은행: $selectedBanks")
            onConfirm(selectedBanks)
            dismiss()
        }

    }


}

//class IncomeBankChangeActivity(
//    context: Context,
//    private val selectedBanks: MutableMap<String, Boolean>,
//    private val onConfirm: (Map<String, Boolean>) -> Unit
//) : Dialog(context) {
//
//    private val binding: ActivityIncomeBankChangeBinding by lazy {
//        ActivityIncomeBankChangeBinding.inflate(layoutInflater)
//    }
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var buttonContainer: LinearLayout
//    private lateinit var adapter: SBankAdapter
//    private val banks = listOf("현금", "계좌이체", "카카오페이", "무통장입금")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // 다이얼로그 창 크기 조정
//        window?.setLayout(
//            (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        val btnCancel = binding.btnCancel
//        val btnConfirm = binding.btnConfirm
//        buttonContainer = binding.buttonContainer
//        recyclerView = binding.bankList   // 체크박스 리스트
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        // 리스트 초기화
//        adapter = SBankAdapter(banks) { selectedBank ->
//            // 모든 항목을 false로 설정
//            selectedBanks.keys.forEach { key -> selectedBanks[key] = false }
//
//            // 선택된 항목만 true로 설정
//            selectedBanks[selectedBank] = true
//            updateBankText() // 선택된 항목을 UI에 반영
//        }
//        recyclerView.adapter = adapter
//
//        // 취소 버튼 클릭 이벤트
//        btnCancel.setOnClickListener { dismiss() }
//
//        // 확인 버튼 클릭 이벤트
//        btnConfirm.setOnClickListener {
//            onConfirm(selectedBanks)
//            dismiss()
//        }
//    }
//
//    // 자산방식 클릭 시 다이얼로그에서 선택된 항목 텍스트 업데이트
//    private fun updateBankText() {
//        val selectedBankText = selectedBanks.filter { it.value }.keys.joinToString(", ")
//        binding..text = selectedBankText
//    }
//}

