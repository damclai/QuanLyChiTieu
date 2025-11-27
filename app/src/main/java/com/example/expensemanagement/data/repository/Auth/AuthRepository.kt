package com.example.expensemanagement.data.repository.Auth

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow


interface AuthRepository {

    // Luồng để lắng nghe thông tin người dùng hiện tại
    val currentUserFlow: StateFlow<FirebaseUser?>

    // Hàm đăng xuất
    fun logout()

// ... ( có thể sẽ cần thêm các hàm khác như login, regist... say này)
}