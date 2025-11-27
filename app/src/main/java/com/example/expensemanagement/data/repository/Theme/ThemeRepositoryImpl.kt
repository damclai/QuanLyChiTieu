
package com.example.expensemanagement.data.repository.Theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Khởi tạo DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ThemeRepository {

    // Định nghĩa các "khóa" để lưu dữ liệu
    private object Keys {
        val THEME_SETTING = stringPreferencesKey("theme_setting")
        val COLOR_THEME = stringPreferencesKey("color_theme")
    }

    override val themeSetting: Flow<ThemeSetting> = context.dataStore.data
        .map { preferences ->
            // Đọc giá trị từ DataStore, nếu không có thì mặc định là SYSTEM
            val settingString = preferences[Keys.THEME_SETTING] ?: ThemeSetting.SYSTEM.name
            ThemeSetting.valueOf(settingString)
        }

    override suspend fun setThemeSetting(setting: ThemeSetting) {
        context.dataStore.edit { preferences ->
            preferences[Keys.THEME_SETTING] = setting.name
        }
    }

    override val colorTheme: Flow<String> = context.dataStore.data
        .map { preferences ->
            // Đọc màu, nếu không có thì mặc định là "PrimaryGreen"
            preferences[Keys.COLOR_THEME] ?: "PrimaryGreen"
        }

    override suspend fun setColorTheme(colorName: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.COLOR_THEME] = colorName
        }
    }
}