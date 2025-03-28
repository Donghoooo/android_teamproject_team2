package bitc.example.app.sdh

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.Analyze_List
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivityLoginBinding
import bitc.example.app.dto.MemberDTO
import bitc.example.app.sagmin.AddInfoActivity
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginActivity : AppCompatActivity() {
  private val binding: ActivityLoginBinding by lazy {
    ActivityLoginBinding.inflate(layoutInflater)
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

    binding.tv2.setOnClickListener {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }

    binding.login.setOnClickListener {
      val id = binding.id.text.toString()
      val pw = binding.pw.text.toString()
      var member = MemberDTO()
      member.memberId = id
      member.memberPw = pw
      val api = AppServerClass.instance
      val call = api.postLogIn(member)
      logInProcess(call)


      val intent = Intent(this,Analyze_List::class.java)
      startActivity(intent)

    }

    setSupportActionBar(binding.topToolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)


  }

  override fun onResume() {
    super.onResume()
    sharedPreferences = getSharedPreferences("savedId", MODE_PRIVATE)
    val savedId = sharedPreferences.getString("savedId", null)
    val isIdSaved = sharedPreferences.getBoolean("isIdSaved", false)
    if (isIdSaved) {
      binding.id.setText(savedId)
      binding.saveId.isChecked = true
    }
    else {
      binding.id.setText("")
      binding.saveId.isChecked = false
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    super.onSupportNavigateUp()
    onBackPressedDispatcher.onBackPressed()
    return true
  }

  private fun logInProcess(call: Call<Boolean>) {
    call.enqueue(object : Callback<Boolean> {
      override fun onResponse(p0: Call<Boolean>, res: Response<Boolean>) {
        if (res.isSuccessful) {
          val result = res.body() ?: false

          if (result) {
            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
            val isSaveIdChecked = binding.saveId.isChecked
            val editor = sharedPreferences.edit()
            if (isSaveIdChecked) {
              val id = binding.id.text.toString()
              editor.putString("savedId", id)
              editor.putBoolean("isIdSaved", true)
            }
            else {
              editor.putBoolean("isIdSaved", false)
            }
            editor.apply()
            val id2 = binding.id.text.toString()
            val pw2 = binding.pw.text.toString()
            var member = MemberDTO()
            member.memberId = id2
            member.memberPw = pw2
            val api = AppServerClass.instance
            val call = api.memberInfo(member)
            getMemberInfo(call)
            binding.id.setText("")
            binding.pw.setText("")
            // intent 메인화면
          }
          else {
            Snackbar.make(binding.root, "아이디 또는 비밀번호가 다릅니다", Snackbar.LENGTH_SHORT).show()
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

  private fun getMemberInfo(call: Call<MemberDTO>) {
    call.enqueue(object : Callback<MemberDTO> {
      override fun onResponse(p0: Call<MemberDTO>, res: Response<MemberDTO>) {
        if (res.isSuccessful) {
          val result: MemberDTO? = res.body()
          if (result != null) {
            saveMemberInfo(result)
            loadMemberInfo()
          }
          else {
            Snackbar.make(binding.root, "아이디 또는 비밀번호가 다릅니다", Snackbar.LENGTH_SHORT).show()
          }
        }
        else {
          Snackbar.make(binding.root, "송신 실패", Snackbar.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(p0: Call<MemberDTO>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
        Log.d("fullstack503", "네트워크 오류 : $t.message")
      }
    })
  }

  private fun saveMemberInfo(memberInfo: MemberDTO) {
    sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    with(sharedPreferences.edit()) {
      putInt("memberSeq", memberInfo.memberSeq!!)
      putString("memberId", memberInfo.memberId)
      putString("memberName", memberInfo.memberName)
      val formattedDate = try {
        val date = LocalDateTime.parse(memberInfo.createDate)
        date.format(formatter)
      } catch (e: Exception) {
        "N/A"
      }
      putString("createDate", formattedDate)
      putString("memberEmail", memberInfo.memberEmail)
      apply()
    }
  }

  private fun loadMemberInfo() {
    val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
    val memberSeq = sharedPreferences.getInt("memberSeq", 0)
    val memberId = sharedPreferences.getString("memberId", "N/A")
    val memberName = sharedPreferences.getString("memberName", "N/A")
    val createDate = sharedPreferences.getString("createDate", "N/A")
    val memberEmail = sharedPreferences.getString("memberEmail", "N/A")
    Log.d("fullstack503", "memberSeq: $memberSeq")
    Log.d("fullstack503", "memberId: $memberId")
    Log.d("fullstack503", "memberName: $memberName")
    Log.d("fullstack503", "createDate: $createDate")
    Log.d("fullstack503", "memberEmail: $memberEmail")
  }
}