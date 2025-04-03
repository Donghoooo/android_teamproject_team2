package bitc.example.app

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.example.app.databinding.FragmentExpenBinding
import bitc.example.app.dto.ExpenseLogDTO
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InComeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 로그인 되어있는 memberId 값을 담을 변수 memberId
    private lateinit var memberId1: SharedPreferences
    private lateinit var memberId: String


    private lateinit var binding: FragmentExpenBinding

    private lateinit var totalExpenMoney: String

    interface totalExpen {
        fun totalExpen(data1: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExpenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  변수 memberId에 로그인 되어있는 값(test1)을 String타입으로 할당해줌
        memberId1 = requireContext().getSharedPreferences("memberInfo", MODE_PRIVATE)
        memberId = memberId1.getString("memberId", "아이디").toString()

        var startDate = this.arguments?.getString("startDate")
        var endDate = this.arguments?.getString("endDate")


        if (startDate == null) {
            startDate = "2025-01-01"
        }

        if (endDate == null) {
            endDate = "2025-12-31"
        }

        Log.d("fullstack503", "startDate : $startDate")
        Log.d("fullstack503", "endDate : $endDate")


        val api = AppServerClass.instance
        val call = api.getanalyze1(startDate, endDate, memberId)

        call.enqueue(object : Callback<List<ExpenseLogDTO>> {
            override fun onResponse(
                p0: Call<List<ExpenseLogDTO>>, res: Response<List<ExpenseLogDTO>>
            ) {
                if (res.isSuccessful) {
                    val result = res.body()

                    // 수입 합계 계산
                    val totalExpen = result?.sumOf { it.expenseMoney?.toIntOrNull() ?: 0 }
                    Log.d("fullstack503", totalExpen.toString())
                    val formattedTotalExpen = NumberFormat.getNumberInstance().format(totalExpen)
                    // 포맷팅된 금액을 totalExpenMoney에 할당
                    totalExpenMoney = formattedTotalExpen
                    // 총 수입 데이터를 액티비티로 전달
                    (activity as? totalExpen)?.totalExpen(totalExpenMoney)

                    Log.d("csy", "result : $result")

                    val categoryExpenMap = mutableMapOf<String, Int>()
                    result?.forEach { expenLog ->
                        val category1 = expenLog.expenseCate ?: "Unknown"
                        val amount1 = expenLog.expenseMoney?.toIntOrNull() ?: 0

                        // 카테고리별로 합산
                        categoryExpenMap[category1] =
                            categoryExpenMap.getOrDefault(category1, 0) + amount1

                        val adapter = result?.let { ExpenAdapter(it) }
                        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
                        binding.recyclerView2.adapter = adapter
                        binding.recyclerView2.addItemDecoration(
                            DividerItemDecoration(
                                context,
                                LinearLayoutManager.VERTICAL
                            )
                        )

                        // PieChart 초기화
                        val pieChart: PieChart = binding.pieChart

                        // 데이터 준비
                        val entries = ArrayList<PieEntry>()
                        categoryExpenMap.forEach { (category, totalAmount) ->
                            entries.add(PieEntry(totalAmount.toFloat(), category))
                        }

                        // PieDataSet 생성
                        // PieDataSet 생성
                        val customColors = listOf(
                            0xFF1E88E5.toInt(),  // 파란색
                            0xFF43A047.toInt(),  // 초록색
                            0xFFFFC107.toInt(),  // 노란색
                            0xFFEF5350.toInt(),  // 빨간색
                            0xFF29B6F6.toInt(),  // 하늘색
                            0xFF9C27B0.toInt(),  // 보라색
                            0xFF5C6BC0.toInt(),  // 파란색
                            0xFF00897B.toInt(),  // 청록색
                            0xFF9E9D24.toInt(),  // 황록색
                            0xFF6D4C41.toInt()   // 갈색
                        )

                        val dataSet = PieDataSet(entries, "")
                        dataSet.colors = customColors
                        dataSet.valueTextSize = 15f // 값 텍스트 크기 설정

                        // PieData 생성
                        val data = PieData(dataSet)

                        // PieChart에 데이터 설정
                        pieChart.data = data

                        // 기타 설정
                        pieChart.isDrawHoleEnabled = true  // 원형 차트 내부에 홀(구멍) 그리기
                        pieChart.setUsePercentValues(true)  // 퍼센트 값 표시
                        pieChart.invalidate()  // 차트 업데이트
                        pieChart.description.isEnabled = false
                    }
                } else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<ExpenseLogDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InComeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}