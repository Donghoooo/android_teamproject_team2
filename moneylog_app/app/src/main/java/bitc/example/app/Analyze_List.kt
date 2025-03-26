package bitc.example.app

import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.databinding.ActivityAnalyzeListBinding
import bitc.example.app.dto.IncomeLogDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Analyze_List : AppCompatActivity() {

    private val binding: ActivityAnalyzeListBinding by lazy {
        ActivityAnalyzeListBinding.inflate(layoutInflater)
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)  // "2025-03-23 14:30:00" 형식으로 날짜 반환
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

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
        binding.imageButton1.setOnClickListener {
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val formattedDate = "$year-${month}-$dayOfMonth"

                    // 선택된 날짜를 TextView에 출력
                    binding.textViewDate1.text = formattedDate

                }
            }, 2025, 3 - 1, 20).show()
        }

//        .......까지 날짜 선택하기
        binding.imageButton2.setOnClickListener {
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val formattedDate = "$year-${month}-$dayOfMonth"

                    // 선택된 날짜를 TextView에 출력
                    binding.textViewDate2.text = formattedDate

                }
            }, 2025, 3, 20).show()
        }



        binding.checkButton.setOnClickListener {
            Log.d("csy", "gettest1 시작")
            val api = AppServerClass.instance
            val call = api.getanalyze()

            call.enqueue(object : Callback<List<IncomeLogDTO>> {
                override fun onResponse(
                    p0: Call<List<IncomeLogDTO>>, res: Response<List<IncomeLogDTO>>
                ) {
                    if (res.isSuccessful) {
                        val result = res.body()
                        Log.d("csy", "result : $result")

                    } else {
                        Log.d("csy", "송신실패")
                    }
                }

                override fun onFailure(p0: Call<List<IncomeLogDTO>>, t: Throwable) {
                    Log.d("csy", "message : ${t.message}")
                }
            })
        }














//
////        확인 버튼 이벤트
//
//        binding.checkButton.setOnClickListener{
//            // 버튼 클릭 리스너 설정
//            binding.checkButton.setOnClickListener {
//                // 버튼 클릭 시 텍스트뷰에 정보 설정
//                binding.incomeMoney.text = "정보1"
//                binding.expenMoney.text = "정보2"
//
//
//                // 토스트 메시지 정보 알림
//                Toast.makeText(this, "정보를 가져왔습니다!", Toast.LENGTH_SHORT).show()
//            }
//
//
//
//        }


//        Fragment vs 수입/지출 버튼 색상 설정

        val fragmentManager:FragmentManager = supportFragmentManager
        var transaction: FragmentTransaction = fragmentManager.beginTransaction()

        val incomeFragment = InComeFragment()
        val expenFragment = ExpenFragment()


        transaction.add(R.id.btn_income_expen, expenFragment)
        binding.btnInput.setBackgroundColor(Color.parseColor("#F5F6F9")) // 기본 화면 지출 버튼 색상
        binding.btnOutput.setBackgroundColor(Color.parseColor("#FFFFFF")) // 기본 화면 지출 버튼 색상
        transaction.commit()


        binding.btnInput.setOnClickListener{
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.btn_income_expen,incomeFragment)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()

            if (it == binding.btnInput){
                binding.btnInput.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.btnOutput.setBackgroundColor(Color.parseColor("#F5F6F9"))
            }

        }

        binding.btnOutput.setOnClickListener{
            binding.btnOutput.setOnClickListener{
                transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.btn_income_expen,expenFragment)
                transaction.setReorderingAllowed(true)
                transaction.addToBackStack("")
                transaction.commit()

                if (it == binding.btnOutput){
                    binding.btnOutput.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    binding.btnInput.setBackgroundColor(Color.parseColor("#F5F6F9"))
                }
        }
            }

        }




//
//        // PieChart 초기화
//        val pieChart: PieChart = binding.pieChart
//
//        // 데이터 준비
//        val entries = ArrayList<PieEntry>()
//        entries.add(PieEntry(23f, "식비"))
//        entries.add(PieEntry(2f, "학비"))
//        entries.add(PieEntry(5f, "교통비"))
//        entries.add(PieEntry(12.5f, "생활용품"))
//        entries.add(PieEntry(20f, "월세"))
//        entries.add(PieEntry(12.5f, "외식"))
//
//        // PieDataSet 생성
//        val dataSet = PieDataSet(entries, "지철 정보")
//        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // 색상 지정
//        dataSet.valueTextSize = 18f // 값 텍스트 크기 설정
//
//        // PieData 생성
//        val data = PieData(dataSet)
//
//        // PieChart에 데이터 설정
//        pieChart.data = data
//
//        // 기타 설정
//        pieChart.isDrawHoleEnabled = true  // 원형 차트 내부에 홀(구멍) 그리기
//        pieChart.setHoleColor(android.R.color.white)  // 구멍 색상 설정
//        pieChart.setUsePercentValues(true)  // 퍼센트 값 표시
//        pieChart.invalidate()  // 차트 업데이트
    }


