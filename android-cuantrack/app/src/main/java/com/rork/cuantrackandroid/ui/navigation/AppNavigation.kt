package com.rork.cuantrackandroid.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.data.AppViewModel
import com.rork.cuantrackandroid.ui.screens.AddTransactionScreen
import com.rork.cuantrackandroid.ui.screens.HomeScreen
import com.rork.cuantrackandroid.ui.screens.LoginScreen
import com.rork.cuantrackandroid.ui.screens.ProfileScreen
import com.rork.cuantrackandroid.ui.screens.TransactionListScreen
import com.rork.cuantrackandroid.ui.theme.CuanColors
import com.rork.cuantrackandroid.ui.components.ToastView

enum class AppTab(val label: String, val icon: ImageVector) {
    HOME("Beranda", Icons.Filled.Home),
    TRANSACTIONS("Transaksi", Icons.Filled.ListAlt),
    BUDGET("Budget", Icons.Filled.PieChart),
    PROFILE("Profil", Icons.Filled.AccountCircle)
}

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isAuthenticated) {
        LoginScreen(onLogin = { viewModel.login("", "") })
        return
    }

    var selectedTab by remember { mutableStateOf(AppTab.HOME) }
    var showAddTransaction by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(CuanColors.Border.copy(alpha = 0.3f))
                    )
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 0.dp
                    ) {
                        AppTab.entries.forEach { tab ->
                            val isSelected = selectedTab == tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { selectedTab = tab },
                                icon = {
                                    Icon(
                                        tab.icon,
                                        contentDescription = tab.label,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        tab.label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CuanColors.Primary,
                                    selectedTextColor = CuanColors.Primary,
                                    unselectedIconColor = CuanColors.SecondaryText,
                                    unselectedTextColor = CuanColors.SecondaryText,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    AppTab.HOME -> HomeScreen(
                        viewModel = viewModel,
                        onAddTransaction = { showAddTransaction = true },
                        onNavigateToTransactions = { selectedTab = AppTab.TRANSACTIONS },
                        onNavigateToProfile = { selectedTab = AppTab.PROFILE }
                    )
                    AppTab.TRANSACTIONS -> TransactionListScreen(viewModel = viewModel)
                    AppTab.BUDGET -> BudgetPlaceholder()
                    AppTab.PROFILE -> ProfileScreen(
                        viewModel = viewModel,
                        onLogout = { viewModel.logout() }
                    )
                }
            }
        }

        if (showAddTransaction) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                AddTransactionScreen(
                    viewModel = viewModel,
                    onBack = { showAddTransaction = false }
                )
            }
        }

        if (uiState.showToast) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp)
            ) {
                ToastView(message = uiState.toastMessage)
            }
        }
    }
}

@Composable
private fun BudgetPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CuanColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Filled.PieChart,
                contentDescription = null,
                tint = CuanColors.Primary.copy(alpha = 0.3f),
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Fitur Budget",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CuanColors.Text
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Akan segera hadir",
                fontSize = 14.sp,
                color = CuanColors.SecondaryText
            )
        }
    }
}
