package bitc.example.app.kms

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
import bitc.example.app.AppServerClass
import bitc.example.app.databinding.FragmentIncomBinding
import bitc.example.app.dto.IncomeLogDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentIncom.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentIncom : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 로그인 되어있는 memberId 값을 담을 변수 memberId
    private lateinit var memberId1: SharedPreferences
    private lateinit var memberId: String

    private lateinit var binding: FragmentIncomBinding

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

        binding = FragmentIncomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  변수 memberId에 로그인 되어있는 값(test1)을 String타입으로 할당해줌
        memberId1 = requireContext().getSharedPreferences("memberInfo", MODE_PRIVATE)
        memberId = memberId1.getString("memberId", "아이디").toString()

        Log.d("fullstack503", "memberId: $memberId")


        //  레트로 핏 API로 데이터를 받아오고 로그인 아이디를 담은 memberId를 매개변수로 서버로 전송
        val api = AppServerClass.instance
        val call = api.getIncomeList(memberId)

        call.enqueue(object : Callback<List<IncomeLogDTO>>{
            override fun onResponse(p0: Call<List<IncomeLogDTO>>, res: Response<List<IncomeLogDTO>>) {
                if (res.isSuccessful) {
                    val result = res.body()?.toMutableList()
                    Log.d("csy", "result : $result")

                    val adapter = result?.let { IncomAdapter(it) }

                    binding.incomRecyclerView.layoutManager = LinearLayoutManager(context)
                    binding.incomRecyclerView.adapter = adapter
                    binding.incomRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

                    //  총수입 result 안에 incomeMoney 값을 다 더해서 바인딩
                    var totalIncome = 0
                    for (item in result!!) {
                        totalIncome += item.incomeMoney?.toIntOrNull() ?: 0
                    }
                    binding.incomTotalMoneyData.text = totalIncome.toString()
                }
                else {
                    Log.d("csy", "송신실패")
                }
            }

            override fun onFailure(p0: Call<List<IncomeLogDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })

//        val items = mutableListOf<String>()
//        for (i in 1..12) {
//            items.add("$i")
//        }

//        val adapter = IncomAdapter(items)
//
//        binding.incomRecyclerView.layoutManager = LinearLayoutManager(context)
//        binding.incomRecyclerView.adapter = adapter
//        binding.incomRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentIncom.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentIncom().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}

