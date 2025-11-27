package com.example.expensemanagement.data.model

import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel

// Enum để định nghĩa các bộ lọc thời gian
enum class TimeFilter {
    WEEK, MONTH, QUARTER, YEAR
}

// Data class chứa dữ liệu cho một cột trong biểu đồ
data class BarChartData(
    val label: String,      // Nhãn (VD: "Ăn uống")
    val amount: Double,     // Số tiền
)

// State chính cho toàn bộ màn hình Báo cáo
data class ReportUiState(
    val isLoading: Boolean = true,
    val selectedTimeFilter: TimeFilter = TimeFilter.MONTH, // Mặc định là Tháng

    val totalIncome: Double = 0.0,
    val incomeChangePercent: Int = 0, // Thay đổi so với kỳ trước

    val totalExpense: Double = 0.0,
    val expenseChangePercent: Int = 0,

//    val chartData: ChartEntryModel? = null, // Dữ liệu cho thư viện biểu đồ Vico
    val expenseTrend: Map<String, Double> = emptyMap(),

    val error: String? = null
)