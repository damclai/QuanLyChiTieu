package com.example.expensemanagement.data.repository.Auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    private val _currentUserFlow = MutableStateFlow(
        auth.currentUser)
    override val currentUserFlow: StateFlow<FirebaseUser?> = _currentUserFlow.asStateFlow()

    init {
        // Lắng nghe sự thay đổi trạng thái đăng nhập của Firebase
        auth.addAuthStateListener { firebaseAuth ->
            _currentUserFlow.value = firebaseAuth.currentUser
        }    }

    override fun logout() {
        auth.signOut()
    }

    // ... (Triển khai các hàm login, register khác ở đây nếu cần)
}
