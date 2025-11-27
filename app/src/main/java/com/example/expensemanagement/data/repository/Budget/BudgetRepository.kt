package com.example.expensemanagement.data.repository.Budget

import com.example.expensemanagement.data.model.Budget
import kotlinx.coroutines.flow.Flow
import com.example.expensemanagement.data.Result

interface BudgetRepository {
    /**
     * Lấy TẤT CẢ các ngân sách của người dùng hiện tại.
     */
    fun getBudgets(): Flow<List<Budget>>

    suspend fun setBudget(budget: Budget): Result<Unit> // Thêm ngân sách mới
}