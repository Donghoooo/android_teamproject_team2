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
import bitc.example.app.databinding.ActivityIncomeReceiptBinding
import bitc.example.app.dto.IncomeLogDTO
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.sdh.MyPageActivity
import bitc.example.app.ui.CateSearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomeReceiptActivity : AppCompatActivity() {
  private val binding: ActivityIncomeReceiptBinding by lazy {
    ActivityIncomeReceiptBinding.inflate(layoutInflater)
  }

  private lateinit var sharedPreferences: SharedPreferences

  //    값을 저장할 incomeResultReceipt 변수
  private lateinit var incomeResultReceipt: TextView
  private lateinit var incomeInfo: TextView
  private lateinit var incomeMemo: TextView
  private lateinit var incomePassReceipt: TextView
  private lateinit var incomeDate : TextView
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
    incomeDate = binding.date
    //        getStringExtra 는 PutExtra에서 text_value 라는 이름을 지정하여 그 text를 가져온다.
    val text = intent.getStringExtra("text_value3")
    //        text의 값이 null이 아닐 경우 incomeResultReceipt에 저장된 값을 가져온다.
    if (text != null) {
      incomeResultReceipt.text = text
    }
    else {
      incomeResultReceipt.text = "No data received"
    }

    val date = intent.getStringExtra("date")
    if (date != null){
      incomeDate.text = date
    }
    else{
      incomeDate.text = "No data receivced"
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



    binding.btnSubmit.setOnClickListener {
      val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
      val memberId = sharedPreferences.getString("memberId", "아이디").toString()
      Log.d("fullstack503", memberId)


      val date = binding.date.text.toString()
      val cate = binding.btnPassIncome.text.toString()
      val money = binding.incomeMoneyReceipt.text.toString()
      val source = binding.incomeDialogReceipt.text.toString()
      val incomeMemo = binding.incomeMemoReceipt.text.toString()
      val incomeUse = binding.incomeInfoReceipt.text.toString()
      var income = IncomeLogDTO()
      income.memberId = memberId
      income.incomeCate = cate
      income.incomeMoney = money
      income.incomeSource = source
      income.incomeMemo = incomeMemo
      income.incomeUse = incomeUse
      income.incomeDate = date
      val api = AppServerClass.instance
      val call = api.postIncome(income)
      retrofitResponse(call)

      val intent = Intent(this,MainActivity2::class.java)
      startActivity(intent)
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

