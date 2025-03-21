package bitc.example.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.databinding.ActivityIncomeReceiptBinding
import bitc.example.app.dto.ExpenseLogDTO


class IncomeReceiptActivity : AppCompatActivity() {

    //    값을 저장할 incomeResultReceipt 변수
    private lateinit var incomeResultReceipt: TextView

    private lateinit var incomeInfo: TextView
    private lateinit var incomeMemo: TextView

private lateinit var incomePassReceipt : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityIncomeReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        incomeResultReceipt 는 incomeMoneyReceipt 를 binding 한다.
        incomeResultReceipt = binding.incomeMoneyReceipt
        incomeMemo = binding.incomeMemoReceipt
        incomeInfo = binding.incomeInfoReceipt
        incomePassReceipt = binding.incomeDialogReceipt
//        getStringExtra 는 PutExtra에서 text_value 라는 이름을 지정하여 그 text를 가져온다.
        val text = intent.getStringExtra("text_value3")
//        text의 값이 null이 아닐 경우 incomeResultReceipt에 저장된 값을 가져온다.
        if (text != null) {
            incomeResultReceipt.text = text
        } else {
            incomeResultReceipt.text = "No data received"
        }

        val info = intent.getStringExtra("text_value4")

        if (info != null) {
            incomeInfo.text = info
        } else {
            incomeInfo.text = "No data received"
        }

        val memo = intent.getStringExtra("text_value5")

        if (memo != null) {
            incomeMemo.text = memo
        }
        else{
            incomeMemo.text = "No data receivce"
        }

//
        val dialog = intent.getStringExtra("incomeDialog")

        if (dialog != null){
            incomePassReceipt.text = dialog
        }
        else{
            incomePassReceipt.text ="No data received"
        }

        val selectedCategory = intent.getStringExtra("selectedCategory")
        binding.btnPassIncome.text = selectedCategory

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            val expense = ExpenseLogDTO()

            val expenseMemo = binding.incomeMemoReceipt.text.toString()
            val expenseCate = binding.btnPassIncome.text.toString()

            expense.expenseMemo = expenseMemo
            expense.expenseCate = expenseCate
            Log.d("fullstack503","expense 값: $expenseCate , expnese 값:$expenseMemo")

        }
    }
}
