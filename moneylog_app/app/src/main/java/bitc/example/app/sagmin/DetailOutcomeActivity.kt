package bitc.example.app.sagmin

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.Analyze_List
import bitc.example.app.AppServerClass
import bitc.example.app.R
import bitc.example.app.databinding.ActivityDetailOutcomeBinding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.sdh.MyPageCheckActivity
import bitc.example.app.ui.CateSearchActivity
import bitc.example.app.ui.dialog.IncomeBankChangeActivity
import bitc.example.app.ui.dialog.OutcomeCategoryChangeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailOutcomeActivity : AppCompatActivity() {

    private val binding: ActivityDetailOutcomeBinding by lazy {
        ActivityDetailOutcomeBinding.inflate(layoutInflater)
    }

    //  카테고리 선택
    private var selectedCategories : String? = null // 선택한 항목 저장

    //  자산방식 선택
    private var selectedBanks : String? = null


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


        binding.calendarIcon.setOnClickListener{}

        binding.chartIcon.setOnClickListener {
            val intent = Intent(this,Analyze_List::class.java)
            startActivity(intent)
        }

        binding.userIcon.setOnClickListener { val intent = Intent(this,MyPageActivity::class.java)
            startActivity(intent)  }

        binding.listIcon.setOnClickListener {
            val intent = Intent(this,MonthlyListActivity::class.java)
            startActivity(intent)
        }

        binding.searchIcone.setOnClickListener {
            val intent = Intent(this, CateSearchActivity::class.java)
            startActivity(intent)
        }


        binding.outcomeDialogReceipt.setOnClickListener{
            val bankDialog = IncomeBankChangeActivity(this, selectedBanks ?: "") { selected ->
                selectedBanks = selected.toString()
                updateBankText()
            }
            bankDialog.show()


        }



        binding.btnPassOutcome.setOnClickListener {
            val categoryDialog = OutcomeCategoryChangeActivity(this, selectedCategories ?:""){ selected ->
                selectedCategories = selected.toString()
                updateCategoryText()
            }
            categoryDialog.show()
        }

        val expenseLogSeq = intent.getIntExtra("expenseLogSeq",-1)
        val expenseCate = intent.getStringExtra("expenseCate")
        val expenseMoney = intent.getStringExtra("expenseMoney")
        val expenseMemo = intent.getStringExtra("expenseMemo")
        val paymentOption =intent.getStringExtra("paymentOption")
        val expenseUse = intent.getStringExtra("expenseUse")
        val expenseDate = intent.getStringExtra("expenseDate")

        binding.outcomeLogSeq.text = expenseLogSeq.toString()
        binding.btnPassOutcome.text = expenseCate
        binding.outcomeMoneyReceipt.setText(expenseMoney)
        binding.outcomeMemoReceipt.setText(expenseMemo)
        binding.outcomeDialogReceipt.text = paymentOption
        binding.outcomeInfoReceipt.setText(expenseUse)
        binding.detailOutcomeDate.text = expenseDate


        binding.btnUpdate.setOnClickListener {
            val cate = binding.btnPassOutcome.text.toString()
            val money = binding.outcomeMoneyReceipt.text.toString()
            val payment = binding.outcomeDialogReceipt.text.toString()
            val date = binding.detailOutcomeDate.text.toString()
            val memo = binding.outcomeMemoReceipt.text.toString()
            val use = binding.outcomeInfoReceipt.text.toString()
            val seq = binding.outcomeLogSeq.text.toString().toInt()


            var expense = ExpenseLogDTO()
            expense.expenseUse = use
            expense.expenseCate = cate
            expense.expenseMemo = memo
            expense.expenseDate = date
            expense.expenseLogSeq = seq
            expense.expenseMoney = money
            expense.paymentOption = payment

            val api = AppServerClass.instance
            val call = api.updateOutcome(expense)
            retrofitResponse(call)

            Log.d("DetailOutcomeActivity", "expenseUse: $use")
            Log.d("DetailOutcomeActivity", "expenseCate: $cate")
            Log.d("DetailOutcomeActivity", "expenseMoney: $money")
            Log.d("DetailOutcomeActivity", "expenseMemo: $memo")
            Log.d("DetailOutcomeActivity", "paymentOption: $payment")
            Log.d("DetailOutcomeActivity", "expenseDate: $date")
            Log.d("DetailOutcomeActivity", "expenseLogSeq: $seq")
        }



        binding.btnDrop.setOnClickListener {
            val seq = binding.outcomeLogSeq.text?.toString()?.toIntOrNull() ?: -1

            if (seq == -1) {
                Log.e("DetailOutcomeActivity", "삭제할 수 없는 데이터입니다.")
                return@setOnClickListener
            }

            // 삭제 확인 다이얼로그
            val dialog = AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("정말로 삭제하시겠습니까?")
                .setPositiveButton("삭제") { _, _ ->
                    val api = AppServerClass.instance
                    val call = api.deleteOutcome(seq)
                    retrofitResponse(call) // 재사용 함수 호출
                }
                .setNegativeButton("취소", null)
                .create()



//            dialog.setOnDismissListener {
//                // 다이얼로그가 닫힐 때 finish() 호출
//                finish()
//            }
            dialog.show()
        }
    }
    //  카테고리 선택 부분 클릭 시 다이얼로그 표시
    private fun updateCategoryText() {
            binding.btnPassOutcome.text = selectedCategories ?: "선택해주세요"

        }


        //  자산 방식 클릭 시 다이얼로그 표시
    private fun updateBankText(){
        binding.outcomeDialogReceipt.text = selectedBanks ?: "선택해주세요"
    }

    override fun onPause() {
        super.onPause()

        // 소프트 키보드가 열려 있으면 닫기
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()

        // 입력 채널이 제대로 해제되도록 명시적으로 처리
        window?.decorView?.rootView?.clearFocus()
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

                    val intent = Intent(this@DetailOutcomeActivity, MonthlyListActivity::class.java)
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
