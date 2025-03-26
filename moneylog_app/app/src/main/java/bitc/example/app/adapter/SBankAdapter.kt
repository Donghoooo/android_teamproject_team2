package bitc.example.app.adapterimport

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R


class SBankAdapter(
    private val banks: List<String>,
    private var selectedBanks: String?,  // 단일 선택을 위한 변수
    private val onBankSelected: (String) -> Unit
) : RecyclerView.Adapter<SBankAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val radioButton: RadioButton = itemView.findViewById(R.id.checkbox_bank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bank_change, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = banks[position]
        holder.radioButton.text = bank
        holder.radioButton.isChecked = bank == selectedBanks

        holder.radioButton.setOnClickListener {
            selectedBanks = bank
            Log.d("SBankAdapter", "선택된 은행: $selectedBanks")
            onBankSelected(bank)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = banks.size
}