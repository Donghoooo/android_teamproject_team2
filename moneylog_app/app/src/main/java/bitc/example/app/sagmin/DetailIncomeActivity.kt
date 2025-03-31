package bitc.example.app.sagmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.R
import bitc.example.app.databinding.ActivityDetailIncomeBinding
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.ui.CateSearchActivity
import bitc.example.app.ui.dialog.IncomeBankChangeActivity
import bitc.example.app.ui.dialog.IncomeCategoryChangeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailIncomeActivity : AppCompatActivity() {

    private val binding: ActivityDetailIncomeBinding by lazy {
        ActivityDetailIncomeBinding.inflate(layoutInflater)
    }

    //  카테고리 선택
    private var selectedCategories : String? = null // 선택한 항목 저장

    //  자산방식 선택
    private var selectedBanks : String? = null

//




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener{
            finish()
        }




        binding.incomeDialogReceipt.setOnClickListener{
            val bankDialog = IncomeBankChangeActivity(this, selectedBanks ?: "") { selected ->
                selectedBanks = selected.toString()
                updateBankText()
            }
            bankDialog.show()


        }



        binding.btnPassIncome.setOnClickListener {
            val categoryDialog = IncomeCategoryChangeActivity(this, selectedCategories ?:""){ selected ->
                selectedCategories = selected.toString()
                updateCategoryText()
            }
            categoryDialog.show()
        }

        val incomeLogSeq = intent.getIntExtra("incomeLogSeq", -1)
        val incomeDate = intent.getStringExtra("incomeDate")
        val incomeCate = intent.getStringExtra("incomeCate")
        val incomeMoney = intent.getStringExtra("incomeMoney")
        val incomeSource = intent.getStringExtra("incomeSource")
        val incomeMemo = intent.getStringExtra("incomeMemo")
        val incomeUse = intent.getStringExtra("incomeUse")



        binding.incomeLogSeq.text = incomeLogSeq.toString()
        binding.detailIncomeDate.text = incomeDate
        binding.btnPassIncome.text = incomeCate
        binding.incomeMoneyReceipt.setText(incomeMoney)
        binding.incomeInfoReceipt.setText(incomeUse)
        binding.incomeMemoReceipt.setText(incomeMemo)
        binding.incomeDialogReceipt.text = incomeSource





        binding.btnUpdate.setOnClickListener {
            val cate = binding.btnPassIncome.text.toString()
            val money = binding.incomeMoneyReceipt.text.toString()
            val source = binding.incomeDialogReceipt.text.toString()
            val incomeMemo = binding.incomeMemoReceipt.text.toString()
            val incomeUse = binding.incomeInfoReceipt.text.toString()
            val seq = binding.incomeLogSeq.text.toString().toInt()

            var income = IncomeLogDTO()
            income.incomeLogSeq = seq
            income.incomeCate = cate
            income.incomeMoney = money
            income.incomeSource = source
            income.incomeMemo = incomeMemo
            income.incomeUse = incomeUse
            val api = AppServerClass.instance
            val call = api.updateIncome(income)
            retrofitResponse(call)
        }


        binding.btnDrop.setOnClickListener {
            val seq = binding.incomeLogSeq.text?.toString()?.toIntOrNull() ?: -1

            if (seq == -1) {
                Log.e("DetailIncomeActivity", "삭제할 수 없는 데이터입니다.")
                return@setOnClickListener
            }

            // 삭제 확인 다이얼로그
            AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("정말로 삭제하시겠습니까?")
                .setPositiveButton("삭제") { _, _ ->
                    val api = AppServerClass.instance
                    val call = api.deleteIncome(seq)
                    retrofitResponse(call) // 재사용 함수 호출
                }
                .setNegativeButton("취소", null)
                .show()

        }


        binding.calendarIcon.setOnClickListener{}

        binding.chartIcon.setOnClickListener {  }

        binding.userIcon.setOnClickListener {  }

        binding.listIcon.setOnClickListener {
            val intent = Intent(this,MonthlyListActivity::class.java)
            startActivity(intent)
        }

        binding.searchIcone.setOnClickListener {
            val intent = Intent(this, CateSearchActivity::class.java)
            startActivity(intent)
        }
    }
    //  카테고리 선택 부분 클릭 시 다이얼로그 표시
    private fun updateCategoryText() {
        binding.btnPassIncome.text = selectedCategories ?: "선택해주세요"

    }


    //  자산 방식 클릭 시 다이얼로그 표시
    private fun updateBankText(){
        binding.incomeDialogReceipt.text = selectedBanks ?: "선택해주세요"
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressedDispatcher.onBackPressed()
        return true
    }


    // Retrofit 통신 응답 부분
    // Callback<String> 부분이 서버에서 전달받을 데이터 타입임
    private fun retrofitResponse(call: Call<Int>) {
        call.enqueue(object : Callback<Int> {
            override fun onResponse(p0: Call<Int>, res: Response<Int>) {
                if (res.isSuccessful) {
                    // 서버에서 전달받은 데이터만 변수로 저장
                    val result = res.body()
                    Log.d("fullstack503", "result : $result")

                    val intent = Intent(this@DetailIncomeActivity, MonthlyListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish() // 현재 액티비티 종료
                }
                else {
                    Log.d("fullstack503", "송신 실패")
                }
            }

            override fun onFailure(p0: Call<Int>, t: Throwable) {
                Log.d("fullstack503", "message : $t.message")
            }
        })
    }
}