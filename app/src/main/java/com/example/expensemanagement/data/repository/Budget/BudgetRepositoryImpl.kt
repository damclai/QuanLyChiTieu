package com.example.expensemanagement.data.repository.Budget

import com.example.expensemanagement.data.Result
import com.example.expensemanagement.data.model.Budget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BudgetRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : BudgetRepository {

    private val userBudgetsCollection
        get() = auth.currentUser?.uid?.let { userId ->
            firestore.collection("users").document(userId).collection("budgets")
        }

    override fun getBudgets(): Flow<List<Budget>> {
        val collection = userBudgetsCollection ?: return flowOf(emptyList())

        return collection.snapshots().map { snapshot ->
            snapshot.toObjects<Budget>()
        }
    }

    override suspend fun setBudget(budget: Budget): Result<Unit> {
        val collection = userBudgetsCollection
            ?: return Result.Error(IllegalStateException("User not logged in"))
        return try {
            // Nếu budget chưa có ID, tạo ID mới
            val docRef = if (budget.id.isNotEmpty()) {
                collection.document(budget.id)
            } else {
                collection.document() // Tự sinh ID
            }

            // Lưu budget với ID đã có (đảm bảo ID trong object khớp với ID document)
            val budgetWithId = budget.copy(id = docRef.id)

            //  DÙNG `await()` CHO TÁC VỤ `set`
            docRef.set(budgetWithId).await()

            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}