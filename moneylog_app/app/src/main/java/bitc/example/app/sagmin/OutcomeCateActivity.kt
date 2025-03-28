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
import bitc.example.app.R
import bitc.example.app.databinding.ActivityOutcomeCateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OutcomeCateActivity : AppCompatActivity() {

//    =============== 달력 표시 ===========
private lateinit var startDate: TextView
    private lateinit var startDatePicker: ImageView
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())



    //    TextView를 저장할 변수
//    lateinit 키워드는 변수가 나중에 초기화 될 것임을 나타낸다
    private lateinit var outResult : TextView

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





//        =================== 작성한 내역 영수증페이지로 값 넘겨주기 ============================


        outcomeInfo = binding.outcomeInfoCate
        outcomeMemo = binding.outcomeMemoCate
        outcomeMoney = binding.outcomeResultCate
        outcomeDialog = binding.outcomeDialogCate
        btnSubmit = binding.btnSubmit
        startDate = binding.date

        binding.btnSubmit.setOnClickListener {
            val money =outcomeMoney.text.toString()
            val memo = outcomeMemo.text.toString()
            val info = outcomeInfo.text.toString()
            val dialog = outcomeDialog.text.toString()
            val selectedCategory = selectedButton?.text.toString()
            val date = startDate.text.toString()



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





//========================================== 드롭다운 ======================================================

//        btnDialog 버튼을 누르면 이벤트가 실행되도록 설정
        binding.outcomeDialogCate.setOnClickListener {
//            버튼을 누르면 showBtnDialog 함수가 호출 된다.
//            binding 은 ActivityOutcomeCateBinding의 객체로, 레이아웃의 뷰들을 바인딩하여 쉽게 접근하도록 해줌
            showBtnDialog(binding)
        }


//        ========================= 달력 아이콘 선택 시, 날짜 선택 ===========================

//    날짜 선택
        startDate = binding.date
        startDatePicker = binding.startDatePicker


//    오늘날짜 기본값 설정
        val today = Calendar.getInstance()
        val todayDate = dateFormat.format(today.time)
        startDate.text = todayDate


//    시작 날짜 캘린더 아이콘 클릭 이벤트
        startDatePicker.setOnClickListener {
            showDatePicker(startDate)
        }

    }

//    /paymentMethodSelectButton 버튼 아이디

    private fun showBtnDialog(binding: ActivityOutcomeCateBinding){
//        드롭다운 종류 설정
        val items = arrayOf("현금","이체","체크카드","신용카드")
//        선택된 항목을 저장할 변수 = selectedItemIndex
        var selectedItemIndex = 0

//         AlertDialog.Builder 는 다이얼로그를 만드는 빌더 객체, this 는 현재 Activity를 의미
        val builder = AlertDialog.Builder(this)

//         다이얼로그의 제목을 설정
        builder.setTitle("지불수단을 선택하세요")

//            setSingleChoiceItems 항목을 단일 선택. items는 배열,기본 선택항목이 selectedItemIndex 이다.
            .setSingleChoiceItems(items, selectedItemIndex) {
//    사용자가 항목을 선택할때마다 which 값으로 선택된 항목의 인덱스가 전달, 이를 selectedItemIndex 에 저장
                dialog, which -> selectedItemIndex = which }

//               setPositiveButton("확인") 확인 버튼을 추가
            .setPositiveButton("확인") {
//               사용자가 선택한 항목을 selectedItem에 저장
                dialog, which -> val selectedItem = items[selectedItemIndex]
//                버튼의 텍스트를 사용자가 선택한 항목으로 변경한다.
                binding.outcomeDialogCate.text = selectedItem
                Toast.makeText(this, "선택된 항목: $selectedItem", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소", null)
            .setCancelable(true) // 뒤로 가기 버튼으로 닫히게 설정
            .show() // 다이얼로그
            }
//    ======================================================================================================
//      binding.btnBack.setOnClickListener{
//            finish()
//        }
// 버튼 선택 함수
private fun onCategorySelected(button: AppCompatButton) {
    // 선택된 버튼이 있으면 원래 상태로 복원 (배경색 초기화)
    selectedButton?.let {
        // 이전에 선택된 버튼의 배경색을 기본 배경색으로 변경
        it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.default_button_color)
    }

    // 현재 클릭된 버튼을 선택된 버튼으로 설정하고 배경색 변경
    // 선택된 버튼의 배경색을 지정
    button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selected_button_color)

    // 현재 선택된 버튼을 저장
    selectedButton = button

    btnSubmit.isEnabled = selectedButton != null
    btnSubmit.isEnabled = outcomeDialog != null
}


//    ==================== 달력 날짜 선택 설정 ===================================
private fun showDatePicker(textView: TextView) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
        val selectedDate = Calendar.getInstance()
        selectedDate.set(selectedYear, selectedMonth, selectedDay)
        textView.text = dateFormat.format(selectedDate.time)
    }, year, month, day)

    datePickerDialog.show()
}
    }

