package com.rork.cuantrackandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rork.cuantrackandroid.models.Transaction
import com.rork.cuantrackandroid.models.TransactionType
import com.rork.cuantrackandroid.ui.theme.CuanColors

@Composable
fun TransactionRow(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(transaction.category.color.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = transaction.category.iconVector(),
                contentDescription = null,
                tint = transaction.category.color,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.size(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                transaction.note.ifEmpty { transaction.category.label },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = CuanColors.Text
            )
            Spacer(Modifier.size(4.dp))
            Text(
                transaction.category.label,
                fontSize = 12.sp,
                color = CuanColors.SecondaryText
            )
        }

        Text(
            "${transaction.amountPrefix}${transaction.formattedAmount}",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = transaction.amountColor
        )
    }
}
