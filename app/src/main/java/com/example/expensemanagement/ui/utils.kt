package com.example.expensemanagement.ui

import com.example.expensemanagement.R

/**
 * Hàm này nhận vào một tên icon (String) được lưu trong Firestore
 * và trả về ID của Drawable Resource tương ứng trong app của bạn.
 */
fun mapIconNameToResource(iconName: String): Int {
    return when (iconName) {
        "ic_food" -> R.drawable.ic_food
        "ic_transport" -> R.drawable.ic_car
        "ic_bill" -> R.drawable.ic_bill
//        "ic_entertainment" -> R.drawable.ic_entertainment
        "ic_shopping" -> R.drawable.ic_shopping
        "ic_salary" -> R.drawable.ic_salary
        // ... thêm các icon khác của bạn vào đây

        // Luôn có một icon mặc định để tránh app bị crash nếu không tìm thấy
        else -> R.drawable.ic_piggy_bank
    }
}