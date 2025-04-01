package bitc.example.app.sdh

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.MainActivity2
import bitc.example.app.databinding.ActivityEditMyPageBinding
import bitc.example.app.dto.MemberDTO
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditMyPageActivity : AppCompatActivity() {
  private val binding: ActivityEditMyPageBinding by lazy {
    ActivityEditMyPageBinding.inflate(layoutInflater)
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
    val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
    val memberId = sharedPreferences.getString("memberId", "아이디")
    val memberName = sharedPreferences.getString("memberName", "이름")
    val memberEmail = sharedPreferences.getString("memberEmail", "이메일")
    binding.id.text = memberId
    binding.name.setText(memberName)
    binding.email.setText(memberEmail)


    binding.edit.setOnClickListener {
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
        var sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
        var memberSeq = sharedPreferences.getInt("memberSeq", 0)
        var member = MemberDTO()
        member.memberSeq = memberSeq
        member.memberId = id
        member.memberPw = pw
        member.memberName = name
        member.memberEmail = email
        val api = AppServerClass.instance
        val call = api.memberUpdate(member)
        updateProcess(call)
        // 메인화면으로 Intent 넣으면 됨
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
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

  private fun updateProcess(call: Call<MemberDTO>) {
    call.enqueue(object : Callback<MemberDTO> {
      override fun onResponse(p0: Call<MemberDTO>, res: Response<MemberDTO>) {
        if (res.isSuccessful) {
          val result: MemberDTO? = res.body()
          if (result != null) {
            saveMemberInfo(result)
            val sharedPreferences = getSharedPreferences("savedId", MODE_PRIVATE)
            sharedPreferences.edit() { putBoolean("isIdSaved", false) }
          }
          else {
            Snackbar.make(binding.root, "오류", Snackbar.LENGTH_SHORT).show()
          }
        }
        else {
          Snackbar.make(binding.root, "송신 실패", Snackbar.LENGTH_SHORT).show()
          Log.d("fullstack503", "송신 실패")
        }
      }

      override fun onFailure(p0: Call<MemberDTO>, t: Throwable) {
        Snackbar.make(binding.root, "네트워크 오류", Snackbar.LENGTH_SHORT).show()
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
}