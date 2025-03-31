package bitc.example.app

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.databinding.ActivityAnalyzeListBinding
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.kms.MonthlyListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Analyze_List : AppCompatActivity(), InComeFragment.totalIncome, ExpenFragment.totalExpen {

    private val binding: ActivityAnalyzeListBinding by lazy {
        ActivityAnalyzeListBinding.inflate(layoutInflater)
    }
    lateinit var incomeTotal: String
    lateinit var expenTotal: String

    var startDate : String = ""
    var endDate : String = ""

    // 인터페이스 정의
//    interface DateSelectionListener {
//        fun onDateSelected(startDate: String, endDate: String)
//    }


    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)  // "2025-03-23 14:30:00" 형식으로 날짜 반환
    }


    override fun totalIncome(data: String) {
        incomeTotal = data
        binding.incomeMoney.text = incomeTotal + "원"
        Log.d("fullstack503", "main$data")
    }

    override fun totalExpen(data1: String) {
        expenTotal = data1
        binding.expenMoney.text = expenTotal + "원"

        Log.d("fullstack503", "main$data1")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            // MainActivity로 이동
            val intent = Intent(this, MonthlyListActivity::class.java)
            startActivity(intent)
            finish()  // 현재 Activity를 종료하고 MainActivity로 이동
        }


        //        Fragment vs 수입/지출 버튼 색상 설정

        val fragmentManager: FragmentManager = supportFragmentManager
        var transaction: FragmentTransaction = fragmentManager.beginTransaction()

        val incomeFragment = InComeFragment()
        val expenFragment = ExpenFragment()


        transaction.add(R.id.btn_income_expen, expenFragment)
        binding.btnInput.setBackgroundColor(Color.parseColor("#F5F6F9")) // 기본 화면 수입 버튼 색상
        binding.btnOutput.setBackgroundColor(Color.parseColor("#FFFFFF")) // 기본 화면 지출 버튼 색상
        transaction.commit()


        binding.btnInput.setOnClickListener {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.btn_income_expen, incomeFragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()

            if (it == binding.btnInput) {
                binding.expenMoney.text = "000,000"
                binding.btnInput.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.btnOutput.setBackgroundColor(Color.parseColor("#F5F6F9"))
            }
        }

        binding.btnOutput.setOnClickListener {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.btn_income_expen, expenFragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()

            if (it == binding.btnOutput) {
                binding.incomeMoney.text = "000,000"
                binding.btnOutput.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.btnInput.setBackgroundColor(Color.parseColor("#F5F6F9"))
            }
        }

        // 현재 날짜와 시간 얻기
        val currentDate = getCurrentDateTime()

        // TextView에 날짜와 시간 설정
        binding.textViewDate1.text = currentDate
        binding.textViewDate2.text = currentDate

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//       .......부터 날짜 선택하기
        binding.timeStart.setOnClickListener {
            // 현재 날짜를 가져오기 위한 Calendar 객체
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)  // 월은 0부터 시작하므로 현재 월을 그대로 사용
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth" // month는 0부터 시작하므로 +1

                    // 선택된 날짜를 TextView에 출력
                    binding.textViewDate1.text = formattedDate
                    startDate = formattedDate
                }
            }, year, month, dayOfMonth).show()
        }

//        .......까지 날짜 선택하기
        binding.timeEnd.setOnClickListener {
            // 현재 날짜를 가져오기 위한 Calendar 객체
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)  // 월은 0부터 시작하므로 현재 월을 그대로 사용
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth" // month는 0부터 시작하므로 +1

                    // 선택된 날짜를 TextView에 출력
                    binding.textViewDate2.text = formattedDate
                    endDate = formattedDate
                }
            }, year, month, dayOfMonth).show()
        }


        // "check_button" 클릭 시 현재 표시된 Fragment에 선택된 날짜(startDate, endDate) 전달
        binding.checkButton.setOnClickListener {
            Log.d("startDate", "startDate : $startDate")
            Log.d("startDate", "endDate : $endDate")

            val args = Bundle()
            args.putString("startDate", startDate)
            args.putString("endDate", endDate)

            incomeFragment.arguments = args
            expenFragment.arguments = args

            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.btn_income_expen, incomeFragment)
            transaction.replace(R.id.btn_income_expen, expenFragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()


            // 현재 Fragment 찾기 (btn_income_expen 영역에 표시된 Fragment)
//            val currentFragment = supportFragmentManager.findFragmentById(binding.btnIncomeExpen.id)
            // 현재 Fragment가 DateSelectionListener 인터페이스를 구현하고 있다면, onDateSelected 호출하여 데이터 전달
//            if (currentFragment is DateSelectionListener) {
//                currentFragment.onDateSelected(startDate, endDate)
//            }
        }
    }

    // DateSelectionListener 인터페이스 구현 - 선택된 날짜를 로그로 출력
//    override fun onDateSelected(startDate: String, endDate: String) {
//        Log.d("Analyze_List", "Selected Dates: $startDate ~ $endDate")
//    }
}


