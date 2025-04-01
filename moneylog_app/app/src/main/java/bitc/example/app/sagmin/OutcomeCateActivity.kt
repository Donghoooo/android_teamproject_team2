package bitc.example.app.sagmin

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.Analyze_List
import bitc.example.app.MainActivity2
import bitc.example.app.R
import bitc.example.app.databinding.ActivityOutcomeCateBinding
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.ui.CateSearchActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OutcomeCateActivity : AppCompatActivity() {

//    =============== 달력 표시 ===========

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private lateinit var dateText : TextView
    private lateinit var date: TextView


    //    TextView를 저장할 변수
//    lateinit 키워드는 변수가 나중에 초기화 될 것임을 나타낸다
    private lateinit var outResult : TextView
    private lateinit var outcomeDialogCate: AppCompatButton
    private lateinit var btnSubmit : AppCompatButton
    private lateinit var outcomeInfo :EditText
            private lateinit var outcomeMemo : EditText
            private lateinit var outcomeMoney : TextView

            private lateinit var outcomeDialog : AppCompatButton


    private var selectedButton: AppCompatButton? = null

    private lateinit var btn10: AppCompatButton
    private lateinit var btn11: AppCompatButton
    private lateinit var btn12: AppCompatButton
    private lateinit var btn13: AppCompatButton
    private lateinit var btn14: AppCompatButton
    private lateinit var btn15: AppCompatButton
    private lateinit var btn16: AppCompatButton
    private lateinit var btn17: AppCompatButton
    private lateinit var btn18: AppCompatButton
    private lateinit var btn19: AppCompatButton
    private lateinit var btn20: AppCompatButton
    private lateinit var btn21: AppCompatButton
    private lateinit var btn22: AppCompatButton
    private lateinit var btn23: AppCompatButton
    private lateinit var btn24: AppCompatButton
    private lateinit var btnEtc : AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityOutcomeCateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        뒤로가기
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
//   버튼 초기화
        btn10 = binding.btn10
        btn11 = binding.btn11
        btn12 = binding.btn12
        btn13 = binding.btn13
        btn14 = binding.btn14
        btn15 = binding.btn15
        btn16 = binding.btn16
        btn17 = binding.btn17
        btn18 = binding.btn18
        btn19 = binding.btn19
        btn20 = binding.btn20
        btn21 = binding.btn21
        btn22 = binding.btn22
        btn23 = binding.btn23
        btn24 = binding.btn24
        btnEtc = binding.btnEtc

        btn10.setOnClickListener { onCategorySelected(btn10) }
        btn11.setOnClickListener { onCategorySelected(btn11) }
        btn12.setOnClickListener { onCategorySelected(btn12) }
        btn13.setOnClickListener { onCategorySelected(btn13) }
        btn14.setOnClickListener { onCategorySelected(btn14) }
        btn15.setOnClickListener { onCategorySelected(btn15) }
        btn16.setOnClickListener { onCategorySelected(btn16) }
        btn17.setOnClickListener { onCategorySelected(btn17) }
        btn18.setOnClickListener { onCategorySelected(btn18) }
        btn19.setOnClickListener { onCategorySelected(btn19) }
        btn20.setOnClickListener { onCategorySelected(btn20) }
        btn21.setOnClickListener { onCategorySelected(btn21) }
        btn22.setOnClickListener { onCategorySelected(btn22) }
        btn23.setOnClickListener { onCategorySelected(btn23) }
        btn24.setOnClickListener { onCategorySelected(btn24) }
        btnEtc.setOnClickListener { onCategorySelected(btnEtc) }





//        =================== 작성한 내역 영수증페이지로 값 넘겨주기 ============================

        outcomeInfo = binding.outcomeInfoCate
        outcomeMemo = binding.outcomeMemoCate
        outcomeMoney = binding.outcomeResultCate
        outcomeDialog = binding.outcomeDialogCate
        btnSubmit = binding.btnSubmit
        date = binding.date

        binding.btnSubmit.setOnClickListener {
            val money =outcomeMoney.text.toString()
            val memo = outcomeMemo.text.toString()
            val info = outcomeInfo.text.toString()
            val dialog = outcomeDialog.text.toString()
            val selectedCategory = selectedButton?.text.toString()
            val date = date.text.toString()



            val intent = Intent(this, OutcomeReceiptActivity::class.java).apply{
                putExtra("text_value3",money)
                putExtra("text_value4",memo)
                putExtra("text_value5",info)
                putExtra("outcomeDialog",dialog)
                putExtra("selectedCategory",selectedCategory)
                putExtra("date",date)

            }
            startActivity(intent)
        }

        binding.calendarIcon.setOnClickListener{  val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent) }

        binding.chartIcon.setOnClickListener {
            val intent = Intent(this, Analyze_List::class.java)
            startActivity(intent)
        }

        binding.userIcon.setOnClickListener { val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)  }

        binding.listIcon.setOnClickListener {
            val intent = Intent(this,MonthlyListActivity::class.java)
            startActivity(intent)
        }

        binding.searchIcone.setOnClickListener {
            val intent = Intent(this, CateSearchActivity::class.java)
            startActivity(intent)
        }


