package com.rork.cuantrackandroid.models

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class BudgetCategory(
    val id: UUID = UUID.randomUUID(),
    val category: TransactionCategory = TransactionCategory.OTHER,
    val monthlyLimit: Double = 0.0,
    val spent: Double = 0.0,
    val month: Int = 0,
    val year: Int = 0
) {
    val formattedLimit: String
        get() {
            val nf = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
            nf.maximumFractionDigits = 0
            return "Rp ${nf.format(monthlyLimit)}"
        }

    val percentSpent: Float
        get() = if (monthlyLimit > 0) (spent / monthlyLimit).toFloat().coerceIn(0f, 1f) else 0f

    val health: BudgetHealth
        get() = BudgetHealth.fromPercent(percentSpent.toDouble())

    val healthLabel: String
        get() = health.label

    val progressColor: Color
        get() = when (health) {
            BudgetHealth.SAFE -> category.color
            BudgetHealth.WARNING -> Color(0xFFF59E0B)
            BudgetHealth.CRITICAL -> Color(0xFFDC2626)
        }
}

enum class BudgetHealth(val label: String) {
    SAFE("Aman"),
    WARNING("Hampir habis"),
    CRITICAL("Melebihi");

    companion object {
        fun fromPercent(percent: Double): BudgetHealth = when {
            percent >= 1.0 -> CRITICAL
            percent >= 0.75 -> WARNING
            else -> SAFE
        }
    }
}
