package com.example.expensemanagement.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)





// gốc của WalletDetailViewModel
//package com.example.expensemanagement.viewmodel
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.expensemanagement.data.model.Transaction
//import com.example.expensemanagement.data.model.Wallet
//import com.example.expensemanagement.data.repository.transaction.TransactionRepository
//import com.example.expensemanagement.data.repository.WalletRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//// Trạng thái UI cho Màn 2 (Chi Tiết Ví)
//// (Nó chứa TOÀN BỘ dữ liệu mà Màn 2 cần)
//data class WalletDetailUiState(
//    val isLoading: Boolean = true,
//
//    // Dữ liệu Khối 1 (Số Dư)
//    val totalBalance: Double = 0.0,
//
//    // Dữ liệu Khối 2 (Ngân Sách)
//    val spentAmount: Double = 0.0, // "Đã chi" (THẬT)
//    val remainingAmount: Double = 0.0, // "Còn lại" (THẬT)
//    val budgetProgress: Float = 0f, // Thanh tiến trình
//
//    // Dữ liệu Khối 3 (Giao Dịch)
//    val transactions: List<Transaction> = emptyList(),
//
//    val walletType: String = "", // Tên nhóm (để hiển thị tiêu đề)
//    val errorMessage: String? = null
//)
//
//@HiltViewModel
//class WalletDetailViewModel @Inject constructor(
//    private val walletRepository: WalletRepository, // Kho Ví (của bạn)
//    private val transactionRepository: TransactionRepository, // Kho Giao dịch (của bạn)
//    // TODO: Thêm BudgetRepository
//    savedStateHandle: SavedStateHandle // Dùng để lấy ID từ navigation
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow(WalletDetailUiState())
//    val uiState: StateFlow<WalletDetailUiState> = _uiState.asStateFlow()
//
//    // Lấy "groupId" (ví dụ: "Normal", "Saving") mà Màn 1 (Home) đã gửi
//    private val groupId: String = savedStateHandle.get<String>("groupId")!!
//
//    init {
//        fetchWalletDetails()
//    }
//
//    private fun fetchWalletDetails() {
//        viewModelScope.launch {
//            // Lấy 2 nguồn: (1) TẤT CẢ Ví và (2) TẤT CẢ Giao dịch
//            // (Chúng ta dùng Repository "xịn" của bạn)
//            val walletsFlow = walletRepository.getWallets()
//            val transactionsFlow = transactionRepository.getTransactions()
//
//            // TODO: Chúng ta sẽ combine 3 nguồn (thêm BudgetFlow) ở đây
//
//
//            // Kết hợp (combine) 2 flow này lại
//            walletsFlow.combine(flow = transactionsFlow) { walletList, transactionList ->
//
//                // --- BẮT ĐẦU LỌC (Filter) ---
//
//                // 1. Lọc ra các ví CHỈ thuộc nhóm này (Ví dụ: "Normal")
//                val groupWallets = walletList.filter {
//                    val walletType = when(it.type) {
//                        "Normal" -> "Ví thường"
//                        "Saving" -> "Ví tiết kiệm"
//                        "Credit" -> "Ví tín dụng"
//                        else -> it.type
//                    }
//                    walletType == groupId
//                }
//
//                // 2. Lấy ID của các ví đó
//                val groupWalletIds = groupWallets.map { it.id }
//
//                // 3. Lọc ra các giao dịch CHỈ thuộc các ví đó
//                val groupTransactions = transactionList.filter {
//                    it.walletId in groupWalletIds
//                }
//
//                // --- BẮT ĐẦU TÍNH TOÁN (Y HỆT FIGMA) ---
//
//                // 1. Tính "Số Dư" (Khối 1)
//                val totalBalance = groupWallets.sumOf { it.balance }
//
//                // 2. Tính "Đã chi" (THẬT) (Khối 2)
//                // (Đây là "Đã chi" của TOÀN BỘ giao dịch chi tiêu)
//                val totalSpent = groupTransactions
//                    .filter { it.type == "Expense" }
//                    .sumOf { it.amount }
//
//                // 3. Tính "Ngân Sách" (Khối 2)
//                // (CHƯA CÓ BudgetRepository, TÔI SẼ GIẢ LẬP (MOCK) ĐỂ HIỂN THỊ)
//                val mockTotalBudget = when(groupId) {
//                    "Ví thường" -> 20000000.0
//                    "Ví tiết kiệm" -> 50000000.0
//                    "Ví tín dụng" -> 40000000.0
//                    else -> 1000000.0
//                }
//
//                val remainingAmount = mockTotalBudget - totalSpent // "Còn lại" (THẬT)
//
//                // Tính thanh Progress
//                val progress = if (mockTotalBudget > 0) {
//                    (totalSpent / mockTotalBudget).toFloat()
//                } else {
//                    0f
//                }
//
//                // Cập nhật UI
//                WalletDetailUiState(
//                    isLoading = false,
//                    totalBalance = totalBalance, // Khối 1
//                    spentAmount = totalSpent, // Khối 2
//                    remainingAmount = remainingAmount, // Khối 2
//                    budgetProgress = progress, // Khối 2
//                    transactions = groupTransactions, // Khối 3
//                    walletType = groupId // Tên tiêu đề
//                )
//
//            }
//                .catch { e ->
//                    _uiState.value = WalletDetailUiState(isLoading = false, errorMessage = e.message)
//                }
//                .collect { newState ->
//                    _uiState.value = newState
//                }
//        }
//    }
//}




