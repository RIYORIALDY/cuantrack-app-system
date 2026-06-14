package com.rork.cuantrackandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.data.AppViewModel
import com.rork.cuantrackandroid.models.BudgetHealth
import com.rork.cuantrackandroid.models.TransactionCategory
import com.rork.cuantrackandroid.ui.theme.CuanColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var editingCategory by remember { mutableStateOf<TransactionCategory?>(null) }
    var editAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CuanColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MonthlySummaryCard(viewModel)
        CategoryBudgetsSection(viewModel) { category ->
            val budget = viewModel.budgetForCategory(category)
            editAmount = if (budget.monthlyLimit > 0) {
                budget.monthlyLimit.toLong().toString()
            } else ""
            editingCategory = category
        }
    }

    editingCategory?.let { category ->
        ModalBottomSheet(
            onDismissRequest = { editingCategory = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            containerColor = Color.White
        ) {
            BudgetEditSheet(
                category = category,
                currentAmount = editAmount,
                onAmountChange = { editAmount = it },
                onSave = {
                    val limit = editAmount.toDoubleOrNull() ?: 0.0
                    viewModel.setBudget(category, limit)
                    editingCategory = null
                },
                onCancel = { editingCategory = null }
            )
        }
    }
}

@Composable
private fun MonthlySummaryCard(viewModel: AppViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Budget Bulan Ini",
                        fontSize = 14.sp,
                        color = CuanColors.SecondaryText
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        formatRupiah(viewModel.totalBudgeted),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = CuanColors.Text
                    )
                }
                BudgetRing(
                    progress = viewModel.budgetPercentUsed,
                    size = 56,
                    color = getRingColor(viewModel.budgetPercentUsed)
                )
            }

            Spacer(Modifier.height(16.dp))
            Divider(color = CuanColors.Border)
            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                StatItem(
                    title = "Terpakai",
                    value = formatRupiah(viewModel.totalSpentThisMonth),
                    color = CuanColors.Expense,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(CuanColors.Border)
                )
                StatItem(
                    title = "Tersisa",
                    value = formatRupiah(viewModel.remainingBudget),
                    color = CuanColors.Income,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BudgetRing(progress: Float, size: Int, color: Color) {
    Box(
        modifier = Modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 5.dp.toPx()
            drawCircle(
                color = CuanColors.Border,
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
            )
        }
        Text(
            "${(progress * 100).toInt()}%",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun StatItem(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            title,
            fontSize = 12.sp,
            color = CuanColors.SecondaryText
        )
    }
}

@Composable
private fun CategoryBudgetsSection(
    viewModel: AppViewModel,
    onEditClick: (TransactionCategory) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Budget per Kategori",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CuanColors.Text
            )
        }

        Spacer(Modifier.height(12.dp))

        val budgetsWithSpent = viewModel.budgetsWithSpent()

        TransactionCategory.entries.forEach { category ->
            val budget = budgetsWithSpent.find { it.category == category }
                ?: return@forEach
            BudgetCategoryCard(
                category = category,
                budget = budget,
                onClick = { onEditClick(category) }
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun BudgetCategoryCard(
    category: TransactionCategory,
    budget: com.rork.cuantrackandroid.models.BudgetCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(category.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (category) {
                            TransactionCategory.FOOD -> Icons.Filled.ChevronRight
                            TransactionCategory.TRANSPORT -> Icons.Filled.ChevronRight
                            TransactionCategory.SHOPPING -> Icons.Filled.ChevronRight
                            TransactionCategory.BILLS -> Icons.Filled.ChevronRight
                            TransactionCategory.ENTERTAINMENT -> Icons.Filled.ChevronRight
                            TransactionCategory.OTHER -> Icons.Filled.ChevronRight
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        category.label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = CuanColors.Text
                    )
                    Text(
                        if (budget.monthlyLimit > 0)
                            "${formatRupiah(budget.spent)} / ${budget.formattedLimit}"
                        else "Belum diatur",
                        fontSize = 12.sp,
                        color = CuanColors.SecondaryText
                    )
                }

                if (budget.monthlyLimit > 0) {
                    HealthBadge(budget.health)
                }

                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFFC7CAD1),
                    modifier = Modifier.size(16.dp)
                )
            }

            if (budget.monthlyLimit > 0) {
                Spacer(Modifier.height(12.dp))
                BudgetProgressBar(
                    progress = budget.percentSpent,
                    color = budget.progressColor
                )
            }
        }
    }
}

@Composable
private fun BudgetProgressBar(progress: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(CuanColors.Border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
    }
}

@Composable
private fun HealthBadge(health: BudgetHealth) {
    val (bgColor, textColor) = when (health) {
        BudgetHealth.SAFE -> CuanColors.Income.copy(alpha = 0.12f) to CuanColors.Income
        BudgetHealth.WARNING -> CuanColors.Warning.copy(alpha = 0.12f) to CuanColors.Warning
        BudgetHealth.CRITICAL -> CuanColors.Expense.copy(alpha = 0.12f) to CuanColors.Expense
    }

    Text(
        health.label,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = textColor,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
private fun BudgetEditSheet(
    category: TransactionCategory,
    currentAmount: String,
    onAmountChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(category.color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (category) {
                    TransactionCategory.FOOD -> Icons.Filled.ChevronRight
                    TransactionCategory.TRANSPORT -> Icons.Filled.ChevronRight
                    TransactionCategory.SHOPPING -> Icons.Filled.ChevronRight
                    TransactionCategory.BILLS -> Icons.Filled.ChevronRight
                    TransactionCategory.ENTERTAINMENT -> Icons.Filled.ChevronRight
                    TransactionCategory.OTHER -> Icons.Filled.ChevronRight
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Budget ${category.label}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = CuanColors.Text
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "Batas Bulanan",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = CuanColors.SecondaryText
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = currentAmount,
            onValueChange = { text ->
                if (text.all { it.isDigit() }) onAmountChange(text)
            },
            modifier = Modifier.fillMaxWidth(),
            prefix = {
                Text(
                    "Rp ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CuanColors.Text
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CuanColors.Text
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CuanColors.Background,
                unfocusedContainerColor = CuanColors.Background,
                focusedBorderColor = CuanColors.Border,
                unfocusedBorderColor = CuanColors.Border
            ),
            singleLine = true
        )

        Spacer(Modifier.height(28.dp))

        androidx.compose.material3.Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = CuanColors.Primary
            )
        ) {
            Text(
                "Simpan Budget",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Batal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = CuanColors.SecondaryText
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

private fun getRingColor(percent: Float): Color = when {
    percent >= 1f -> CuanColors.Expense
    percent >= 0.75f -> CuanColors.Warning
    else -> CuanColors.Primary
}

private fun formatRupiah(amount: Double): String {
    val nf = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
    nf.maximumFractionDigits = 0
    return "Rp ${nf.format(amount)}"
}
