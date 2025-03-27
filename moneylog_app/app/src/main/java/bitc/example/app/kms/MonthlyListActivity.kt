package bitc.example.app.kms

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.R
import bitc.example.app.databinding.ActivityMonthlyListBinding

class MonthlyListActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMonthlyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("memberInfo", MODE_PRIVATE)
        val memberId = sharedPreferences.getString("memberId", "아이디").toString()
        // 수입/지출 버튼 토글
        val toggleGroup = findViewById<RadioGroup>(R.id.toggleGroup)
        val btnIncome = findViewById<RadioButton>(R.id.btn_income)
        val btnExpense = findViewById<RadioButton>(R.id.btn_expense)

        // 초기값 설정
        btnIncome.isChecked = true

        // 선택 변경 리스너
        toggleGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_income -> {
                    // "수입" 선택 시
                }
                R.id.btn_expense -> {
                    // "지출" 선택 시
                }
            }
        }

        val fragmentManager: FragmentManager = supportFragmentManager
        var transaction: FragmentTransaction = fragmentManager.beginTransaction()

        val fragmentIncom = FragmentIncom()
        val fragmentExpense = FragmentExpense()

        transaction.add(R.id.layout_fragment_base, fragmentIncom)
        transaction.commit()

        binding.btnIncome.setOnClickListener {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.layout_fragment_base, fragmentIncom)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()
        }

        binding.btnExpense.setOnClickListener {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.layout_fragment_base, fragmentExpense)
            transaction.setReorderingAllowed(true)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }
}