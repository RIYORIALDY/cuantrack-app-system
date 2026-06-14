package com.rork.cuantrackandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rork.cuantrackandroid.data.AppViewModel
import com.rork.cuantrackandroid.ui.navigation.AppNavigation
import com.rork.cuantrackandroid.ui.theme.CuanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuanTheme {
                val appViewModel: AppViewModel = viewModel()
                AppNavigation(viewModel = appViewModel)
            }
        }
    }
}
