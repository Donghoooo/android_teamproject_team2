package bitc.example.app.sagmin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivityDetailIncomeBinding
import bitc.example.app.databinding.ActivityDetailOutcomeBinding
import bitc.example.app.dto.ExpenseLogDTO
import bitc.example.app.ui.dialog.IncomeBankChangeActivity
import bitc.example.app.ui.dialog.IncomeCategoryChangeActivity
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


        binding.btnUpdate.setOnClickListener {

        }


        binding.btnDrop.setOnClickListener {

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

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}
