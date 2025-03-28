package bitc.example.app.sagmin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.databinding.ActivityAddInfoBinding
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.ui.CateSearchActivity

class AddInfoActivity : AppCompatActivity() {

//
private lateinit var binding: ActivityAddInfoBinding

    private lateinit var outcomeResult : TextView
    private lateinit var btnOutcome: AppCompatButton

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var incomeResult : TextView
    private lateinit var btnIncome: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         binding = ActivityAddInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //        입력한 금액이 기본값 0원일 경우 수입, 지출 버튼이 눌러지지 않음
        binding.tvResult.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkSubmitButtonState()
            }
            override fun afterTextChanged(editable: Editable?) {}

        })

        checkSubmitButtonState()


//        ================Addinfo에서 입력한 금액과 수입 선택 시 입금 카테 선택페이지로 넘기기 =======




        incomeResult = binding.tvResult
        btnIncome = binding.btnIncome

        binding.btnIncome.setOnClickListener {
            val text = incomeResult.text.toString()

            val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
            val memberId = sharedPreferences.getString("memberId", "아이디").toString()
            Log.d("fullstack503", memberId)




            val intent = Intent(this, IncomeCateActivity::class.java).apply{
            putExtra("text_value2",text)
            }
            startActivity(intent)
        }



//  ==================================== Addinfo에서 입력한 금액과 지출 선택시 지출 카테 선택페이지로 넘기기
        outcomeResult = binding.tvResult
        btnOutcome = binding.btnOutcome


        binding.btnOutcome.setOnClickListener {
//            btnOutcome 버튼을 클릭하면 tvResult에 표시된 텍스트를 변수 text에 저장한다.
            val text = outcomeResult.text.toString()


//            intent를 사용하여 OutcomeCateActivity 로 전달한다.
//            putExtra 메소드로 text_value 라는 키에 text를 넣어 전달한다.
            val intent = Intent(this, OutcomeCateActivity::class.java).apply{
                putExtra("text_value",text)
            }
            startActivity(intent)
        }


// =======================================================================================================

//        뒤로가기버튼
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btn0.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "0"
            }
            else {
                num += "0"
            }
            binding.tvResult.text = num
        }

        binding.btn1.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "1"
            }
            else {
                num += "1"
            }
            binding.tvResult.text = num
        }

        binding.btn2.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "2"
            }
            else {
                num += "2"
            }
            binding.tvResult.text = num
        }

        binding.btn3.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "3"
            }
            else {
                num += "3"
            }
            binding.tvResult.text = num
        }

        binding.btn4.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "4"
            }
            else {
                num += "4"
            }
            binding.tvResult.text = num
        }

        binding.btn5.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "5"
            }
            else {
                num += "5"
            }
            binding.tvResult.text = num
        }

        binding.btn6.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "6"
            }
            else {
                num += "6"
            }
            binding.tvResult.text = num
        }

        binding.btn7.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "7"
            }
            else {
                num += "7"
            }
            binding.tvResult.text = num
        }

        binding.btn8.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "8"
            }
            else {
                num += "8"
            }
            binding.tvResult.text = num
        }

        binding.btn9.setOnClickListener {
            var num = binding.tvResult.text.toString()
            if (num == "0원") {
                num = "9"
            }
            else {
                num += "9"
            }
            binding.tvResult.text = num
        }

        binding.btnClear.setOnClickListener{
            binding.tvResult.text = "0원"
        }



        binding.btnDelete.setOnClickListener {
            var num = binding.tvResult.text.toString()

            if(num != "0원"){
                num = if (num.length > 1){
                    num.substring(0, num.length - 1)
                }
                else{
                    "0원"
                }
                binding.tvResult.text = num
            }
        }

        binding.calendarIcon.setOnClickListener{}

        binding.chartIcon.setOnClickListener {  }

        binding.userIcon.setOnClickListener {  }

        binding.listIcon.setOnClickListener {
            val intent = Intent(this,MonthlyListActivity::class.java)
            startActivity(intent)
        }

        binding.searchIcone.setOnClickListener {
            val intent = Intent(this,CateSearchActivity::class.java)
            startActivity(intent)
        }





    }
    private fun checkSubmitButtonState(){
        val result = binding.tvResult.text.toString()
        
        binding.btnIncome.isEnabled = result != "0원"
        binding.btnOutcome.isEnabled = result != "0원"
    }

}
