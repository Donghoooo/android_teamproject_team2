package bitc.example.app.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.adapter.SearchListAdapter
import bitc.example.app.databinding.ActivityCateSearchBinding
import bitc.example.app.model.SearchListItem
import bitc.example.app.ui.dialog.BankSelectionDialog
import bitc.example.app.ui.dialog.CategorySelectionDialog
import java.text.SimpleDateFormat
import java.util.*

class CateSearchActivity : AppCompatActivity() {

  private val binding: ActivityCateSearchBinding by lazy {
    ActivityCateSearchBinding.inflate(layoutInflater)
  }

  //    날짜 선택
  private lateinit var startDateText: TextView
  private lateinit var endDateText: TextView
  private lateinit var startDatePicker: ImageView
  private lateinit var endDatePicker: ImageView
  private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

//  카테고리 선택
  private val selectedCategories = mutableMapOf<String, Boolean>() // 선택한 항목 저장

//  자산방식 선택
  private val selectedBanks = mutableMapOf<String, Boolean>()

  //    정렬방식 드롭다운
  private lateinit var btnSort: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    // 뒤로 가기 버튼
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

//    날짜 선택
    startDateText = binding.startDate
    endDateText = binding.endDate
    startDatePicker = binding.startDatePicker
    endDatePicker = binding.endDatePicker

//    오늘날짜 기본값 설정
    val today = Calendar.getInstance()
    val todayDate = dateFormat.format(today.time)
    startDateText.text = todayDate
    endDateText.text = todayDate

//    시작 날짜 캘린더 아이콘 클릭 이벤트
    startDatePicker.setOnClickListener {
      showDatePicker(startDateText)
    }
//    종료 날짜 캘린더 아이콘 클릭 이벤트
    endDatePicker.setOnClickListener {
      showDatePicker(endDateText)
    }

//    카테고리 선택
    val btnOpenCateDialog = binding.category
    btnOpenCateDialog.setOnClickListener {
      val cateDialog = CategorySelectionDialog(this, selectedCategories) { selected ->
        selectedCategories.putAll(selected)
        updateCategoryText()
      }
      cateDialog.show()
    }

//    자산방식 선택
    val btnOpenBankDialog = binding.bank
    btnOpenBankDialog.setOnClickListener {
      val bankDialog = BankSelectionDialog(this, selectedBanks) { selected ->
        selectedBanks.putAll(selected)
        updateBankText()
      }
      bankDialog.show()
    }

//    정렬방식 드롭다운 클릭 이벤트
    btnSort = binding.btnSort
    btnSort.setOnClickListener {
      showPopupMenu(it)
    }

//    검색 결과 리스트
    val searchListView: RecyclerView = binding.searchListView
    searchListView.layoutManager = LinearLayoutManager(this)

    val searchItemList = listOf(
      SearchListItem("3.15", "식비", "내역", "신용카드", "123,123 원", true),
      SearchListItem("3.15", "식비", "내역", "이체", "123,123 원", false),
      SearchListItem("3.15", "식비", "내역", "현금", "123,123 원", true),
      SearchListItem("3.15", "식비", "내역", "현금", "123,123 원", false),
      SearchListItem("3.15", "식비", "내역", "체크카드", "123,123 원", true)
    )
    searchListView.adapter = SearchListAdapter(searchItemList)
  }




  //  뒤로가기 버튼
  override fun onSupportNavigateUp(): Boolean {
    super.onSupportNavigateUp()
    onBackPressedDispatcher.onBackPressed()
    return true
  }

//  DatePickerDialog 표시 (캘린더 창 생성)
  private fun showDatePicker(textView: TextView) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
      val selectedDate = Calendar.getInstance()
      selectedDate.set(selectedYear, selectedMonth, selectedDay)
      textView.text = dateFormat.format(selectedDate.time)
    }, year, month, day)

    datePickerDialog.show()
  }

//  카테고리 선택 부분 클릭 시 다이얼로그 표시
  private fun updateCategoryText() {
  val selectedCateText = selectedCategories.filter { it.value }.keys.joinToString(", ")
  binding.category.text = selectedCateText
  }

//  자산 방식 클릭 시 다이얼로그 표시
private fun updateBankText(){
  val selectedBankText = selectedBanks.filter { it.value }.keys.joinToString(", ")
  binding.bank.text = selectedBankText
}

//  정렬방식 드롭다운
  private fun showPopupMenu(view: View) {
    val popup = PopupMenu(this, view)
    popup.menuInflater.inflate(R.menu.sort_menu, popup.menu)

    popup.setOnMenuItemClickListener { item ->
      when (item.itemId) {
        R.id.menu_date -> {
          btnSort.text = "날짜순"
          sortByDate()
          true
        }
        R.id.menu_amount -> {
          btnSort.text = "금액순"
          sortByAmount()
          true
        }
        else -> false
      }
    }
    popup.show()
  }

  private fun sortByAmount() {
    Toast.makeText(this, "금액순 정렬", Toast.LENGTH_SHORT).show()
//    금액 순 정렬 코드 입력 필요
  }

  private fun sortByDate() {
    Toast.makeText(this, "날짜순 정렬", Toast.LENGTH_SHORT).show()
//    날짜 순 정렬 코드 입력 필요
  }


}