//thay đổi của WalletDetailViewModel

/*

package com.example.expensemanagement.viewmodel

import androidx.compose.ui.input.key.type
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanagement.data.model.Transaction
import com.example.expensemanagement.data.model.Budget //
import com.example.expensemanagement.data.repository.Budget.BudgetRepository
import com.example.expensemanagement.data.repository.transaction.TransactionRepository
import com.example.expensemanagement.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class trạng thái UI giữ nguyên
data class WalletDetailUiState(
    val isLoading: Boolean = true,
    val totalBalance: Double = 0.0,
    val spentAmount: Double = 0.0,
    val remainingAmount: Double = 0.0,
    val budgetProgress: Float = 0f,
    val transactions: List<Transaction> = emptyList(),
    val walletType: String = "",
    val errorMessage: String? = null
)

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository, // <-- SỬA 1: TIÊM BUDGET REPOSITORY VÀO
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(WalletDetailUiState())
    val uiState: StateFlow<WalletDetailUiState> = _uiState.asStateFlow()

    // Lấy 'walletId' từ navigation, không phải 'groupId'
    // Màn hình này là chi tiết của MỘT ví, nên nó cần 'walletId'
    private val walletId: String = savedStateHandle.get<String>("walletId")!!

    init {
        fetchWalletDetails()
    }

    private fun fetchWalletDetails() {
        viewModelScope.launch {
            // SỬA 2: LẤY 3 NGUỒN DỮ LIỆU
            val walletFlow = walletRepository.getWalletById(walletId) // Chỉ lấy 1 ví cụ thể
            val transactionsFlow = transactionRepository.getTransactionsByWallet(walletId) // Chỉ lấy giao dịch của ví này
            val budgetsFlow = budgetRepository.getBudgets() // Lấy tất cả ngân sách

            // Kết hợp (combine) 3 flow này lại
            combine(walletFlow, transactionsFlow, budgetsFlow) { wallet, transactionList, budgetList ->

                // Nếu ví không tồn tại, báo lỗi
                if (wallet == null) {
                    return@combine WalletDetailUiState(isLoading = false, errorMessage = "Không tìm thấy ví.")
                }

                // --- BẮT ĐẦU TÍNH TOÁN (LOGIC MỚI HOÀN TOÀN) ---

                // 1. "Số Dư" (Khối 1) - Lấy trực tiếp từ ví
                val totalBalance = wallet.balance // <-- SỬA 3: DÙNG initialBalance

                // 2. Tính "Đã chi" (Khối 2) - Tổng các giao dịch chi tiêu của ví này
                val totalSpent = transactionList
                    .filter { it.type == "Expense" }
                    .sumOf { it.amount }

                // 3. Tính "Ngân Sách" (Khối 2) - DÙNG DỮ LIỆU THẬT
                // Giả sử chúng ta chỉ quan tâm đến ngân sách đầu tiên được thiết lập
                val currentBudget = budgetList.firstOrNull() // Lấy ngân sách đầu tiên, hoặc null
                val totalBudgetLimit = currentBudget?.limitAmount ?: 0.0 // Hạn mức của ngân sách đó, nếu không có thì là 0

                val remainingAmount = totalBudgetLimit - totalSpent // "Còn lại"

                // Tính thanh Progress
                val progress = if (totalBudgetLimit > 0) {
                    (totalSpent / totalBudgetLimit).toFloat().coerceIn(0f, 1f) // Giới hạn giá trị từ 0 đến 1
                } else {
                    0f
                }

                // --- Cập nhật UI với dữ liệu thật ---
                WalletDetailUiState(
                    isLoading = false,
                    totalBalance = totalBalance,
                    spentAmount = totalSpent,
                    remainingAmount = remainingAmount,
                    budgetProgress = progress,
                    transactions = transactionList, // Danh sách giao dịch đã được lọc sẵn từ Repository
                    walletType = wallet.name // Lấy tên ví làm tiêu đề
                )

            }.catch { e ->
                // Xử lý lỗi nếu có bất kỳ flow nào thất bại
                _uiState.value = WalletDetailUiState(isLoading = false, errorMessage = e.message)
                e.printStackTrace()
            }.collect { newState ->
                // Cập nhật trạng thái UI cuối cùng
                _uiState.value = newState
            }
        }
    }
}
* */



