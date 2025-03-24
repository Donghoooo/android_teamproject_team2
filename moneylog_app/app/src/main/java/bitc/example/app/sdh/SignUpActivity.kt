package bitc.example.app.sdh

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
      val email = binding.email.text.toString()
      if (id.length < 4) {
        Snackbar.make(binding.root, "아이디는 4자 이상이어야 합니다", Snackbar.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (pw.length < 4) {
        Snackbar.make(binding.root, "비밀번호는 4자 이상이어야 합니다", Snackbar.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (pw != pwCheck) {
        Snackbar.make(binding.root, "비밀번호가 일치하지 않습니다", Snackbar.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (!email.matches(
          Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
          )
        )
      ) {
        Snackbar.make(binding.root, "이메일 형식이 올바르지 않습니다.", Snackbar.LENGTH_SHORT).show()
        return@setOnClickListener
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
        member.memberEmail = email
        val api = AppServerClass.instance
        val call = api.postSignUp(member)
        signUpProcess(call)
      }
    }

    binding.id.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(s: Editable?) {
        val id = binding.id.text.toString()
        if (id.length >= 4) {
          val api = AppServerClass.instance
          val call = api.isMemberId(id)
          isMemberId(call)
        }
        else {
          binding.idCheck.visibility = View.GONE
        }
      }
    })

    binding.name.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(s: Editable?) {
        val name = binding.name.text.toString()
        if (name.length >= 4) {
          val api = AppServerClass.instance
          val call = api.isMemberName(name)
          isMemberName(call)
        }
        else {
          binding.nameCheck.visibility = View.GONE
        }
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

  // Retrofit 통신 응답 부분
  // Callback<String> 부분이 서버에서 전달받을 데이터 타입임
  private fun signUpProcess(call: Call<String>) {
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

  private fun isMemberId(call: Call<Boolean>) {
    call.enqueue(object : Callback<Boolean> {
      override fun onResponse(p0: Call<Boolean>, res: Response<Boolean>) {
        if (res.isSuccessful) {
          val result = res.body() ?: true
          if (!result) {
            binding.idCheck.visibility = View.VISIBLE
          }
          else {
            binding.idCheck.visibility = View.GONE
          }
        }
        else {
          Snackbar.make(binding.root, "송신 실패", Snackbar.LENGTH_SHORT).show()
          Log.d("fullstack503", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<Boolean>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
        Log.d("fullstack503", "message : $t.message")
      }
    })
  }

  private fun isMemberName(call: Call<Boolean>) {
    call.enqueue(object : Callback<Boolean> {
      override fun onResponse(p0: Call<Boolean>, res: Response<Boolean>) {
        if (res.isSuccessful) {
          val result = res.body() ?: true
          if (!result) {
            binding.nameCheck.visibility = View.VISIBLE
          }
          else {
            binding.nameCheck.visibility = View.GONE
          }
        }
        else {
          Snackbar.make(binding.root, "송신 실패", Snackbar.LENGTH_SHORT).show()
          Log.d("fullstack503", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<Boolean>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
        Log.d("fullstack503", "message : $t.message")
      }
    })
  }
}