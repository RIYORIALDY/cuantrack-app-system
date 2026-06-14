//
//  AccountCard.swift
//  CuanTrack
//

import SwiftUI

struct AccountCard: View {
    let bankName: String
    let balance: String
    let accentColor: Color

    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(accentColor.opacity(0.12))
                .frame(width: 44, height: 44)
                .overlay {
                    Text(String(bankName.prefix(1)))
                        .font(.system(size: 18, weight: .bold))
                        .foregroundStyle(accentColor)
                }

            VStack(alignment: .leading, spacing: 4) {
                Text(bankName)
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                Text(balance)
                    .font(.system(size: 14))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
            }

            Spacer()

            Image(systemName: "chevron.right")
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(Color(red: 0.89, green: 0.91, blue: 0.94))
        }
        .padding(16)
        .background(Color.white)
        .clipShape(.rect(cornerRadius: 14))
        .shadow(color: .black.opacity(0.04), radius: 4, y: 2)
    }
}

#Preview {
    VStack(spacing: 12) {
        AccountCard(bankName: "BCA", balance: "Rp 3.250.000", accentColor: .blue)
        AccountCard(bankName: "BNI", balance: "Rp 1.100.000", accentColor: .teal)
    }
    .padding(24)
    .background(Color(red: 0.97, green: 0.98, blue: 0.99))
}
