//
//  TransactionRow.swift
//  CuanTrack
//

import SwiftUI

struct TransactionRow: View {
    let category: TransactionCategory
    let title: String
    let amount: Double
    let type: TransactionType

    private var amountColor: Color {
        type == .income
            ? Color(red: 0.09, green: 0.64, blue: 0.29)
            : Color(red: 0.86, green: 0.15, blue: 0.15)
    }

    private var amountPrefix: String {
        type == .income ? "+ " : "- "
    }

    var body: some View {
        HStack(spacing: 12) {
            RoundedRectangle(cornerRadius: 12)
                .fill(category.color.opacity(0.12))
                .frame(width: 44, height: 44)
                .overlay {
                    Image(systemName: category.icon)
                        .font(.system(size: 18))
                        .foregroundStyle(category.color)
                }

            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                Text(category.rawValue)
                    .font(.system(size: 12))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
            }

            Spacer()

            Text("\(amountPrefix)Rp \(formatAmount(amount))")
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(amountColor)
        }
        .padding(12)
        .background(Color.white)
        .clipShape(.rect(cornerRadius: 14))
    }

    private func formatAmount(_ value: Double) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = "."
        formatter.maximumFractionDigits = 0
        return formatter.string(from: NSNumber(value: value)) ?? "0"
    }
}

#Preview {
    VStack(spacing: 8) {
        TransactionRow(category: .food, title: "Makan siang", amount: 45000, type: .expense)
        TransactionRow(category: .transport, title: "Gojek", amount: 25000, type: .expense)
        TransactionRow(category: .other, title: "Gaji", amount: 5000000, type: .income)
    }
    .padding(24)
    .background(Color(red: 0.97, green: 0.98, blue: 0.99))
}
