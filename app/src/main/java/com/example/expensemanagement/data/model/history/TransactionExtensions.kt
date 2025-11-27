package com.example.expensemanagement.data.model.history

import androidx.compose.ui.input.key.type
import com.example.expensemanagement.data.model.history.TransactionUiModel
import com.example.expensemanagement.data.model.Transaction

/**
 * Hàm mở rộng (extension function) để dễ dàng chuyển đổi từ
 * Transaction (model gốc) sang TransactionUiModel (model cho UI).
 * dùng hàm này trong ViewModel.
 */

fun Transaction.toUiModel(): TransactionUiModel {
    return TransactionUiModel(
        id = this.id,
        amount = this.amount,
        type = this.type,
        date = this.date,
        note = this.note,

        // Các trường này đã được lưu sẵn trong Transaction gốc nhờ kỹ thuật denormalization
        categoryName = this.categoryName,
        categoryIcon = this.categoryIcon
    )
}