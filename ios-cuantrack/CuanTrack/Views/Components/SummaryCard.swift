//
//  SummaryCard.swift
//  CuanTrack
//

import SwiftUI

struct SummaryCard: View {
    let totalBalance: Double
    let monthlyExpense: Double
    let monthlyIncome: Double

    var body: some View {
        VStack(spacing: 0) {
            VStack(spacing: 6) {
                Text("Total Saldo")
                    .font(.system(size: 12))
                    .foregroundStyle(.white.opacity(0.8))

                Text("Rp \(formatAmount(totalBalance))")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundStyle(.white)
            }
            .padding(.vertical, 24)
            .frame(maxWidth: .infinity)
            .background(Color(red: 0.15, green: 0.39, blue: 0.92))

            HStack(spacing: 0) {
                VStack(spacing: 4) {
                    Text("Pengeluaran")
                        .font(.system(size: 12))
                        .foregroundStyle(.white.opacity(0.8))

                    Text("Rp \(formatAmount(monthlyExpense))")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundStyle(Color(red: 1, green: 0.60, blue: 0.55))
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 16)

                Rectangle()
                    .fill(.white.opacity(0.2))
                    .frame(width: 1, height: 32)

                VStack(spacing: 4) {
                    Text("Pemasukan")
                        .font(.system(size: 12))
                        .foregroundStyle(.white.opacity(0.8))

                    Text("Rp \(formatAmount(monthlyIncome))")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundStyle(Color(red: 0.40, green: 0.90, blue: 0.55))
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 16)
            }
            .background(Color(red: 0.10, green: 0.31, blue: 0.78))
        }
        .clipShape(.rect(cornerRadius: 18))
        .shadow(color: Color(red: 0.15, green: 0.39, blue: 0.92).opacity(0.25), radius: 12, y: 6)
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
    SummaryCard(totalBalance: 12_450_000, monthlyExpense: 3_200_000, monthlyIncome: 8_500_000)
        .padding(24)
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
}