// gốc
/*
package com.example.expensemanagement.ui.screens.budget

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensemanagement.ui.theme.PrimaryGreen
import com.example.expensemanagement.viewmodel.BudgetSetupUiState
import com.example.expensemanagement.viewmodel.BudgetSetupViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSetupScreen(
//    walletId: String, // ID được truyền từ màn hình trước
    onNavigateBack: () -> Unit,
    viewModel: BudgetSetupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Các biến State nhập liệu
    val amount by viewModel.amount.collectAsStateWithLifecycle()
    val cycle by viewModel.cycle.collectAsStateWithLifecycle()
    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val category by viewModel.selectedCategory.collectAsStateWithLifecycle()

    val context = LocalContext.current

    // Xử lý kết quả lưu
    LaunchedEffect(uiState) {
        when(uiState) {
            is BudgetSetupUiState.Success -> {
                Toast.makeText(context, "Lưu ngân sách thành công!", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                onNavigateBack()
            }
            is BudgetSetupUiState.Error -> {
                Toast.makeText(context, (uiState as BudgetSetupUiState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // State cho DatePicker và Dropdown
    var showDatePicker by remember { mutableStateOf(false) }
    // (Dropdown logic có thể thêm sau, tạm thời dùng Text hoặc click đơn giản)

    Scaffold(
        containerColor = Color.White // Nền trắng toàn màn hình
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Cho phép cuộn
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. BANNER "Thiết Lập Ngân Sách" (Giống ảnh)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFD1F5F1)), // Màu xanh nhạt
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Thiết Lập Ngân Sách",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray) // Gạch ngang mờ
            Spacer(modifier = Modifier.height(24.dp))

            // 2. Hạn Mức Ngân Sách (Ô nhập tiền to)
            Text(
                text = "Hạn Mức Ngân Sách",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Ô nhập tiền cách điệu
            OutlinedTextField(
                value = amount,
                onValueChange = viewModel::onAmountChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red, // Số tiền màu đỏ
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                placeholder = {
                    Text(
                        "0 VND",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.LightGray
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(24.dp), // Bo tròn nhiều như ảnh
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0F7FA), // Nền xanh rất nhạt khi focus
                    unfocusedContainerColor = Color(0xFFE0F7FA),
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = Color.Gray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Chu kỳ Áp Dụng
            RowItemSelector(
                label = "Chu kỳ\nAp Dụng",
                value = cycle,
                icon = Icons.Default.Lock, // Icon cái khóa
                iconColor = Color(0xFFFFC107), // Màu vàng
                onClick = { /* TODO: Mở dialog chọn chu kỳ */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Ngày Bắt Đầu
            RowItemSelector(
                label = "Ngày Bắt\nĐầu",
                value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate),
                icon = Icons.Default.CalendarMonth, // Icon lịch
                iconColor = Color(0xFFF48FB1), // Màu hồng nhạt
                onClick = { showDatePicker = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Hạng Mục Chi Tiêu
            Text(
                text = "Hạng Mục Chi Tiêu",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Ô chọn hạng mục
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { /* TODO: Mở dialog chọn hạng mục */ },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Category, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = category, // "Ngân Sách Gia Đình" hoặc "Ăn uống"...
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E), // Xanh đậm
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    // Icon mũi tên xuống (nếu cần)
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Đẩy 2 nút xuống dưới

            // 6. Hai nút Hủy / Lưu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Nút Hủy
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(50.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("Hủy", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                // Nút Lưu
                Button(
                    onClick = { viewModel.saveBudget() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80DEEA)), // Màu xanh ngọc
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .height(50.dp),
                    enabled = (uiState != BudgetSetupUiState.Loading)
                ) {
                    if (uiState == BudgetSetupUiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Lưu", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Dialog chọn ngày
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { viewModel.onStartDateChange(Date(it)) }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Hủy") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

/**
 * Composable phụ để vẽ các dòng "Label - Ô nhập" (Chu kỳ, Ngày bắt đầu)
 */
@Composable
fun RowItemSelector(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Label bên trái (In nghiêng, đậm)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.weight(0.3f)
        )

        // Ô giá trị bên phải
        Card(
            modifier = Modifier
                .weight(0.7f)
                .height(50.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = iconColor)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

* */


// gốc

/*
package com.example.expensemanagement.ui.screens.transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.example.expensemanagement.ui.screens.home.formatCurrency // Dùng lại hàm
import com.example.expensemanagement.viewmodel.AddTransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.isSuccess, uiState.error) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Lưu giao dịch thành công!", Toast.LENGTH_SHORT).show()
            viewModel.resetState()
            onNavigateBack()
        }
        if (uiState.error != null) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
        }
    }

    // State cho Dialog
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showWalletDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Thêm Giao Dịch Mới", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Nút Chuyển Loại (Chi Tiêu / Thu Nhập)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.onTypeChanged("Expense") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.type == "Expense") Color(0xFFEF5350) else Color.LightGray
                    ),
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Chi Tiêu")
                }

                Button(
                    onClick = { viewModel.onTypeChanged("Income") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.type == "Income") Color(0xFF66BB6A) else Color.LightGray
                    ),
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                ) {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Thu Nhập")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Form Nhập Liệu (ScrollView)
            Column(modifier = Modifier.weight(1f)) {

                // Số Tiền
                TransactionInputField(
                    title = "Số Tiền",
                    value = uiState.amount,
                    onValueChange = viewModel::onAmountChanged,
                    isAmount = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Hạng Mục
                TransactionSelectorField(
                    title = "Hạng Mục",
                    text = uiState.selectedCategory?.name ?: "Chọn hạng mục",
                    icon = Icons.Default.Category,
                    onClick = { showCategoryDialog = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Ghi Chú
                TransactionInputField(
                    title = "Ghi Chú",
                    value = uiState.note,
                    onValueChange = viewModel::onNoteChanged,
                    icon = Icons.Default.Edit,
                    placeholder = "Thêm ghi chú..."
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Ví
                TransactionSelectorField(
                    title = "Ví",
                    text = uiState.selectedWallet?.name ?: "Chọn ví",
                    icon = Icons.Default.AccountBalanceWallet,
                    onClick = { showWalletDialog = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Ngày Giờ
                val dateFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
                val dateString = dateFormat.format(uiState.date)

                // Logic chọn ngày giờ
                val calendar = Calendar.getInstance()
                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)
                        viewModel.onDateChanged(calendar.time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        calendar.set(year, month, day)
                        timePickerDialog.show() // Chọn ngày xong hiện chọn giờ
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                TransactionSelectorField(
                    title = "Thời Gian",
                    text = dateString,
                    icon = Icons.Default.AccessTime,
                    onClick = { datePickerDialog.show() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Nút Lưu Giao Dịch
            Button(
                onClick = { viewModel.saveTransaction() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
                shape = RoundedCornerShape(16.dp),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Lưu Giao Dịch", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // --- Dialog Hạng Mục ---
    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = { Text("Chọn Hạng Mục") },
            text = {
                LazyColumn {
                    items(uiState.categories.size) { index ->
                        val category = uiState.categories[index]
                        TextButton(
                            onClick = {
                                viewModel.onCategorySelected(category)
                                showCategoryDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(category.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCategoryDialog = false }) { Text("Đóng") }
            }
        )
    }

    // --- Dialog Ví ---
    if (showWalletDialog) {
        AlertDialog(
            onDismissRequest = { showWalletDialog = false },
            title = { Text("Chọn Ví") },
            text = {
                LazyColumn {
                    items(uiState.wallets.size) { index ->
                        val wallet = uiState.wallets[index]
                        TextButton(
                            onClick = {
                                viewModel.onWalletSelected(wallet)
                                showWalletDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(wallet.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
                            Text(wallet.balance.formatCurrency(), color = if(wallet.type=="Credit") Color.Red else Color.Black)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showWalletDialog = false }) { Text("Đóng") }
            }
        )
    }
}

// --- COMPOSABLE PHỤ TRỢ ---

@Composable
fun TransactionInputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    placeholder: String? = null,
    isAmount: Boolean = false
) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFE0E0E0),
            unfocusedContainerColor = Color(0xFFE0E0E0),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        placeholder = { Text(placeholder ?: if (isAmount) "0 VND" else "") },
        trailingIcon = {
            if (isAmount) Text("VND", modifier = Modifier.padding(end = 16.dp), fontWeight = FontWeight.Bold)
        },
        leadingIcon = if (icon != null) { { Icon(icon, contentDescription = null) } } else null,
        keyboardOptions = KeyboardOptions(keyboardType = if (isAmount) KeyboardType.Number else KeyboardType.Text),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
fun TransactionSelectorField(
    title: String,
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE0E0E0))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
    }
}
* */


// gốc

/*
package com.example.expensemanagement.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanagement.data.Result
import com.example.expensemanagement.data.model.Category
import com.example.expensemanagement.data.model.Transaction
import com.example.expensemanagement.data.model.Wallet
import com.example.expensemanagement.data.repository.Category.CategoryRepository
import com.example.expensemanagement.data.repository.WalletRepository
import com.example.expensemanagement.data.repository.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

// Trạng thái UI
data class AddTransactionUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,

    val type: String = "Expense", // "Expense" hoặc "Income"
    val amount: String = "",
    val note: String = "",
    val date: Date = Date(),

    // Dữ liệu để chọn
    val wallets: List<Wallet> = emptyList(),
    val categories: List<Category> = emptyList(),

    // Cái đã chọn
    val selectedWallet: Wallet? = null,
    val selectedCategory: Category? = null
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository,
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    // Lấy ID ví nếu được truyền từ màn hình trước (để chọn sẵn)
    private val initialWalletId: String? = savedStateHandle.get<String>("walletId")

    init {
        loadData()
        viewModelScope.launch { categoryRepository.initDefaultCategories() }
    }

    private fun loadData() {
        val currentType = _uiState.value.type

        // 1. Lấy danh sách Ví
        viewModelScope.launch {
            walletRepository.getWallets().collect { wallets ->
                _uiState.update { state ->
                    // Tự chọn ví nếu có ID truyền vào, hoặc chọn ví đầu tiên
                    val preSelected = wallets.find { it.id == initialWalletId } ?: wallets.firstOrNull()
                    state.copy(wallets = wallets, selectedWallet = state.selectedWallet ?: preSelected)
                }
            }
        }

        // 2. Lấy danh sách Hạng mục (theo loại)
        viewModelScope.launch {
            categoryRepository.getCategories(currentType).collect { categories ->
                _uiState.update { state ->
                    state.copy(categories = categories, selectedCategory = null)
                }
            }
        }
    }

    // --- Các hàm cập nhật UI ---

    fun onTypeChanged(newType: String) {
        _uiState.update { it.copy(type = newType) }
        // Load lại danh mục tương ứng
        viewModelScope.launch {
            categoryRepository.getCategories(newType).collect { categories ->
                _uiState.update { it.copy(categories = categories, selectedCategory = null) }
            }
        }
    }

    fun onAmountChanged(newAmount: String) {
        if (newAmount.all { it.isDigit() }) {
            _uiState.update { it.copy(amount = newAmount) }
        }
    }

    fun onNoteChanged(newNote: String) { _uiState.update { it.copy(note = newNote) } }
    fun onDateChanged(newDate: Date) { _uiState.update { it.copy(date = newDate) } }
    fun onWalletSelected(wallet: Wallet) { _uiState.update { it.copy(selectedWallet = wallet) } }
    fun onCategorySelected(category: Category) { _uiState.update { it.copy(selectedCategory = category) } }

    // --- Hàm Lưu Giao Dịch ---
    fun saveTransaction() {
        val state = _uiState.value
        val amountVal = state.amount.toDoubleOrNull()

        if (amountVal == null || amountVal <= 0) {
            _uiState.update { it.copy(error = "Vui lòng nhập số tiền hợp lệ") }
            return
        }
        if (state.selectedCategory == null) {
            _uiState.update { it.copy(error = "Vui lòng chọn hạng mục") }
            return
        }
        if (state.selectedWallet == null) {
            _uiState.update { it.copy(error = "Vui lòng chọn ví") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val newTransaction = Transaction(
                amount = amountVal,
                type = state.type,
                date = state.date,
                note = state.note.ifBlank { state.selectedCategory!!.name },
                walletId = state.selectedWallet!!.id,
                categoryId = state.selectedCategory!!.id
            )

            // 1. Lưu Giao Dịch
            val result = transactionRepository.addTransaction(newTransaction)

            if (result is Result.Success) {
                // 2. CẬP NHẬT SỐ DƯ VÍ
                val currentBalance = state.selectedWallet!!.balance
                val amountDelta = if (state.type == "Expense") -amountVal else amountVal

                val updatedWallet = state.selectedWallet!!.copy(
                    balance = currentBalance + amountDelta
                )

                val walletUpdateResult = walletRepository.updateWallet(updatedWallet)

                if (walletUpdateResult is Result.Success) {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Lưu giao dịch thành công nhưng lỗi cập nhật số dư.") }
                }

            } else {
                // Lấy message lỗi an toàn
                val errorMsg = (result as? Result.Error)?.exception?.message ?: "Lỗi không xác định"
                _uiState.update { it.copy(isLoading = false, error = "Lỗi lưu: $errorMsg") }
            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(isSuccess = false, error = null, amount = "", note = "") }
    }
}
* */



/// home
/*
package com.example.expensemanagement.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensemanagement.R
import com.example.expensemanagement.data.model.Wallet
import com.example.expensemanagement.ui.theme.PrimaryGreen
import com.example.expensemanagement.viewmodel.HomeUiState
import com.example.expensemanagement.viewmodel.HomeViewModel
import com.example.expensemanagement.viewmodel.WalletGroupSummary
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import com.example.expensemanagement.ui.components.AppBottomNavBar
import androidx.compose.material3.Surface
import androidx.navigation.NavController



// Hàm định dạng tiền tệ (Giữ nguyên)
fun Double.formatCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    return format.format(this).replace("₫", " VND").replace(" ", "") // Chuyển ₫ thành VND
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddWalletClicked: () -> Unit, // Hàm xử lý khi người dùng nhấn vào "Thêm ví"
    //onWalletClicked: (String) -> Unit, // Hàm xử lý khi người dùng nhấn vào 1 ví
    onWalletGroupClicked: (String) -> Unit, // Nhấn vào NHÓM ví (Ví dụ: "Ví thường")
    onAddTransactionClicked: () -> Unit, // Hàm xử lý khi người dùng nhấn vào "Thêm giao dịch"
    navController: NavController, // Dùng để điều hướng
    // (Chúng ta sẽ thêm các hàm điều hướng cho các tab khác sau)
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Lắng nghe trạng thái từ ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // State để ẩn/hiện số dư
    var isBalanceVisible by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            // Thanh Top Bar cho màn hình Ví
            TopAppBar(
                title = { Text("Ví", fontWeight = FontWeight.Bold) },
                actions = {
                    // Nút Thêm Ví Mới
                    IconButton(onClick = onAddWalletClicked) {
                        Icon(Icons.Default.Add, contentDescription = "Thêm ví mới")
                    }
                    // Nút Menu (3 gạch)
                    IconButton(onClick = { /* TODO: Mở menu cài đặt nhanh */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },

        // Đây là Bottom Navigation Bar như trong phác thảo của bạn
        bottomBar = {
            AppBottomNavBar(
                navController = navController,
            )
        },

        floatingActionButton = {
            if (uiState is HomeUiState.Success && !(uiState as HomeUiState.Success).isEmpty) {
                FloatingActionButton(
                    onClick = onAddTransactionClicked,
                    containerColor = PrimaryGreen,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm giao dịch")
                }
            }
        },
        //floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        // Dùng 'when' để quyết định hiển thị cái gì
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            }
            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) { Text("Lỗi: ${state.message}", color = Color.Red) }
            }
            is HomeUiState.Success -> {
                if (state.isEmpty) {
                    // 1. TRẠNG THÁI RỖNG (MÀN 1)
                    WalletEmptyState(
                        onAddWalletClicked = onAddWalletClicked,
                        modifier = Modifier.padding(paddingValues)
                    )
                } else {
                    // 2. TRẠNG THÁI CÓ DỮ LIỆU (DANH SÁCH VÍ CHI TIẾT)
                    WalletListContent(
                        groupedWallets = state.walletGroups,
                        isBalanceVisible = isBalanceVisible,
                        onToggleVisibility = { isBalanceVisible = !isBalanceVisible },
                        onWalletGroupClicked = onWalletGroupClicked,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

/**
 * Giao diện chính khi đã có ví (Hiển thị chi tiết theo nhóm)
 */
@Composable
fun WalletListContent(
//    groupedWallets: List<WalletGroup>,
//    grandTotalBalance: Double,
//    onWalletClicked: (String) -> Unit,
//    modifier: Modifier = Modifier

    groupedWallets: List<WalletGroupSummary>,
    isBalanceVisible: Boolean,
    onToggleVisibility: () -> Unit,
    onWalletGroupClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp), // Thêm padding dưới cùng cho FAB
//        verticalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa các nhóm
    ) {
        // 1. Dòng chữ "Danh sách ví"
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Khoảng cách với Card dưới
            ) {
                Text(
                    text = "Danh sách ví",
                    style = MaterialTheme.typography.titleLarge, // Sửa: titleLarge (To hơn)
                    fontWeight = FontWeight.ExtraBold, // Sửa: ExtraBold (Đậm hơn)
                    color = PrimaryGreen // SỬA: DÙNG MÀU XANH CHỦ ĐẠO
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Thêm gạch chân trang trí
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(0.3f), // Chỉ dài bằng 30%
                    thickness = 4.dp, // Dày 4dp
                    color = PrimaryGreen.copy(alpha = 0.8f) // Hơi mờ
                )
            }
        }

        // 2. Danh sách các nhóm (Ví thường, Tiết kiệm, Tín dụng)
//        items(groupedWallets) { group ->
//            // Tiêu đề nhóm (Ví thường / Ví tiết kiệm / Ví tín dụng)
//            Text(
//                text = group.type,
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
//            )
//
//            // Số dư nhóm (Đã sửa để hiển thị như trong ảnh)
//            Text(
//                text = "Số dư: ${group.totalBalance.formatCurrency()}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // LazyColumn lồng nhau cho các ví trong nhóm
//            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                group.wallets.forEach { wallet ->
//                    WalletItemCard(
//                        wallet = wallet,
//                        onClick = { onWalletClicked(wallet.id) }
//                    )
//                }
//            }
//        }

        // 2. Danh sách các NHÓM VÍ
        items(groupedWallets) { group ->
            WalletGroupCard(
                group = group,
                isBalanceVisible = isBalanceVisible,
                onToggleVisibility = onToggleVisibility,
                onClick = {
                    // TODO: Quyết định xem nhấn vào "Chi tiết"
                    // là đi đâu (ví dụ: màn hình mới)
                    // Tạm thời truyền tên Nhóm đi
                    onWalletGroupClicked(group.type)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


/**
 * Thẻ hiển thị Tổng Số Dư (Giữ nguyên)
 */
@Composable
fun TotalBalanceCard(totalBalance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Tổng số dư",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = totalBalance.formatCurrency(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}

/**
 * Thẻ hiển thị cho TỪNG NHÓM VÍ
 */
@Composable
fun WalletGroupCard(
    group: WalletGroupSummary,
    isBalanceVisible: Boolean,
    onToggleVisibility: () -> Unit,
    onClick: () -> Unit
) {
    // SỬA LẠI: DÙNG CARD
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Thêm bóng
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)), // Thêm viền
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Màu nền
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp) // Thêm padding cho Card
        ) {
            // Dòng 1: Tên nhóm (Ví thường)
            Text(
                text = group.type,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp)) // Tăng khoảng cách

            // --- KHỐI CHUNG: SỐ DƯ VÀ ĐÃ CHI  ---

            // Dòng 2: Số dư
            Text(
                text = "Số dư",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isBalanceVisible) group.totalBalance.formatCurrency() else "∗∗∗∗∗∗∗",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (group.type == "Ví tín dụng") Color.Red else MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Ẩn/Hiện số dư",
                        tint = Color.Gray
                    )
                }
            }

            // Dòng 3: Đã chi
            Text(
                text = "Đã chi",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isBalanceVisible) group.totalSpent.formatCurrency() else "∗∗∗∗∗∗∗",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    Icons.Default.History, // Icon Đồng hồ
                    contentDescription = "Đã chi",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }

            // Dòng 4: Dấu gạch ngang
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))
            // --- HẾT KHỐI CHUNG ---

            // Dòng 5: Nút "Chi tiết ví"
            TextButton(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                Text("Chi tiết ví", color = PrimaryGreen, fontWeight = FontWeight.Bold)
            }


        }
    }
}

