package com.example.expensemanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensemanagement.ui.navigation.AppDestinations
import com.example.expensemanagement.ui.navigation.AppNavigation
import com.example.expensemanagement.ui.theme.ExpenseManagementTheme
import com.example.expensemanagement.viewmodel.AuthState
import com.example.expensemanagement.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseManagementTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {

        // Dùng 'when' để quyết định hiển thị cái gì
        when (authState) {

            // 1. Nếu đang tải (mới mở app), hiển thị vòng quay loading
            // (Chúng ta gộp Idle vào đây luôn, vì nó cũng là trạng thái chờ)
            is AuthState.Loading, is AuthState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // 2. Nếu đã đăng nhập, chạy AppNavigation bắt đầu từ Home
            is AuthState.Authenticated -> {
                AppNavigation(startDestination = AppDestinations.Home.route)
            }

            // 3. SỬA LỖI Ở ĐÂY:
            // Nếu chưa đăng nhập, hoặc có lỗi, hoặc báo thành công (như gửi link)
            // thì vẫn ở luồng xác thực (bắt đầu từ Onboarding).
            is AuthState.Unauthenticated, is AuthState.Error, is AuthState.Success -> {
                AppNavigation(startDestination = AppDestinations.Onboarding.route)
            }

            // Cách 2: Bạn cũng có thể dùng 'else' để bắt tất cả trường hợp còn lại
             else -> {
                 AppNavigation(startDestination = AppDestinations.Onboarding.route)
             }
        }
    }
}