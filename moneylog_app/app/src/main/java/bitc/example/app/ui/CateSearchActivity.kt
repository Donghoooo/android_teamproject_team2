package bitc.example.app.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.AppServerClass
import bitc.example.app.dto.SearchDTO
import bitc.example.app.R
import bitc.example.app.adapter.SearchListAdapter
import bitc.example.app.databinding.ActivityCateSearchBinding
import bitc.example.app.model.SearchListItem
import bitc.example.app.ui.dialog.BankSelectionDialog
import bitc.example.app.ui.dialog.CategorySelectionDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CateSearchActivity : AppCompatActivity() {

  private val binding: ActivityCateSearchBinding by lazy {
    ActivityCateSearchBinding.inflate(layoutInflater)
  }

  //  자산방식, 카테고리 정보 가져오기
  private lateinit var searchListAdapter: SearchListAdapter
  val searchItemList = mutableListOf<SearchListItem>()

  //    날짜 선택
  private lateinit var startDate: TextView
  private lateinit var endDate: TextView
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

////    키워드 검색
//    binding.searchView.addTextChangedListener(object : TextWatcher {
//      override fun afterTextChanged(s: Editable?) {
//        filterList(s.toString())  // 입력된 검색어로 리스트 필터링
//      }
//
//      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//    })

//    날짜 선택
    startDate = binding.startDate
    endDate = binding.endDate
    startDatePicker = binding.startDatePicker
    endDatePicker = binding.endDatePicker

//    오늘날짜 기본값 설정
    val today = Calendar.getInstance()
    val todayDate = dateFormat.format(today.time)
    startDate.text = todayDate
    endDate.text = todayDate

//    시작 날짜 캘린더 아이콘 클릭 이벤트
    startDatePicker.setOnClickListener {
      showDatePicker(startDate)
    }
//    종료 날짜 캘린더 아이콘 클릭 이벤트
    endDatePicker.setOnClickListener {
      showDatePicker(endDate)
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

    //    검색 리스트
//    RecyclerView 초기화
    binding.searchListView.layoutManager = LinearLayoutManager(this)
    searchListAdapter = SearchListAdapter(searchItemList)
    binding.searchListView.adapter = searchListAdapter

    // 검색 버튼 클릭 시 API 요청
    binding.btnSearch.setOnClickListener {
      fetchTransactionData("date")
    }
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

  //   수입, 지출 총 금액 계산
  private fun calculateTotalAmount(transactions: List<SearchDTO>): Pair<String, String> {
    var totalIncome = 0
    var totalExpense = 0

    for (item in transactions) {
      val moneyValue = item.money ?: 0  // null 값이면 0으로 출력
      if (item.type == "income") {
        totalIncome += moneyValue
      }
      else {
        totalExpense += moneyValue
      }
    }
    val formatter = DecimalFormat("#,###")  // 숫자를 00,000 형식으로 변환하는 포맷
    return Pair("${formatter.format(totalIncome)} 원", "${formatter.format(totalExpense)} 원")
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
//    금액 순 정렬 코드
    fetchTransactionData("amount")
  }

  private fun sortByDate() {
    Toast.makeText(this, "날짜순 정렬", Toast.LENGTH_SHORT).show()
//    날짜 순 정렬 코드
    fetchTransactionData("date")
  }

  private fun fetchTransactionData(sortBy: String) {

    Log.d("DEBUG", "정렬 방식: $sortBy")
//    선택한 자산빙식 및 카테고리 가져오기
    // 선택한 카테고리 (미선택 시 null)
    val selectedCategory = selectedCategories.filter { it.value }.keys.toList().takeIf { it.isNotEmpty() }
    val selectedBank = selectedBanks.filter { it.value }.keys.toList().takeIf { it.isNotEmpty() }

    // 선택한 날짜 가져오기
    val startDate = binding.startDate.text.toString()
    val endDate = binding.endDate.text.toString()

//    검색창에서 작성한 키워드 가져오기 (미입력시 null)
    val keyword = binding.searchView.text.toString().takeIf { it.isNotBlank() } ?: ""

    // Retrofit API 호출
    AppServerClass.instance.getSearchList(
      selectedCategory,  // null이 전달될 수 있도록 수정
      selectedBank,
      startDate,
      endDate,
      keyword,
      sortBy
    ).enqueue(object : Callback<List<SearchDTO>>  {
      @SuppressLint("NotifyDataSetChanged")
      override fun onResponse(call: Call<List<SearchDTO>>, response: Response<List<SearchDTO>>) {
        if (response.isSuccessful) {
          Log.d("DEBUG", "서버 응답 성공. 데이터 수: ${response.body()?.size}")
          val transactions = response.body() ?: emptyList()

          // 기존 데이터 지우고 새 데이터 추가
          searchItemList.clear()

          searchItemList.addAll(transactions.map {
            SearchListItem(
              it.date,
              it.category ?: "미분류",
              it.use ?: "내역없음",  // 필요하면 변경
              it.source ?: "미분류",
              "${it.money} 원",
              it.type// 타입 저장할때 'income' or 'expense' 로 저장
            )
          })

          // 총 수입 & 총 지출 계산 후 UI 반영
          val (formattedIncome, formattedExpense) = calculateTotalAmount(transactions)
          binding.income.text = formattedIncome
          binding.expense.text = formattedExpense

//            리스트 새로고침
          searchListAdapter.notifyDataSetChanged()
          // 총 검색된 리스트 수 표시
          binding.totalCount.text = "총 ${searchItemList.size} 건"
        }
        else {
          Log.e("API_ERROR", "서버 응답 실패: ${response.code()} ${response.message()}")
          Toast.makeText(this@CateSearchActivity, "서버 응답 실패", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<List<SearchDTO>>, t: Throwable) {
        Toast.makeText(this@CateSearchActivity, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
      }
    })
  }
}


