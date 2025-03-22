package bitc.example.app.sdh

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivitySignUpBinding
import bitc.example.app.dto.MemberDTO
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
  private val binding: ActivitySignUpBinding by lazy {
    ActivitySignUpBinding.inflate(layoutInflater)
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

    binding.signUp.setOnClickListener {
      val id = binding.id.text.toString()
      val pw = binding.pw.text.toString()
      val pwCheck = binding.pwCheck.text.toString()
      val name = binding.name.text.toString()
      val emil = binding.email.text.toString()
      if (pw.isEmpty() || pwCheck.isEmpty()) {
        Snackbar.make(binding.root, "비밀번호를 입력해주세요", Snackbar.LENGTH_SHORT).show()
      }
      else if (pw != pwCheck) {
        Toast.makeText(this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
      }
      else {
        binding.id.setText("")
        binding.pw.setText("")
        binding.pwCheck.setText("")
        binding.name.setText("")
        binding.email.setText("")
        var member = MemberDTO()
        member.memberId = id
        member.memberPw = pw
        member.memberName = name
        member.memberEmail = emil
        // member 를 서버로 보냄
        val api = AppServerClass.instance
        val call = api.postSignUp(member)
        call.enqueue(object : Callback<String> {
          override fun onResponse(p0: Call<String>, res: Response<String>) {
            if (res.isSuccessful) {
              //            서버에서 전달받은 데이터만 변수로 저장
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

    binding.id.addTextChangedListener(object : TextWatcher {
      // 입련 전
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      // 입력 중
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      // 입력 후
      override fun afterTextChanged(s: Editable?) {
        val id = binding.id.text.toString()
        Log.d("fullstack503", id)
      }
    })

    setSupportActionBar(binding.topToolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    super.onSupportNavigateUp()
    onBackPressedDispatcher.onBackPressed()
    return true
  }
}