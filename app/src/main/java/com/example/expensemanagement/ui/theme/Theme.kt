/*
package com.example.expensemanagement.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ExpenseManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}

 */


package com.example.expensemanagement.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensemanagement.data.repository.Theme.ThemeSetting
import com.example.expensemanagement.viewmodel.ThemeViewModel

// --- ĐỊNH NGHĨA CÁC BẢNG MÀU CHO TỪNG CHỦ ĐỀ ---

// Các bảng màu cho chủ đề SÁNG
private val LightGreenColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    tertiary = Pink80
)
private val LightPurpleColorScheme = lightColorScheme(primary = PurpleThemeColor)
private val LightOrangeColorScheme = lightColorScheme(primary = OrangeThemeColor)
private val LightRedColorScheme = lightColorScheme(primary = RedThemeColor)

// Các bảng màu cho chủ đề TỐI (bạn có thể tùy chỉnh lại cho đẹp hơn)
private val DarkGreenColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    tertiary = Pink40
)
private val DarkPurpleColorScheme = darkColorScheme(primary = PurpleThemeColor)
private val DarkOrangeColorScheme = darkColorScheme(primary = OrangeThemeColor)
private val DarkRedColorScheme = darkColorScheme(primary = RedThemeColor)

@Composable
fun ExpenseManagementTheme(
    // Bỏ các tham số darkTheme và dynamicColor mặc định
    themeViewModel: ThemeViewModel = hiltViewModel(), // Lấy ViewModel để biết lựa chọn của người dùng
    content: @Composable () -> Unit
) {
    // Lấy trạng thái từ ViewModel
    val themeSetting by themeViewModel.themeSetting.collectAsState()
    val colorName by themeViewModel.colorTheme.collectAsState()

    // 1. Xác định dùng Chế độ Tối hay không
    val useDarkTheme = when (themeSetting) {
        ThemeSetting.LIGHT -> false
        ThemeSetting.DARK -> true
        ThemeSetting.SYSTEM -> isSystemInDarkTheme()
    }

    // 2. Dựa vào tên màu và chế độ Sáng/Tối, chọn ra ColorScheme phù hợp
    val colorScheme = when (colorName) {
        "Purple" -> if (useDarkTheme) DarkPurpleColorScheme else LightPurpleColorScheme
        "Orange" -> if (useDarkTheme) DarkOrangeColorScheme else LightOrangeColorScheme
        "Red" -> if (useDarkTheme) DarkRedColorScheme else LightRedColorScheme
        else -> if (useDarkTheme) DarkGreenColorScheme else LightGreenColorScheme // Mặc định là màu xanh
    }

    // 3. Áp dụng màu cho thanh trạng thái (status bar) của hệ thống
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    // 4. Áp dụng chủ đề cho toàn bộ ứng dụng
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
