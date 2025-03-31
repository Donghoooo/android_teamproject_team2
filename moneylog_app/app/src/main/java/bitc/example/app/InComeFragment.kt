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
import bitc.example.app.databinding.FragmentIncomeBinding
import bitc.example.app.dto.IncomeLogDTO
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InComeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InComeFragment : Fragment() {

//    override fun onDateSelected(startDate: String, endDate: String) {
//        Log.d("InComeFragment", "Received Dates: $startDate ~ $endDate")
//        // 여기서 API 호출을 갱신하는 등의 작업을 하면 됨
//    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 로그인 되어있는 memberId 값을 담을 변수 memberId
    private lateinit var memberId1: SharedPreferences
    private lateinit var memberId: String

    private lateinit var binding: FragmentIncomeBinding


    private lateinit var totalIncomeMoney: String

    interface totalIncome {
        fun totalIncome(data: String)
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

        binding = FragmentIncomeBinding.inflate(inflater, container, false)
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
        val call = api.getanalyze(startDate, endDate, memberId)

        call.enqueue(object : Callback<List<IncomeLogDTO>> {
            override fun onResponse(
                p0: Call<List<IncomeLogDTO>>, res: Response<List<IncomeLogDTO>>
            ) {
                if (res.isSuccessful) {
                    val result = res.body()

                    // 수입 합계 계산
                    val totalIncome = result?.sumOf { it.incomeMoney?.toIntOrNull() ?: 0 }
                    Log.d("fullstack503", totalIncome.toString())
                    totalIncomeMoney = totalIncome.toString()

                    // 총 수입 데이터를 액티비티로 전달
                    (activity as? totalIncome)?.totalIncome(totalIncomeMoney)

                    Log.d("csy", "result : $result")
                    val categoryIncomeMap = mutableMapOf<String, Int>()
                    result?.forEach { incomeLog ->
                        val category = incomeLog.incomeCate ?: "Unknown"
                        val amount = incomeLog.incomeMoney?.toIntOrNull() ?: 0

                        // 카테고리별로 합산
                        categoryIncomeMap[category] =
                            categoryIncomeMap.getOrDefault(category, 0) + amount
                    }

                    val adapter = result?.let { InComeAdapter(it) }
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            LinearLayoutManager.VERTICAL
                        )
                    )

                    // PieChart 초기화
                    val pieChart: PieChart = binding.pieChart

                    // 데이터 준비
                    val entries = ArrayList<PieEntry>()
                    categoryIncomeMap.forEach { (category, totalAmount) ->
                        entries.add(PieEntry(totalAmount.toFloat(), category))
                    }


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


                } else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<IncomeLogDTO>>, t: Throwable) {
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