package bitc.example.app.sdh

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.Analyze_List
import bitc.example.app.AppServerClass
import bitc.example.app.MainActivity2
import bitc.example.app.databinding.ActivityMyPageBinding
import bitc.example.app.kms.MonthlyListActivity
import bitc.example.app.ui.CateSearchActivity
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageActivity : AppCompatActivity() {
  private val binding: ActivityMyPageBinding by lazy {
    ActivityMyPageBinding.inflate(layoutInflater)
  }
  private lateinit var sharedPreferences: SharedPreferences
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    binding.btnEdit.setOnClickListener {
      val intent = Intent(this, MyPageCheckActivity::class.java)
      startActivity(intent)
    }

    binding.btnDelete.setOnClickListener {
      showDeleteConfirmationDialog()
    }


    binding.calendarIcon.setOnClickListener{  val intent = Intent(this, MainActivity2::class.java)
      startActivity(intent) }

    binding.chartIcon.setOnClickListener {
      val intent = Intent(this, Analyze_List::class.java)
      startActivity(intent)
    }

    binding.userIcon.setOnClickListener { val intent = Intent(this,MyPageActivity::class.java)
      startActivity(intent)  }

    binding.listIcon.setOnClickListener {
      val intent = Intent(this, MonthlyListActivity::class.java)
      startActivity(intent)
    }

    binding.searchIcone.setOnClickListener {
      val intent = Intent(this, CateSearchActivity::class.java)
      startActivity(intent)
    }


    setSupportActionBar(binding.topToolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }
  override fun onSupportNavigateUp(): Boolean {
    super.onSupportNavigateUp()
    onBackPressedDispatcher.onBackPressed()
    return true
  }

  override fun onResume() {
    super.onResume()
    val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
    val memberId = sharedPreferences.getString("memberId", "아이디").toString()
    val memberName = sharedPreferences.getString("memberName", "이름").toString()
    val createDate = sharedPreferences.getString("createDate", "생성날짜").toString()
    val memberEmail = sharedPreferences.getString("memberEmail", "이메일").toString()
    binding.id.text = memberId
    binding.createDate.text = createDate
    binding.name.text = memberName
    binding.email.text = memberEmail
  }

  private fun showDeleteConfirmationDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("삭제 확인")
    builder.setMessage("정말 삭제하시겠습니까?")
    builder.setPositiveButton("확인") { dialog, which ->
      val memberInfo = getSharedPreferences("memberInfo", MODE_PRIVATE)
      val memberId = memberInfo.getString("memberId", "아이디").toString()
      val api = AppServerClass.instance
      val call = api.memberDelete(memberId)
      memberDeleteProcess(call)
      with(memberInfo.edit()) {
        clear()
        apply()
      }
      val savedId = getSharedPreferences("savedId", MODE_PRIVATE)
      with(savedId.edit()) {
        clear()
        apply()
      }
    }
    builder.setNegativeButton("취소") { dialog, which ->
      dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
  }

  private fun memberDeleteProcess(call: Call<Void>) {
    call.enqueue(object : Callback<Void> {
      override fun onResponse(p0: Call<Void>, res: Response<Void>) {
        if (res.isSuccessful) {
          Snackbar.make(binding.root, "삭제 성공", Snackbar.LENGTH_SHORT).show()
         val intent = Intent(this@MyPageActivity,LoginActivity::class.java)
          startActivity(intent)
        }
        else {
          Snackbar.make(binding.root, "삭제 실패", Snackbar.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(p0: Call<Void>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
        Log.d("fullstack503", "네트워크 오류 : $t.message")
      }
    })
  }
}