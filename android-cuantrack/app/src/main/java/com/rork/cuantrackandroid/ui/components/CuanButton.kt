package com.rork.cuantrackandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.ui.theme.CuanColors

enum class CuanButtonStyle {
    PRIMARY, SECONDARY, DISABLED
}

@Composable
fun CuanButton(
    title: String,
    style: CuanButtonStyle = CuanButtonStyle.PRIMARY,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (bgColor, fgColor) = when (style) {
        CuanButtonStyle.PRIMARY -> CuanColors.Primary to Color.White
        CuanButtonStyle.SECONDARY -> CuanColors.Primary.copy(alpha = 0.08f) to CuanColors.Primary
        CuanButtonStyle.DISABLED -> CuanColors.Border to CuanColors.SecondaryText
    }

    Button(
        onClick = onClick,
        enabled = style != CuanButtonStyle.DISABLED,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = fgColor,
            disabledContainerColor = CuanColors.Border,
            disabledContentColor = CuanColors.SecondaryText
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
