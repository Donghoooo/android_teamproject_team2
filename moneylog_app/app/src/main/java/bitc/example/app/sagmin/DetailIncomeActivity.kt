package bitc.example.app.sagmin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.R
import bitc.example.app.databinding.ActivityDetailIncomeBinding
import bitc.example.app.ui.dialog.IncomeBankChangeActivity
import bitc.example.app.ui.dialog.IncomeCategoryChangeActivity

class DetailIncomeActivity : AppCompatActivity() {

    private val binding: ActivityDetailIncomeBinding by lazy {
        ActivityDetailIncomeBinding.inflate(layoutInflater)
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


        binding.btnUpdate.setOnClickListener {

        }


        binding.btnDrop.setOnClickListener {

        }


        val incomeDate = intent.getStringExtra("incomeDate")
        val incomeCate = intent.getStringExtra("incomeCate")
        val incomeMoney = intent.getStringExtra("incomeMoney")

        findViewById<TextView>(R.id.detail_income_date).text = incomeDate
        findViewById<TextView>(R.id.btn_pass_income).text = incomeCate
        findViewById<TextView>(R.id.income_money_receipt).text = incomeMoney



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

    }
