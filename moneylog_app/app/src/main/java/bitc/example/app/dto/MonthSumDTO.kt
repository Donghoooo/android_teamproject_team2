package bitc.example.app.dto

import com.google.gson.annotations.SerializedName

data class MonthSumDTO(
    @SerializedName("totalIncome")
    val totalIncome: Int? = null,
    @SerializedName("totalExpense")
    val totalExpense: Int? = null
)
