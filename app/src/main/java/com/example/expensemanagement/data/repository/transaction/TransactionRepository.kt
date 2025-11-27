package com.example.expensemanagement.data.repository.transaction

import com.example.expensemanagement.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import com.example.expensemanagement.data.Result
import java.util.Date


/**
 * Interface (Hợp đồng) cho Kho chứa Giao dịch.
 */
interface TransactionRepository {

    /**
     * Lấy TẤT CẢ các giao dịch của người dùng hiện tại (theo thời gian thực).
     */
    fun getTransactions(): Flow<List<Transaction>>

    /**
     * Lấy một giao dịch cụ thể bằng ID của nó.
     * Hữu ích khi xem chi tiết hoặc chỉnh sửa một giao dịch.
     * @param transactionId ID của giao dịch cần lấy.
     */
    fun getTransactionById(transactionId: String): Flow<Transaction?>

    /**
     * Thêm một giao dịch mới vào Firestore.
     * @param transaction Đối tượng Transaction chứa thông tin cần thêm.
     * @return Result để biết tác vụ thành công hay thất bại.
     */
    suspend fun addTransaction(transaction: Transaction): Result<Unit>

    /**
     * Cập nhật một giao dịch đã có.
     * @param transaction Đối tượng Transaction chứa thông tin đã được cập nhật.
     * @return Result để biết tác vụ thành công hay thất bại.
     */
    suspend fun updateTransaction(transaction: Transaction): Result<Unit>

    /**
     * Xóa một giao dịch khỏi Firestore.
     * @param transactionId ID của giao dịch cần xóa.
     * @return Result để biết tác vụ thành công hay thất bại.
     */
    suspend fun deleteTransaction(transactionId: String): Result<Unit>

    /**
     * Lấy các giao dịch CHỈ của 1 ví cụ thể.
     */
    fun getTransactionsByWallet(walletId: String): Flow<List<Transaction>>

    // thêm hàm cho ReportUistate
    fun getTransactionsBetween(startDate: Date, endDate: Date): Flow<List<Transaction>>

    // (Chúng ta sẽ thêm addTransaction, deleteTransaction... sau)
}