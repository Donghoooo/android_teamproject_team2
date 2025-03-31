package bitc.example.app.sagmin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.Analyze_List
import bitc.example.app.AppServerClass
import bitc.example.app.MainActivity2
import bitc.example.app.databinding.ActivityOutcomeReceiptBinding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.ui.CateSearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutcomeReceiptActivity : AppCompatActivity() {

    private val binding : ActivityOutcomeReceiptBinding by lazy{
        ActivityOutcomeReceiptBinding.inflate(layoutInflater)
    }
    private lateinit var moneyReceipt: TextView
    private lateinit var infoReceipt: TextView
    private lateinit var memoReceipt: TextView
    private lateinit var outcomeDialog: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var outcomeDate : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        moneyReceipt = binding.outcomeMoneyReceipt
        infoReceipt = binding.outcomeInfoReceipt
        memoReceipt = binding.outcomeMemoReceipt
        outcomeDialog = binding.outcomeDialogReceipt
        outcomeDate = binding.date


        val money = intent.getStringExtra("text_value3")
        if (money != null) {
            moneyReceipt.text = money
        } else {
            moneyReceipt.text = "No data receivced"
        }

        val date = intent.getStringExtra("date")
        if (date != null){
            outcomeDate.text = date
        }
        else{
            outcomeDate.text = "No date received"
        }


        val memo = intent.getStringExtra("text_value4")
        if (money != null) {
            memoReceipt.text = memo
        } else {
            memoReceipt.text = "No data receivce"
        }

        val info = intent.getStringExtra("text_value5")
        if (info != null) {
            infoReceipt.text = info
        } else {
            infoReceipt.text = "No data receivce"
        }

        val dialog = intent.getStringExtra("outcomeDialog")
        if (dialog != null) {
            outcomeDialog.text = dialog
        } else {
            outcomeDialog.text = "No data received"
        }
//        선택된 버튼의 텍스트 넘겨주기
        val selectedCategory = intent.getStringExtra("selectedCategory")
        binding.btnPassOutcome.text = selectedCategory

        binding.btnSubmit.setOnClickListener {

            val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
            val memberId = sharedPreferences.getString("memberId", "아이디").toString()
            Log.d("fullstack503", memberId)

            val cate = binding.btnPassOutcome.text.toString()
            val outcomeMoney = binding.outcomeMoneyReceipt.text.toString()
            val outcomeSource = binding.outcomeDialogReceipt.text.toString()
            val outcomeMemo = binding.outcomeMemoReceipt.text.toString()
            val outcomeUse = binding.outcomeInfoReceipt.text.toString()
            val date = binding.date.text.toString()


            val outcome = ExpenseLogDTO()
            outcome.expenseDate = date
            outcome.memberId = memberId
            outcome.expenseCate = cate
            outcome.expenseMoney = outcomeMoney
            outcome.paymentOption = outcomeSource
            outcome.expenseMemo = outcomeMemo
            outcome.expenseUse = outcomeUse


            val api = AppServerClass.instance
            val call = api.postOutcome(outcome)
            retrofitResponse(call)
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

    }

    private fun retrofitResponse(call: Call<String>) {
        call.enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, res: Response<String>) {
                if (res.isSuccessful) {
                    // 서버에서 전달받은 데이터만 변수로 저장
                    val result = res.body()
                    Log.d("fullstack503", "result : $result")
                } else {
                    Log.d("fullstack503", "송신 실패")
                }
            }

            override fun onFailure(p0: Call<String>, t: Throwable) {
                Log.d("fullstack503", "message : $t.message")
            }
        })
    }
}