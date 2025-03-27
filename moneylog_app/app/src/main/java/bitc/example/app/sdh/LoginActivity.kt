package bitc.example.app.sdh

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.example.app.AddInfoActivity
import bitc.example.app.databinding.ActivityLoginBinding
import bitc.example.app.dto.MemberDTO

class LoginActivity : AppCompatActivity() {
  private lateinit var sharedPreferences: SharedPreferences
  private val binding: ActivityLoginBinding by lazy {
    ActivityLoginBinding.inflate(layoutInflater)
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

    sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val savedId = sharedPreferences.getString("saved_id", "")
    if (!savedId.isNullOrEmpty()) {
      binding.id.setText(savedId)
      binding.saveId.isChecked = true
    }
    binding.tv2.setOnClickListener {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }

    binding.login.setOnClickListener {
      val id = binding.id.text.toString()
      val pw = binding.pw.text.toString()
      binding.id.setText("")
      binding.pw.setText("")
      var member = MemberDTO()
      member.memberId = id
      member.memberPw = pw
      val intent = Intent(this, AddInfoActivity::class.java).apply {
        putExtra("user_id", id) // ID를 Intent에 추가
      }
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
  //  fun initEventListener() {
  //    val savedId = binding.getString("saved_id", "")
  //    if (!savedId.isNullOrEmpty()) {
  //      binding.id.setText(savedId)
  //      binding.saveId.isChecked = true
  //  }
}