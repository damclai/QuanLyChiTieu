package com.example.expensemanagement.data.model.history

import java.util.Date

/**
 * Sealed Interface này định nghĩa tất cả các loại item có thể có trong
 * danh sách Lịch sử Giao dịch, giúp LazyColumn hiển thị nhiều loại giao diện.
 */
sealed interface TransactionHistoryItem {
    /**
     * Data class này đại diện cho một "Tiêu đề Ngày" (ví dụ: "Hôm nay, 8 tháng 11").
     */
    data class Header(val date: Date) : TransactionHistoryItem

    /**
     * Data class này đại diện cho một "Item Giao dịch".
     * Nó chứa một đối tượng TransactionUiModel đã được xử lý.
     */
    data class TransactionRow(val transactionUiModel: TransactionUiModel) : TransactionHistoryItem
}