package com.rork.cuantrackandroid.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rork.cuantrackandroid.models.Account
import com.rork.cuantrackandroid.models.Transaction
import com.rork.cuantrackandroid.models.TransactionCategory
import com.rork.cuantrackandroid.models.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID

data class AppUiState(
    val isAuthenticated: Boolean = false,
    val accounts: List<Account> = listOf(),
    val transactions: List<Transaction> = listOf(),
    val showToast: Boolean = false,
    val toastMessage: String = ""
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AppUiState(
            accounts = defaultAccounts(),
            transactions = defaultTransactions()
        )
    )
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(800)
            _uiState.update { it.copy(isAuthenticated = true) }
        }
    }

    fun logout() {
        _uiState.update { it.copy(isAuthenticated = false) }
    }

    fun addTransaction(
        type: TransactionType,
        category: TransactionCategory,
        amount: Double,
        accountName: String,
        date: Instant,
        note: String,
        isRecurring: Boolean
    ) {
        val transaction = Transaction(
            id = UUID.randomUUID(),
            type = type,
            category = category,
            amount = amount,
            accountName = accountName,
            date = date,
            note = note,
            isRecurring = isRecurring
        )
        _uiState.update { state ->
            state.copy(
                transactions = listOf(transaction) + state.transactions,
                accounts = state.accounts.map { account ->
                    if (account.bankName == accountName) {
                        val newBalance = when (type) {
                            TransactionType.INCOME -> account.balance + amount
                            TransactionType.EXPENSE -> account.balance - amount
                            TransactionType.TRANSFER -> account.balance
                        }
                        account.copy(balance = newBalance)
                    } else account
                }
            )
        }
        showToastMessage("Transaksi tersimpan")
    }

    fun deleteTransaction(transactionId: UUID) {
        _uiState.update { state ->
            val tx = state.transactions.find { it.id == transactionId } ?: return@update state
            state.copy(
                transactions = state.transactions.filter { it.id != transactionId },
                accounts = state.accounts.map { account ->
                    if (account.bankName == tx.accountName) {
                        val revertedBalance = when (tx.type) {
                            TransactionType.INCOME -> account.balance - tx.amount
                            TransactionType.EXPENSE -> account.balance + tx.amount
                            TransactionType.TRANSFER -> account.balance
                        }
                        account.copy(balance = revertedBalance)
                    } else account
                }
            )
        }
    }

    fun showToastMessage(message: String) {
        _uiState.update { it.copy(showToast = true, toastMessage = message) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            _uiState.update { it.copy(showToast = false) }
        }
    }

    val totalBalance: Double
        get() = _uiState.value.accounts.sumOf { it.balance }

    val monthlyIncome: Double
        get() = _uiState.value.transactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }

    val monthlyExpense: Double
        get() = _uiState.value.transactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }

    val recentTransactions: List<Transaction>
        get() = _uiState.value.transactions.take(5)

    val transactionsGroupedByDate: Map<String, List<Transaction>>
        get() {
            val formatter = java.time.format.DateTimeFormatter
                .ofPattern("dd MMMM yyyy")
                .withLocale(java.util.Locale("id", "ID"))
            val today = java.time.LocalDate.now()
            val yesterday = today.minusDays(1)

            return _uiState.value.transactions.groupBy { tx ->
                val localDate = java.time.LocalDate.ofInstant(tx.date, java.time.ZoneId.of("Asia/Jakarta"))
                when {
                    localDate == today -> "Hari ini"
                    localDate == yesterday -> "Kemarin"
                    else -> localDate.format(formatter)
                }
            }
        }

    companion object {
        fun defaultAccounts(): List<Account> = listOf(
            Account(
                UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890"),
                "BCA", "Riyo", 3_250_000.0, "•••• 1234"
            ),
            Account(
                UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f12345678901"),
                "BNI", "Riyo", 1_100_000.0, "•••• 5678"
            ),
            Account(
                UUID.fromString("c3d4e5f6-a7b8-9012-cdef-123456789012"),
                "Mandiri", "Riyo", 8_100_000.0, "•••• 9012"
            ),
        )

        fun defaultTransactions(): List<Transaction> = listOf(
            Transaction(
                UUID.fromString("d4e5f6a7-b8c9-0123-defa-bcdef1234567"),
                TransactionType.EXPENSE, TransactionCategory.FOOD,
                45_000.0, "BCA", Instant.parse("2026-06-14T05:30:00Z"),
                "Makan siang"
            ),
            Transaction(
                UUID.fromString("e5f6a7b8-c9d0-1234-efab-cdef12345678"),
                TransactionType.EXPENSE, TransactionCategory.TRANSPORT,
                25_000.0, "BCA", Instant.parse("2026-06-14T02:00:00Z"),
                "Gojek"
            ),
            Transaction(
                UUID.fromString("f6a7b8c9-d0e1-2345-fabc-def123456789"),
                TransactionType.INCOME, TransactionCategory.OTHER,
                5_000_000.0, "Mandiri", Instant.parse("2026-06-13T00:00:00Z"),
                "Gaji bulanan"
            ),
            Transaction(
                UUID.fromString("a7b8c9d0-e1f2-3456-abcd-ef123456789a"),
                TransactionType.EXPENSE, TransactionCategory.BILLS,
                350_000.0, "BNI", Instant.parse("2026-06-13T08:00:00Z"),
                "Listrik"
            ),
            Transaction(
                UUID.fromString("b8c9d0e1-f2a3-4567-bcde-f123456789ab"),
                TransactionType.EXPENSE, TransactionCategory.SHOPPING,
                100_000.0, "Mandiri", Instant.parse("2026-06-12T10:30:00Z"),
                "Top up e-wallet"
            ),
        )
    }
}
