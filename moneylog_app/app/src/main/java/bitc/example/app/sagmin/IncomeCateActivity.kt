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
import bitc.example.app.R
import bitc.example.app.databinding.ActivityIncomeCateBinding
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.ui.CateSearchActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class IncomeCateActivity : AppCompatActivity() {

//    달력 표시
    private lateinit var startDate: TextView
    private lateinit var startDatePicker: ImageView
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())



    private lateinit var incomeResult: TextView

    //  입력한 금액을 보내기에 필요한 금액 텍스트뷰 변수와 확인버튼 변수
    private lateinit var incomeResultReceipt: TextView
    private lateinit var btnSubmit: AppCompatButton

    //    입력한 내역 , 메모 변수 생성
    private lateinit var incomeInfo: EditText
    private lateinit var incomeMemo: EditText


    //    버튼의 값을 저장하고 선택을 누를시 버튼의 값도 다음페이지로 같이 전달, 버튼의 값을 저장할 변수와 버튼 선언
    private lateinit var incomeDialog: AppCompatButton

//    선택될 버튼의 값 변수 초기화
    private var selectedButton: AppCompatButton? = null


    private lateinit var btnAllowance: AppCompatButton
    private lateinit var btnSalary: AppCompatButton
    private lateinit var btnPartmoney: AppCompatButton
    private lateinit var btnDutch: AppCompatButton
    private lateinit var btnEtc: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityIncomeCateBinding.inflate(layoutInflater)
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

//        =========================== 달력 날짜 선택하기 =========================================

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




//  =================== 버튼 선택 후 그 버튼의 정보들을 넘겨주기 ===================================
        btnAllowance = binding.btnAllowance
        btnSalary = binding.btnSalary
        btnPartmoney = binding.btnPartmoney
        btnDutch = binding.btnDutch
        btnEtc = binding.btnEtc

        btnAllowance.setOnClickListener { onCategorySelected(btnAllowance) }
        btnSalary.setOnClickListener { onCategorySelected(btnSalary) }
        btnPartmoney.setOnClickListener { onCategorySelected(btnPartmoney) }
        btnDutch.setOnClickListener { onCategorySelected(btnDutch) }
        btnEtc.setOnClickListener { onCategorySelected(btnEtc) }






//==================== 입력한 매모, 내용, 금액 전부 넘겨주기 =====================

//        incomeResultReceipt 이란 변수는 incomeCate의 incomeResultCate 를 binding한다.
        incomeResultReceipt = binding.incomeResultCate
        incomeInfo = binding.incomeInfoCate
        incomeMemo = binding.incomeMemoCate
//        btnSubmit 이란 변수는 incomeCate 의 btnSubmit 을 binding 한다.
        btnSubmit = binding.btnSubmit
        startDate = binding.date

//        확인 버튼을 누를 시 발생되는 이벤트
        binding.btnSubmit.setOnClickListener {
//            incomeResultReceipt에 저장된 값을 문자열로 변환하여 text에 저장한다.
            val text = incomeResultReceipt.text.toString()
            val info = incomeInfo.text.toString()
            val memo = incomeMemo.text.toString()
            val dialog = incomeDialog.text.toString()
            val selectedCategory = selectedButton?.text.toString()
            val date = startDate.text.toString()


//            intent를 사용하여 incomeReceiptActivity에 접근가능하게하고
//            putExtra에 설정한 "text_value3" 이라는 text 문자열에 저장
            val intent = Intent(this, IncomeReceiptActivity::class.java).apply {
                putExtra("text_value3", text)
                putExtra("text_value4", info)
                putExtra("text_value5", memo)
                putExtra("incomeDialog",dialog)
                putExtra("selectedCategory",selectedCategory)
                putExtra("date",date)
            }
            startActivity(intent)
        }

        binding.calendarIcon.setOnClickListener{}

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


//        =========================== addInfo 의 TextView 넘겨주기 ===========================




        incomeResult = binding.incomeResultCate

        val text = intent.getStringExtra("text_value2")

        if (text != null) {
            incomeResult.text = text
        } else {
            incomeResult.text = "No data received"
        }

//    =========================================== 드롭다운 선택 =======================================
        incomeDialog = binding.incomeDialogCate

        binding.incomeDialogCate.setOnClickListener {
            showIncomeCate(binding)
        }
    }

//    =============================== 날짜 캘린더 설정 =============================================
private fun showDatePicker(textView: TextView) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)



    val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
        val selectedDate = Calendar.getInstance()
        selectedDate.set(selectedYear, selectedMonth, selectedDay)
        selectedDate.set(Calendar.HOUR_OF_DAY, 0)
        selectedDate.set(Calendar.MINUTE, 0)
        selectedDate.set(Calendar.SECOND, 0)
        selectedDate.set(Calendar.MILLISECOND, 0)

        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
        textView.text = formattedDate
    }, year, month, day)




    datePickerDialog.show()
}

    //  현금 결제 수단 선택
    private fun showIncomeCate(binding: ActivityIncomeCateBinding) {
        val items = arrayOf("현금", "계좌이체", "카카오페이", "무통장입금")

        var selectedItemIndex  = 0

        val builder = AlertDialog.Builder(this)

        builder.setTitle("입금경로를 입력하세요")
            .setSingleChoiceItems(items, selectedItemIndex ) { dialog, which ->
                selectedItemIndex  = which
            }
            .setPositiveButton("확인") { dialog, which ->
                val selectedItemIndex  = items[selectedItemIndex ]
                binding.incomeDialogCate.text = selectedItemIndex
                Toast.makeText(this, "선택된 항목 :$selectedItemIndex ", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("취소", null)
            .setCancelable(true)
            .show()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    private fun onCategorySelected(button: AppCompatButton){
        // 선택된 버튼이 있으면 원래 상태로 복원 (배경색 초기화)
        selectedButton?.let {
            // 이전에 선택된 버튼의 배경색을 기본 배경색으로 변경
            it.backgroundTintList = ContextCompat.getColorStateList(this,
                R.color.default_button_color
            )
        }

        // 현재 클릭된 버튼을 선택된 버튼으로 설정하고 배경색 변경
        // 선택된 버튼의 배경색을 지정
        button.backgroundTintList = ContextCompat.getColorStateList(this,
            R.color.selected_button_color
        )

        // 현재 선택된 버튼을 저장
        selectedButton = button

        btnSubmit.isEnabled = selectedButton != null
        btnSubmit.isEnabled = incomeDialog != null
        btnSubmit.isEnabled = startDate != null

    }
}