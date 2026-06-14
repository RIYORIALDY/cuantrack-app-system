package com.rork.cuantrackandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.data.AppViewModel
import com.rork.cuantrackandroid.ui.components.AccountCard
import com.rork.cuantrackandroid.ui.components.SectionHeader
import com.rork.cuantrackandroid.ui.components.SummaryCard
import com.rork.cuantrackandroid.ui.components.TransactionRow
import com.rork.cuantrackandroid.ui.theme.CuanColors

@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onAddTransaction: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize().background(CuanColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Halo, Riyo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = CuanColors.Text
                    )
                    Text(
                        "Ringkasan hari ini",
                        fontSize = 14.sp,
                        color = CuanColors.SecondaryText
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(CuanColors.Surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Notifikasi",
                        tint = CuanColors.SecondaryText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            SummaryCard(
                totalBalance = viewModel.totalBalance,
                monthlyExpense = viewModel.monthlyExpense,
                monthlyIncome = viewModel.monthlyIncome
            )

            Spacer(Modifier.height(24.dp))

            SectionHeader(title = "Rekening", actionLabel = "Lihat semua", onAction = {})

            Spacer(Modifier.height(12.dp))

            uiState.accounts.forEach { account ->
                AccountCard(
                    bankName = account.bankName,
                    balance = account.formattedBalance,
                    accentColor = bankColor(account.bankName)
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(12.dp))

            SectionHeader(
                title = "Transaksi Terbaru",
                actionLabel = "Lihat semua",
                onAction = onNavigateToTransactions
            )

            Spacer(Modifier.height(8.dp))

            viewModel.recentTransactions.forEach { tx ->
                TransactionRow(transaction = tx)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(100.dp))
        }

        FloatingActionButton(
            onClick = onAddTransaction,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            containerColor = CuanColors.Primary,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Tambah transaksi")
        }
    }
}

private fun bankColor(name: String): Color = when (name) {
    "BCA" -> CuanColors.BcaBlue
    "BNI" -> CuanColors.BniTeal
    "Mandiri" -> CuanColors.MandiriBlue
    else -> CuanColors.SecondaryText
}
