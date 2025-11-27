package com.example.expensemanagement.data.model.history

import java.util.Date

/**
 * Model dùng riêng cho UI, chứa đầy đủ thông tin để hiển thị một dòng giao dịch.
 * Model này được tạo ra bằng cách kết hợp dữ liệu từ Transaction gốc.
 */
data class TransactionUiModel(
    val id: String,
    val amount: Double,
    val type: String, // "Expense" hoặc "Income"
    val date: Date?,
    val note: String?,

    // Thông tin từ Category (đã được map sang)
    val categoryName: String,
    val categoryIcon: String
)