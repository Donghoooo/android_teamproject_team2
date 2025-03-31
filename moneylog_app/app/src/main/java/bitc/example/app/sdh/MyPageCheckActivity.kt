package bitc.example.app.sdh

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivityMyPageCheckBinding
import bitc.example.app.dto.MemberDTO
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageCheckActivity : AppCompatActivity() {
  private val binding: ActivityMyPageCheckBinding by lazy {
    ActivityMyPageCheckBinding.inflate(layoutInflater)
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
    binding.btnSubmit.setOnClickListener {
      val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
      val memberId = sharedPreferences.getString("memberId", "아이디").toString()
      var pw = binding.pw.text.toString()
      val member = MemberDTO()
      member.memberId = memberId
      member.memberPw = pw
      val api = AppServerClass.instance
      val call = api.postLogIn(member)
      pwCheck(call)
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

  private fun pwCheck(call: Call<Boolean>) {
    call.enqueue(object : Callback<Boolean> {
      override fun onResponse(p0: Call<Boolean>, res: Response<Boolean>) {
        if (res.isSuccessful) {
          val result = res.body() ?: false

          if (result) {
            val intent = Intent(this@MyPageCheckActivity, EditMyPageActivity::class.java)
            startActivity(intent)
          }
          else {
            Snackbar.make(binding.root, "비밀번호가 다릅니다", Snackbar.LENGTH_SHORT).show()
          }
        }
        else {
          Snackbar.make(binding.root, "송신 실패", Snackbar.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(p0: Call<Boolean>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
        Log.d("fullstack503", "네트워크 오류 : $t.message")
      }
    })
  }
}