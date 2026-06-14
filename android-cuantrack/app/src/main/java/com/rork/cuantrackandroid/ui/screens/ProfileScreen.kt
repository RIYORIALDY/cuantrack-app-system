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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.rork.cuantrackandroid.ui.theme.CuanColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDarkMode by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("IDR") }
    var currencyExpanded by remember { mutableStateOf(false) }
    val currencies = listOf("IDR", "USD", "SGD", "MYR")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CuanColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(CuanColors.Primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text("R", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = CuanColors.Primary)
            }
            Spacer(Modifier.height(12.dp))
            Text("Riyo Pratama", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = CuanColors.Text)
            Spacer(Modifier.height(4.dp))
            Text("riyo@email.com", fontSize = 14.sp, color = CuanColors.SecondaryText)
        }

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            SettingsRow(
                icon = Icons.Filled.Palette,
                title = "Tampilan",
                trailing = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            if (isDarkMode) "Gelap" else "Terang",
                            fontSize = 14.sp,
                            color = CuanColors.SecondaryText
                        )
                        Spacer(Modifier.size(8.dp))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CuanColors.Primary)
                        )
                    }
                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 52.dp), color = CuanColors.Border.copy(alpha = 0.5f))

            SettingsRow(
                icon = Icons.Filled.CurrencyExchange,
                title = "Mata uang",
                trailing = {
                    ExposedDropdownMenuBox(
                        expanded = currencyExpanded,
                        onExpandedChange = { currencyExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedCurrency,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = currencyExpanded) },
                            modifier = Modifier
                                .width(100.dp)
                                .height(40.dp)
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = CuanColors.SecondaryText)
                        )
                        ExposedDropdownMenu(
                            expanded = currencyExpanded,
                            onDismissRequest = { currencyExpanded = false }
                        ) {
                            currencies.forEach { currency ->
                                DropdownMenuItem(
                                    text = { Text(currency) },
                                    onClick = {
                                        selectedCurrency = currency
                                        currencyExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 52.dp), color = CuanColors.Border.copy(alpha = 0.5f))

            SettingsRow(
                icon = Icons.Filled.Category,
                title = "Kelola kategori",
                trailing = {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = CuanColors.Border, modifier = Modifier.size(20.dp))
                }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 52.dp), color = CuanColors.Border.copy(alpha = 0.5f))

            SettingsRow(
                icon = Icons.Filled.Lock,
                title = "Keamanan",
                subtitle = "Ubah kata sandi",
                trailing = {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = CuanColors.Border, modifier = Modifier.size(20.dp))
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        TextButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.Logout, null, tint = CuanColors.Expense, modifier = Modifier.size(18.dp))
                Spacer(Modifier.size(8.dp))
                Text("Keluar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = CuanColors.Expense)
            }
        }

        Spacer(Modifier.height(100.dp))
    }
}

@Composable
private fun SettingsRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = CuanColors.Primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, color = CuanColors.Text)
            if (subtitle != null) {
                Text(subtitle, fontSize = 12.sp, color = CuanColors.SecondaryText)
            }
        }
        Spacer(Modifier.size(12.dp))
        trailing()
    }
}
