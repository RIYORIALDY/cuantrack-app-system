package com.rork.cuantrackandroid.models

import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.util.UUID

enum class TransactionType(val label: String) {
    EXPENSE("Pengeluaran"),
    INCOME("Pemasukan"),
    TRANSFER("Transfer");
}

enum class TransactionCategory(val label: String, val iconName: String) {
    FOOD("Makanan", "restaurant"),
    TRANSPORT("Transport", "directions_car"),
    SHOPPING("Belanja", "shopping_bag"),
    BILLS("Tagihan", "description"),
    ENTERTAINMENT("Hiburan", "tv"),
    OTHER("Lainnya", "more_horiz");

    val color: Color
        get() = when (this) {
            FOOD -> Color(0xFFF58738)
            TRANSPORT -> Color(0xFF3B99D9)
            SHOPPING -> Color(0xFFAB54D9)
            BILLS -> Color(0xFFDE5454)
            ENTERTAINMENT -> Color(0xFFF0338D)
            OTHER -> Color(0xFF64748B)
        }
}

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val type: TransactionType = TransactionType.EXPENSE,
    val category: TransactionCategory = TransactionCategory.OTHER,
    val amount: Double = 0.0,
    val accountName: String = "",
    val date: Instant = Instant.now(),
    val note: String = "",
    val isRecurring: Boolean = false
) {
    val formattedAmount: String
        get() {
            val nf = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
            nf.maximumFractionDigits = 0
            return "Rp ${nf.format(amount)}"
        }

    val amountPrefix: String
        get() = if (type == TransactionType.INCOME) "+ " else "- "

    val amountColor: Color
        get() = if (type == TransactionType.INCOME) Color(0xFF16A34A) else Color(0xFFDC2626)
}
