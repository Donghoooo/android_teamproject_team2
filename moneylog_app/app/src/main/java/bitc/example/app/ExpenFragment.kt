package bitc.example.app

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


        val api = AppServerClass.instance
        val call = api.getanalyze1()

        call.enqueue(object : Callback<List<ExpenseLogDTO>> {
            override fun onResponse(
                p0: Call<List<ExpenseLogDTO>>, res: Response<List<ExpenseLogDTO>>
            ) {
                if (res.isSuccessful) {
                    val result = res.body()
                    val totalExpen = result?.sumOf { it.expenseMoney?.toIntOrNull() ?: 0 }
                    Log.d("fullstack503", totalExpen.toString())

                    totalExpenMoney = totalExpen.toString()

                    (activity as? totalExpen)?.totalExpen(totalExpenMoney)

                    Log.d("csy", "result : $result")


                    val adapter = result?.let { ExpenAdapter(it) }
                    binding.recyclerView2.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView2.adapter = adapter
                    binding.recyclerView2.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            LinearLayoutManager.VERTICAL
                        )
                    )
                } else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<ExpenseLogDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })


        // PieChart 초기화
        val pieChart: PieChart = binding.pieChart

        // 데이터 준비
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(4f))
        entries.add(PieEntry(10f))
        entries.add(PieEntry(13f))
        entries.add(PieEntry(14f))
        entries.add(PieEntry(15f))
        entries.add(PieEntry(26f))


        // PieDataSet 생성
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // 색상 지정
        dataSet.valueTextSize = 15f // 값 텍스트 크기 설정

        // PieData 생성
        val data = PieData(dataSet)

        // PieChart에 데이터 설정
        pieChart.data = data

        // 기타 설정
        pieChart.isDrawHoleEnabled = true  // 원형 차트 내부에 홀(구멍) 그리기
//        pieChart.holeRadius = 30f
//        pieChart.setHoleColor(R.color.white)  // 구멍 색상 설정
        pieChart.setUsePercentValues(true)  // 퍼센트 값 표시
        pieChart.invalidate()  // 차트 업데이트
        pieChart.description.isEnabled = false
//        pieChart.isRotationEnabled = false

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