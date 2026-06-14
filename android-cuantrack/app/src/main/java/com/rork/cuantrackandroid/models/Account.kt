package com.rork.cuantrackandroid.models

import java.util.UUID

data class Account(
    val id: UUID = UUID.randomUUID(),
    val bankName: String = "",
    val accountHolder: String = "",
    val balance: Double = 0.0,
    val accountNumber: String = ""
) {
    val formattedBalance: String
        get() {
            val nf = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
            nf.maximumFractionDigits = 0
            return "Rp ${nf.format(balance)}"
        }
}
