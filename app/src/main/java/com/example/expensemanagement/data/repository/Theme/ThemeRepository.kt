package com.example.expensemanagement.data.repository.Theme

import kotlinx.coroutines.flow.Flow

enum class ThemeSetting { LIGHT, DARK, SYSTEM }

interface ThemeRepository {
    // Lấy cài đặt Sáng/Tối hiện tại
    val themeSetting: Flow<ThemeSetting>

    // Lưu cài đặt Sáng/Tối mới
    suspend fun setThemeSetting(setting: ThemeSetting)

    // Lấy mã màu chủ đạo hiện tại (ví dụ: "PrimaryGreen", "Purple")
    val colorTheme: Flow<String>

    // Lưu màu chủ đạo mới
    suspend fun setColorTheme(colorName: String)
}