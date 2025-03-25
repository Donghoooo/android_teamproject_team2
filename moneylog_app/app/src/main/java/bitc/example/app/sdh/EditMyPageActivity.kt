package bitc.example.app.sdh

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.ActivityEditMyPageBinding
import bitc.example.app.dto.MemberDTO
import com.google.android.material.snackbar.Snackbar

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
    binding.id.setText(memberId)
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
        var member = MemberDTO()
        member.memberId = id
        member.memberPw = pw
        member.memberName = name
        member.memberEmail = email
        val api = AppServerClass.instance
        //        val call = api.postSignUp(member)   member update 걸면 됨
        //        signUpProcess(call)
      }
      // 아이디랑 닉네임 입력값받아서 사용가능한지 안되는지 보여주기
      // 셰어드 프리퍼런스 바뀐값으로 수정해주기 memberInfo
      // savedId 는 꺼주기
      val intent = Intent(this, MyPageActivity::class.java)
      startActivity(intent)
    }
  }
}