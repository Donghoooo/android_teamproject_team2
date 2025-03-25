package bitc.example.app.sagmin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivityDetailOutcomeBinding
import bitc.example.app.dto.ExpenseLogDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailOutcomeActivity : AppCompatActivity() {

    private val binding: ActivityDetailOutcomeBinding by lazy {
        ActivityDetailOutcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnUpdate.setOnClickListener {
            val cate = binding.btnPassOutcome.text.toString()
            val outcomeMoney = binding.outcomeMoneyReceipt.text.toString()
            val outcomeSource = binding.outcomeDialogReceipt.text.toString()
            val outcomeMemo = binding.outcomeMemoReceipt.text.toString()
            val outcomeUse = binding.outcomeInfoReceipt.text.toString()


            val outcome = ExpenseLogDTO()
            outcome.expenseCate = cate
            outcome.expenseMoney = outcomeMoney
            outcome.paymentOption = outcomeSource
            outcome.expenseMemo = outcomeMemo
            outcome.expenseUse = outcomeUse


            val api = AppServerClass.instance
            val call = api.postOutcome(outcome)
            retrofitResponse(call)
        }


        binding.btnDrop.setOnClickListener {

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