/**
 * Thẻ hiển thị cho từng cái ví (Thiết kế chi tiết)
 */
@Composable
fun WalletItemCard(wallet: Wallet, onClick: () -> Unit) {
    val isCredit = wallet.type == "Credit"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Màu nền thẻ
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon (Lấy icon theo loại ví)
            Icon(
                imageVector = when (wallet.type) {
                    "Saving" -> Icons.Default.Savings
                    "Credit" -> Icons.Default.CreditCard
                    else -> Icons.Default.Wallet
                },
                contentDescription = wallet.name,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PrimaryGreen.copy(alpha = 0.1f)) // Nền icon mờ
                    .padding(8.dp),
                tint = PrimaryGreen // Màu icon
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Tên và Loại ví
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wallet.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = when (wallet.type) {
                        "Saving" -> "Ví tiết kiệm"
                        "Credit" -> "Ví tín dụng"
                        else -> "Ví thường"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Số dư (Đã sửa để căn lề phải)
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = wallet.balance.formatCurrency(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    // Màu đỏ cho dư nợ
                    color = if (isCredit) Color.Red else MaterialTheme.colorScheme.onSurface
                )
                if (isCredit) {
                    Text(
                        text = "Dư nợ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

/**
 * Giao diện khi chưa có ví nào (Màn 1)
 */
@Composable
fun WalletEmptyState(
    onAddWalletClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // thêm Icon heo đất
        Image(
            painter = painterResource(id = R.drawable.ic_piggy_bank),
            contentDescription = "Bạn chưa có ví",
            modifier = Modifier.size(150.dp)
            // Bỏ ColorFilter để giữ màu hồng của icon heo đất
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bạn chưa có ví nào!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Hãy tạo ví ngay để bắt đầu theo dõi thu nhập và chi tiêu",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Nút "+ Thêm ví"
        Button(
            onClick = onAddWalletClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen // Màu xanh
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Thêm ví",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun WalletGroupCardPreview() {
    // Thêm Preview này để xem Card
    WalletGroupCard(
        group = WalletGroupSummary("Ví thường", 1500000.0, 500000.0, emptyList()),
        isBalanceVisible = true,
        onToggleVisibility = {},
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WalletEmptyStatePreview() {
    WalletEmptyState(onAddWalletClicked = {})
}
* */

// home model
/*
package com.example.expensemanagement.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanagement.data.model.Wallet
import com.example.expensemanagement.data.repository.WalletRepository
import com.google.protobuf.TypeOrBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.expensemanagement.data.model.Transaction
import com.example.expensemanagement.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.combine


// Lớp mới để chứa dữ liệu đã nhóm (Grouped Wallet Data)
data class WalletGroupSummary(
    val type: String, // "Ví thường", "Ví tiết kiệm", "Ví tín dụng"
    val totalBalance: Double, // Tổng số dư trong tất cả các ví trong nhóm
    val totalSpent: Double, // Tổng số tiền đã chi tiêu trong tất cả các ví trong nhóm
    // val wallets: List<Wallet> // Danh sách các ví trong nhóm
    val walletIds: List<String> // Danh sách các ID của các ví trong nhóm
)

// Trạng thái UI cho HomeScreen
sealed class HomeUiState {
    object Loading : HomeUiState()

    // Trả về danh sách ví đã nhóm và tổng số dư TẤT CẢ
    data class Success(
//        val wallets: List<Wallet>
//        val groupedWallets: List<WalletGroup>,
        val walletGroups: List<WalletGroupSummary>,
//        val grandTotalBalance: Double, // Tổng số dư toàn bộ
        val isEmpty: Boolean // Cờ báo danh sách có rỗng hay không
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository, // Hilt tiêm Repository
    private val transactionRepository: TransactionRepository // Hilt tiêm "Kho Giao dịch"
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Ngay khi ViewModel được tạo, bắt đầu lấy danh sách ví
//        fetchWallets()
        fetchWalletsAndTransactions()
    }

    /**
     * HÀM: Dùng flow.combine()
     * Lấy CẢ HAI (Ví và Giao dịch) cùng một lúc.
     */
    private fun fetchWalletsAndTransactions() {
        viewModelScope.launch {
            // Lấy flow1 (Danh sách Ví)
            val walletsFlow = walletRepository.getWallets()
            // Lấy flow2 (Danh sách Giao dịch)
            val transactionsFlow = transactionRepository.getTransactions()

            // Kết hợp (combine) 2 flow này lại
            walletsFlow.combine(transactionsFlow) { walletList, transactionList ->
                // (Hàm này sẽ tự động chạy lại mỗi khi Ví HOẶC Giao dịch thay đổi)

                if (walletList.isEmpty()) {
                    HomeUiState.Success(emptyList(), true)
                } else {
                    // --- LOGIC TÍNH TOÁN "ĐÃ CHI" THẬT ---
                    val groupedData = groupWalletsAndCalculate(walletList, transactionList)
                    HomeUiState.Success(groupedData, false)
                }
            }
                .catch { exception ->
                    _uiState.value = HomeUiState.Error(exception.message ?: "Lỗi không xác định")
                }
                .collect { state ->
                    // Cập nhật UI
                    _uiState.value = state
                }
        }
    }


    /**
     * HÀM: Thêm (transactionList)
     * Hàm nhóm ví và TÍNH TOÁN "ĐÃ CHI" THẬT
     */
    private fun groupWalletsAndCalculate(
        wallets: List<Wallet>,
        transactions: List<Transaction> // DỮ LIỆU THẬT
    ): List<WalletGroupSummary> {

        val groups = wallets.groupBy { it.type }
        val result = mutableListOf<WalletGroupSummary>()
        val typeOrder = listOf("Normal", "Saving", "Credit")

        for (type in typeOrder) {
            val list = groups[type] ?: continue

            val displayName = when(type) {
                "Normal" -> "Ví thường"
                "Saving" -> "Ví tiết kiệm"
                "Credit" -> "Ví tín dụng"
                else -> type
            }

            val totalGroupBalance = list.sumOf { it.balance }
            val ids = list.map { it.id }

            // --- TÍNH TOÁN "ĐÃ CHI" THẬT (BỎ MOCK DATA) ---
            val realTotalSpent = transactions
                .filter { trans ->
                    // Lọc: 1. Giao dịch phải nằm trong nhóm ví này
                    //      2. Giao dịch phải là "Chi tiêu"
                    trans.walletId in ids && trans.type == "Expense"

                    // TODO: Bạn cần thêm logic lọc theo "Tháng Này" (Date) ở đây
                    // (Tạm thời đang tính tổng Đã Chi CỦA TẤT CẢ THỜI GIAN)
                }
                .sumOf { it.amount }

            result.add(
                WalletGroupSummary(
                    type = displayName,
                    totalBalance = totalGroupBalance,
                    totalSpent = realTotalSpent, // Dùng DỮ LIỆU THẬT
                    walletIds = ids
                )
            )
        }
        return result
    }
}
* */

// gốc
/*
package com.example.expensemanagement.viewmodel

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK // Thêm cái này cho chắc
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanagement.data.repository.Security.SecurityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SecurityUiState(
    val isAppLockEnabled: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val hasPinSetup: Boolean = false,
    val canUseBiometrics: Boolean = false
)

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val securityRepository: SecurityRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // StateFlow kết hợp từ các nguồn dữ liệu
    val uiState: StateFlow<SecurityUiState> = combine(
        securityRepository.isAppLockEnabled,
        securityRepository.isBiometricEnabled,
        securityRepository.hasPinSetup()
    ) { appLock, biometric, hasPin ->
        SecurityUiState(
            isAppLockEnabled = appLock,
            isBiometricEnabled = biometric,
            hasPinSetup = hasPin,
            // Kiểm tra biometric mỗi khi state thay đổi (hoặc lấy giá trị tĩnh)
            canUseBiometrics = checkBiometricSupport()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SecurityUiState()
    )

    // Bật/tắt Khóa ứng dụng
    fun onAppLockToggle(isEnabled: Boolean) {
        viewModelScope.launch {
            securityRepository.setAppLockEnabled(isEnabled)
            if (!isEnabled) {
                securityRepository.setBiometricEnabled(false)
            }
        }
    }

    // Bật/tắt Sinh trắc học
    fun onBiometricToggle(isEnabled: Boolean) {
        viewModelScope.launch {
            securityRepository.setBiometricEnabled(isEnabled)
        }
    }

    private fun checkBiometricSupport(): Boolean {
        return try {
            val biometricManager = BiometricManager.from(context)
            // Cho phép cả STRONG và WEAK (ví dụ Face Unlock trên một số máy Android)
            val authenticators = BIOMETRIC_STRONG or BIOMETRIC_WEAK

            when (biometricManager.canAuthenticate(authenticators)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                else -> false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}package com.example.expensemanagement.viewmodel

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK // Thêm cái này cho chắc
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanagement.data.repository.Security.SecurityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SecurityUiState(
    val isAppLockEnabled: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val hasPinSetup: Boolean = false,
    val canUseBiometrics: Boolean = false
)

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val securityRepository: SecurityRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // StateFlow kết hợp từ các nguồn dữ liệu
    val uiState: StateFlow<SecurityUiState> = combine(
        securityRepository.isAppLockEnabled,
        securityRepository.isBiometricEnabled,
        securityRepository.hasPinSetup()
    ) { appLock, biometric, hasPin ->
        SecurityUiState(
            isAppLockEnabled = appLock,
            isBiometricEnabled = biometric,
            hasPinSetup = hasPin,
            // Kiểm tra biometric mỗi khi state thay đổi (hoặc lấy giá trị tĩnh)
            canUseBiometrics = checkBiometricSupport()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SecurityUiState()
    )

    // Bật/tắt Khóa ứng dụng
    fun onAppLockToggle(isEnabled: Boolean) {
        viewModelScope.launch {
            securityRepository.setAppLockEnabled(isEnabled)
            if (!isEnabled) {
                securityRepository.setBiometricEnabled(false)
            }
        }
    }

    // Bật/tắt Sinh trắc học
    fun onBiometricToggle(isEnabled: Boolean) {
        viewModelScope.launch {
            securityRepository.setBiometricEnabled(isEnabled)
        }
    }

    private fun checkBiometricSupport(): Boolean {
        return try {
            val biometricManager = BiometricManager.from(context)
            // Cho phép cả STRONG và WEAK (ví dụ Face Unlock trên một số máy Android)
            val authenticators = BIOMETRIC_STRONG or BIOMETRIC_WEAK

            when (biometricManager.canAuthenticate(authenticators)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                else -> false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
 */