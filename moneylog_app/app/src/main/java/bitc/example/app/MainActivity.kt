package bitc.example.app

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.adapter.CalendarAdapter
import bitc.example.app.databinding.ActivityMainBinding
import bitc.example.app.viewmodel.CalendarViewModel

class MainActivity : AppCompatActivity() {

  private val viewModel: CalendarViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel.headerDate.observe(this) { date ->
      binding.tvHeader.text = date
    }

    viewModel.calendarData.observe(this) { data ->
      binding.calendar.layoutManager = GridLayoutManager(this, 7)
      binding.calendar.adapter = CalendarAdapter(data)

      binding.btnPrevMonth.setOnClickListener {
        viewModel.changeMonth(-1)
      }

      binding.btnNextMonth.setOnClickListener {
        viewModel.changeMonth(1)
      }

      // 이미지뷰 색상 필터 적용
      val imageView: ImageView = findViewById(R.id.imageView)
      val colorFilter = PorterDuffColorFilter(
        ContextCompat.getColor(this, R.color.btn_plus), PorterDuff.Mode.SRC_IN
      )
      imageView.setColorFilter(colorFilter)
    }

    // WindowInsets 처리
    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }
}
