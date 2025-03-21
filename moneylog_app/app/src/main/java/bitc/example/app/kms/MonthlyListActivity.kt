package bitc.example.app.kms

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import bitc.example.app.R
import bitc.example.app.databinding.ActivityMonthlyListBinding

class MonthlyListActivity : AppCompatActivity() {
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
            transaction.addToBackStack(null)
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