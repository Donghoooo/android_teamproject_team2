package bitc.example.app

import CalendarAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.adapter.ListAdapter
import bitc.example.app.databinding.ActivityMainBinding
import bitc.example.app.dto.MainListDTO
import bitc.example.app.viewmodel.CalendarViewModel


class MainActivity : AppCompatActivity() {
    // 로그인 되어있는 memberId 값을 담을 변수 memberId
    private lateinit var memberId1: SharedPreferences
    private lateinit var memberId: String

    private val viewModel: CalendarViewModel by viewModels()  // ViewModel을 가져옴
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    } // Binding 초기화
    private lateinit var sharedPreferences: SharedPreferences

//    레트로핏


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // ViewBinding을 사용하여 레이아웃을 inflate
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        // RecyclerView의 LayoutManager 설정
        binding.listRecycle.layoutManager = LinearLayoutManager(this)
        // ListAdapter 초기화 및 RecyclerView에 연결
        val listAdapter: ListAdapter = ListAdapter(mutableListOf())
        binding.listRecycle.adapter = listAdapter
        binding.listRecycle.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        // headerDate
        viewModel.headerDate.observe(this, Observer { date ->
            binding.tvHeader.text = date // headerDate TextView 업데이트
        })
        // scrollMonth를  tvScrollMonth에 표시
        viewModel.scrollMonth.observe(this, Observer { month ->
            binding.tvScrollMonth.text = month // scrollMonth TextView 업데이트
        })
        // calendarData를 RecyclerView에 달력 데이터 표시
        viewModel.calendarData.observe(this) { data ->
            binding.calendar.layoutManager = GridLayoutManager(this, 7) // 한 주에 7일
            binding.calendar.adapter = CalendarAdapter(data) { day, income, expense, month, year ->
                // 날짜 클릭 시 데이터 업데이트
                if (day != null) {
                    binding.scrollView.visibility = View.VISIBLE
                    binding.tvScrollDay.text = day.toString() // 날짜 업데이트

              // 해당 월을 업데이트
                    viewModel.scrollMonth.value = month.toString()
                }
            }
        }
        // 이전 월 버튼 클릭 시
        binding.btnPrevMonth.setOnClickListener {
            viewModel.changeMonth(-1)  // -1로 호출하여 한 달 전으로 변경
        }
        // 다음 월 버튼 클릭 시
        binding.btnNextMonth.setOnClickListener {
            viewModel.changeMonth(1)  // +1로 호출하여 한 달 후로 변경
        }

        // WindowInsets 처리 (전체 화면을 위한 설정)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}