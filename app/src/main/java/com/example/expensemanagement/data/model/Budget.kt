package com.example.expensemanagement.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

/**
 * Data Class đại diện cho 1 tài liệu (document) trong collection 'budgets'
 */
data class Budget(
    @DocumentId
    val id: String = "",
    val name: String = "", // Tên của ngân sách, VD: "Ngân sách Ăn uống", "Ngân sách Mua sắm"
    val limitAmount: Double = 0.0, // Hạn mức ngân sách (VD: 4.000.000)
    val spentAmount: Double = 0.0, // Đã chi (Sẽ được cập nhật bằng code)
    val cycle: String = "Hàng Tháng", // Chu kỳ
    val startDate: Date? = null,
    val endDate: Date? = null,

    // ID để liên kết
/*
    val walletId: String = "", // Ngân sách này của Ví nào
    val groupId: String? = null, // Ngân sách này cho Nhóm nào (null = chung)
    val categoryId: String? = null, // Ngân sách này cho Hạng mục nào (null = chung)
    val budgetId: String? = null, // Ngân sách này cho Ngân sách nào (null = chung)
* */

    // 3. THÔNG TIN LIÊN KẾT (quan trọng nhất)
    // Ngân sách này áp dụng cho các hạng mục (Category) nào?
    // Dùng List để một ngân sách có thể áp dụng cho nhiều hạng mục
    // VD: Ngân sách "Ăn Uống" có thể bao gồm các hạng mục "Nhà hàng", "Cà phê", "Đi chợ".
    //val categoryIds: List<String> = emptyList(),

    // 4. THÔNG TIN "PHI CHUẨN HÓA" (DENORMALIZATION) ĐỂ TỐI ƯU HIỂN THỊ
    // Lưu lại tên và icon của CÁC hạng mục để hiển thị nhanh chóng mà không cần đọc thêm từ DB.
    // Dùng Map để lưu cặp (ID -> Tên) và (ID -> Icon).
//    val categoryNames: Map<String, String> = emptyMap(),
//    val categoryIcons: Map<String, String> = emptyMap()

    // 1. Áp dụng cho LOẠI VÍ nào? (VD: "Normal" - Ví thường)
    // Nếu null -> Áp dụng cho tất cả các ví.
    val walletType: String? = null,

    // 1. Áp dụng cho MỘT VÍ CỤ THỂ?
    // (Nếu có giá trị -> Chỉ tính giao dịch của ví này)
    val walletId: String? = null,

    // 2. Áp dụng cho HẠNG MỤC nào? (VD: "Ăn uống")
    // Nếu null -> Áp dụng cho tất cả hạng mục (Ngân sách tổng).
//    val categoryId: String? = null,
//    val categoryName: String? = null, // Lưu tên để hiển thị cho nhanh
//    val categoryIcon: String? = null

    // 3. Áp dụng cho HẠNG MỤC nào?
    // (Nếu rỗng -> Áp dụng cho tất cả chi tiêu)
    val categoryIds: List<String> = emptyList()
)