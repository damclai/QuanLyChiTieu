package com.example.expensemanagement.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Data Class đại diện cho 1 tài liệu (document) trong collection 'transactions'
 */
data class Transaction(
    @DocumentId
    val id: String = "",

    val amount: Double = 0.0,
    val type: String = "Expense", // "Expense" (Chi) hoặc "Income" (Thu)
    val date: Date? = null,
    val note: String? = null,

    // ID để liên kết
    val walletId: String = "",
    val categoryId: String = "",

    // --- CÁC TRƯỜNG TỐI ƯU HIỂN THỊ ---
    val walletName: String = "",    // Tên của ví tại thời điểm giao dịch
    val categoryName: String = "",  // Tên của danh mục tại thời điểm giao dịch
    val categoryIcon: String = ""   // Icon của danh mục tại thời điểm giao dịch

    // (Bạn có thể thêm categoryName, walletName... sau để tối ưu)
)