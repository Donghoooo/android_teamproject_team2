package bitc.example.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.databinding.ActivityOutcomeReceiptBinding
import org.w3c.dom.Text

class OutcomeReceiptActivity : AppCompatActivity() {

    private lateinit var moneyReceipt: TextView
    private lateinit var infoReceipt : TextView
    private lateinit var memoReceipt : TextView
    private lateinit var outcomeDialog: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityOutcomeReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener{
            finish()
        }
        moneyReceipt = binding.outcomeMoneyReceipt
        infoReceipt = binding.outcomeInfoReceipt
        memoReceipt = binding.outcomeMemoReceipt
        outcomeDialog = binding.outcomeDialogReceipt

        val money = intent.getStringExtra("text_value3")
        if (money != null){
            moneyReceipt.text = money
        }
        else{
            moneyReceipt.text = "No data receivced"
        }

        val memo = intent.getStringExtra("text_value4")
        if(money != null){
            memoReceipt.text = memo
        }
        else{
            memoReceipt.text = "No data receivce"
        }

        val info = intent.getStringExtra("text_value5")
        if (info != null){
            infoReceipt.text = info
        }
        else{
            infoReceipt.text = "No data receivce"
        }

        val dialog = intent.getStringExtra("outcomeDialog")
        if (dialog != null){
            outcomeDialog.text = dialog
        }
        else{
            outcomeDialog.text = "No data received"
        }
//        선택된 버튼의 텍스트 넘겨주기
        val selectedCategory = intent.getStringExtra("selectedCategory")
        binding.btnPassOutcome.text = selectedCategory



    }
}