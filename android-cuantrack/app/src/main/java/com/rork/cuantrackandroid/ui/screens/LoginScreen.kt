package com.rork.cuantrackandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.ui.components.CuanButton
import com.rork.cuantrackandroid.ui.components.CuanButtonStyle
import com.rork.cuantrackandroid.ui.components.CuanTextField
import com.rork.cuantrackandroid.ui.theme.CuanColors

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val isFormValid = email.trim().isNotEmpty() && password.trim().isNotEmpty()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CuanColors.Background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(80.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(CuanColors.Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.ShowChart,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Row {
                    Text(
                        "Cuan",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = CuanColors.Text
                    )
                    Text(
                        "Track",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light,
                        color = CuanColors.Primary
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

            Text(
                "Selamat datang",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = CuanColors.Text
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Catat pengeluaran dan pantau saldo semua rekening.",
                fontSize = 14.sp,
                color = CuanColors.SecondaryText,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(32.dp))

            CuanTextField(
                placeholder = "Email / No HP",
                value = email,
                onValueChange = { email = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                leadingIcon = Icons.Filled.Email
            )

            Spacer(Modifier.height(12.dp))

            CuanTextField(
                placeholder = "Kata sandi",
                value = password,
                onValueChange = { password = it },
                isSecure = true,
                leadingIcon = Icons.Filled.Lock
            )

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    "Lupa password?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = CuanColors.Primary
                )
            }

            Spacer(Modifier.height(8.dp))

            CuanButton(
                title = if (isLoading) "Memuat..." else "Masuk",
                style = if (isFormValid) CuanButtonStyle.PRIMARY else CuanButtonStyle.DISABLED,
                onClick = {
                    isLoading = true
                    focusManager.clearFocus()
                    onLogin()
                }
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Belum punya akun? ",
                    fontSize = 14.sp,
                    color = CuanColors.SecondaryText
                )
                TextButton(onClick = {}) {
                    Text(
                        "Daftar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CuanColors.Primary
                    )
                }
            }
        }
    }
}
