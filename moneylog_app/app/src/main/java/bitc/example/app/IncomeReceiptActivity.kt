package bitc.example.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.databinding.ActivityIncomeReceiptBinding
import bitc.example.app.dto.IncomeLogDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomeReceiptActivity : AppCompatActivity() {
  private val binding: ActivityIncomeReceiptBinding by lazy {
    ActivityIncomeReceiptBinding.inflate(layoutInflater)
  }

  //    값을 저장할 incomeResultReceipt 변수
  private lateinit var incomeResultReceipt: TextView
  private lateinit var userId: TextView
  private lateinit var incomeInfo: TextView
  private lateinit var incomeMemo: TextView
  private lateinit var incomePassReceipt: TextView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
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
    userId = binding.userId
    //        getStringExtra 는 PutExtra에서 text_value 라는 이름을 지정하여 그 text를 가져온다.
    val text = intent.getStringExtra("text_value3")
    //        text의 값이 null이 아닐 경우 incomeResultReceipt에 저장된 값을 가져온다.
    if (text != null) {
      incomeResultReceipt.text = text
    }
    else {
      incomeResultReceipt.text = "No data received"
    }
    val id = intent.getStringExtra("user_id")
    if (id != null) {
      userId.text = id
    }
    else {
      userId.text = "No data received"
    }
    val info = intent.getStringExtra("text_value4")

    if (info != null) {
      incomeInfo.text = info
    }
    else {
      incomeInfo.text = "No data received"
    }
    val memo = intent.getStringExtra("text_value5")

    if (memo != null) {
      incomeMemo.text = memo
    }
    else {
      incomeMemo.text = "No data receivce"
    }
    //
    val dialog = intent.getStringExtra("incomeDialog")

    if (dialog != null) {
      incomePassReceipt.text = dialog
    }
    else {
      incomePassReceipt.text = "No data received"
    }
    val selectedCategory = intent.getStringExtra("selectedCategory")
    binding.btnPassIncome.text = selectedCategory

    binding.btnBack.setOnClickListener {
      finish()
    }

    binding.btnSubmit.setOnClickListener {
      val cate = binding.btnPassIncome.text.toString()
      val money = binding.incomeMoneyReceipt.text.toString()
      val source = binding.incomeDialogReceipt.text.toString()
      val incomeMemo = binding.incomeMemoReceipt.text.toString()
      val incomeUse = binding.incomeInfoReceipt.text.toString()
      val id = binding.userId.text.toString()
      var income = IncomeLogDTO()
      income.incomeCate = cate
      income.incomeMoney = money
      income.incomeSource = source
      income.incomeMemo = incomeMemo
      income.incomeUse = incomeUse
      income.memberId = id
      val api = AppServerClass.instance
      val call = api.postIncome(income)
      retrofitResponse(call)
    }
  }

  // Retrofit 통신 응답 부분
  // Callback<String> 부분이 서버에서 전달받을 데이터 타입임
  private fun retrofitResponse(call: Call<String>) {
    call.enqueue(object : Callback<String> {
      override fun onResponse(p0: Call<String>, res: Response<String>) {
        if (res.isSuccessful) {
          // 서버에서 전달받은 데이터만 변수로 저장
          val result = res.body()
          Log.d("fullstack503", "result : $result")
        }
        else {
          Log.d("fullstack503", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<String>, t: Throwable) {
        Log.d("fullstack503", "message : $t.message")
      }
    })
  }
}

