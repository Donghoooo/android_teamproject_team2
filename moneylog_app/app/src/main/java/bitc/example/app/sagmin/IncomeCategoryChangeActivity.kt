package bitc.example.app.ui.dialog


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
import bitc.example.app.adapter.SCategoryAdapter
import bitc.example.app.databinding.ActivityIncomeCategoryChangeBinding


class IncomeCategoryChangeActivity(
    context: Context,
    private var selectedCategories: String? = null,
    private val onConfirm: (String?)  -> Unit
) : Dialog(context) {

    private val binding: ActivityIncomeCategoryChangeBinding by lazy {
        ActivityIncomeCategoryChangeBinding.inflate(layoutInflater)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonContainer: LinearLayout
    private lateinit var adapter: SCategoryAdapter
    private val categorys = listOf("월급", "용돈", "이월", "알바비", "기타")

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
        recyclerView = binding.categoryList   // 체크박스 리스트
        recyclerView.layoutManager = LinearLayoutManager(context)

//    리스트 초기화
        adapter = SCategoryAdapter(categorys, selectedCategories) { updatedCategories ->
            selectedCategories = updatedCategories.toString() // 선택된 항목을 업데이트
        }
        recyclerView.adapter = adapter




        //    취소 버튼 클릭 이벤트
        btnCancel.setOnClickListener { dismiss() }

//    확인 버튼 클릭 이벤트
        btnConfirm.setOnClickListener {
            Log.d("IncomeCategoryChangeActivity", "확인 버튼 클릭됨, 선택된 은행: $selectedCategories")
            onConfirm(selectedCategories)
            dismiss()
        }

    }


}