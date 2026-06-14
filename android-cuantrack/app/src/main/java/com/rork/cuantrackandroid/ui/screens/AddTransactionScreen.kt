package com.rork.cuantrackandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.data.AppViewModel
import com.rork.cuantrackandroid.models.TransactionCategory
import com.rork.cuantrackandroid.models.TransactionType
import com.rork.cuantrackandroid.ui.components.CategoryChip
import com.rork.cuantrackandroid.ui.components.CuanButton
import com.rork.cuantrackandroid.ui.components.CuanButtonStyle
import com.rork.cuantrackandroid.ui.components.ToastView
import com.rork.cuantrackandroid.ui.theme.CuanColors
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTransactionScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedCategory by remember { mutableStateOf(TransactionCategory.FOOD) }
    var selectedAccount by remember { mutableStateOf("BCA") }
    var nominalText by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isRecurring by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var accountDropdownExpanded by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale("id", "ID")) }

    val accounts = uiState.accounts.map { it.bankName }
    val canSave = nominalText.toDoubleOrNull()?.let { it > 0 } == true

    Box(modifier = modifier.fillMaxSize().background(CuanColors.Background)) {
        Column {
            TopAppBar(
                title = { Text("Tambah Transaksi", fontSize = 18.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CuanColors.Background)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CuanColors.Surface)
                        .padding(4.dp)
                ) {
                    TransactionType.entries.forEach { type ->
                        val isSelected = selectedType == type
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) segmentColor(type) else Color.Transparent)
                                .let { it }
                                .then(
                                    if (!isSelected) Modifier else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.material3.TextButton(
                                onClick = { selectedType = type }
                            ) {
                                Text(
                                    type.label,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color.White else CuanColors.SecondaryText
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Rekening", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CuanColors.Text)
                Spacer(Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = accountDropdownExpanded,
                    onExpandedChange = { accountDropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedAccount,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountDropdownExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CuanColors.Border,
                            unfocusedBorderColor = CuanColors.Border,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = accountDropdownExpanded,
                        onDismissRequest = { accountDropdownExpanded = false }
                    ) {
                        accounts.forEach { account ->
                            DropdownMenuItem(
                                text = { Text(account) },
                                onClick = {
                                    selectedAccount = account
                                    accountDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Kategori", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CuanColors.Text)
                Spacer(Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransactionCategory.entries.forEach { category ->
                        CategoryChip(
                            category = category,
                            isSelected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Nominal", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CuanColors.Text)
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Rp",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = CuanColors.Text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    OutlinedTextField(
                        value = nominalText,
                        onValueChange = { nominalText = it },
                        placeholder = { Text("0", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = CuanColors.Border) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = CuanColors.Text
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text("Tanggal", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CuanColors.Text)
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                ) {
                    TextButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            selectedDate.format(dateFormatter),
                            fontSize = 16.sp,
                            color = CuanColors.Text
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text("Catatan", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CuanColors.Text)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Tambahkan catatan...", fontSize = 16.sp, color = CuanColors.Border) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CuanColors.Border,
                        unfocusedBorderColor = CuanColors.Border,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Tandai sebagai berulang",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = CuanColors.Text
                        )
                        Text(
                            "Transaksi akan otomatis tercatat setiap bulan",
                            fontSize = 12.sp,
                            color = CuanColors.SecondaryText
                        )
                    }
                    Switch(
                        checked = isRecurring,
                        onCheckedChange = { isRecurring = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CuanColors.Primary)
                    )
                }

                Spacer(Modifier.height(32.dp))

                CuanButton(
                    title = "Simpan",
                    style = if (canSave) CuanButtonStyle.PRIMARY else CuanButtonStyle.DISABLED,
                    onClick = {
                        val amount = nominalText.toDoubleOrNull() ?: return@CuanButton
                        val instant = selectedDate.atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant()
                        viewModel.addTransaction(
                            type = selectedType,
                            category = selectedCategory,
                            amount = amount,
                            accountName = selectedAccount,
                            date = instant,
                            note = note.ifEmpty { selectedCategory.label },
                            isRecurring = isRecurring
                        )
                    }
                )

                Spacer(Modifier.height(32.dp))
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.of("Asia/Jakarta"))
                                .toLocalDate()
                        }
                        showDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (uiState.showToast) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
            ) {
                ToastView(message = uiState.toastMessage)
            }
        }
    }
}

private fun segmentColor(type: TransactionType): Color = when (type) {
    TransactionType.EXPENSE -> CuanColors.Expense
    TransactionType.INCOME -> CuanColors.Income
    TransactionType.TRANSFER -> CuanColors.Primary
}
