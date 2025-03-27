package bitc.example.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.example.app.R
import bitc.example.app.dto.MainListDTO


class ListAdapter(private val mainList: List<MainListDTO>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cateTextView: TextView = view.findViewById(R.id.main_list_cate)
        val useeTextView: TextView = view.findViewById(R.id.main_list_usee)
        val wayTextView: TextView = view.findViewById(R.id.main_list_way)
        val amountTextView: TextView = view.findViewById(R.id.main_list_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val mainList = mainList[position]
        holder.cateTextView.text = "${mainList.cate}"
        holder.useeTextView.text = "${mainList.usee}"
        holder.wayTextView.text = "${mainList.way}"
        holder.amountTextView.text = "${mainList.amount}원"
    }

    override fun getItemCount(): Int = mainList.size
}