//        =========================== 텍스트 넘겨받기 ==============================================


//        변수 초기화 : outResult 는 binding.tvResult로 초기화된다.
//        TextView (tv_result)를 바인딩하는 코드이다.
        outResult = binding.outcomeResultCate


//        intent.getStringExtra 는 Intent를 통해 전달된 데이터를 가져온다.
//        text_value 는 Intent에서 데이터를 넣을 때 사용한 키 (전달하는 엑티비티에서 설정함)
        val text = intent.getStringExtra("text_value")

//        intent로 전달된 값이 null 이 아니라면 outResult 즉, TextView에서 전달된 텍스트를 표시한다.
        if (text != null){
            outResult.text = text
        }
        else{
            outResult.text = "No data received"
        }


        val dateText = intent.getStringExtra("date")

        if (dateText != null){
            date.text = dateText
        }
        else{
            date.text = "No date received"
        }




//========================================== 드롭다운 ======================================================

        binding.outcomeDialogCate.setOnClickListener {
            showOutcomeCate(binding)
        }
        outcomeDialogCate = binding.outcomeDialogCate

    }

    // 버튼 선택 함수
    private fun onCategorySelected(button: AppCompatButton) {
        selectedButton?.let {
            it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.default_button_color)
        }

        button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selected_button_color)
        selectedButton = button

        updateSubmitButtonState()
    }

    private fun showOutcomeCate(binding: ActivityOutcomeCateBinding) {
        val items = arrayOf("현금", "이체", "체크카드", "신용카드")
        var selectedItemIndex = -1 // 아무것도 선택 안 된 상태로 초기화

        val builder = AlertDialog.Builder(this)
        builder.setTitle("지불수단을 선택하세요")
            .setSingleChoiceItems(items, -1) { _, which ->
                selectedItemIndex = which // 선택된 인덱스 저장
            }
            .setPositiveButton("확인") { _, _ ->
                // 사용자가 지불수단을 선택한 경우에만 업데이트
                if (selectedItemIndex != -1) {
                    binding.outcomeDialogCate.text = items[selectedItemIndex]
                    updateSubmitButtonState()
                }
            }
            .setNegativeButton("취소", null) // 아무것도 하지 않음
            .setCancelable(true)
            .show()
    }

    private fun updateSubmitButtonState() {
        val isCategorySelected = selectedButton != null // 카테고리 선택 여부
        val isPaymentMethodSelected = outcomeDialogCate.text.isNotEmpty() && outcomeDialogCate.text != "선택해주세요" // 지불수단 선택 여부

        // 두 가지가 모두 선택되어야 btnSubmit 활성화
        btnSubmit.isEnabled = isCategorySelected && isPaymentMethodSelected
    }


    }

