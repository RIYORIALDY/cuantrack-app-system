//
//  Account.swift
//  CuanTrack
//

import Foundation

struct Account: Identifiable, Codable {
    let id: UUID
    var bankName: String
    var accountHolder: String
    var balance: Double
    var accountNumber: String

    init(
        id: UUID = UUID(),
        bankName: String = "",
        accountHolder: String = "",
        balance: Double = 0,
        accountNumber: String = ""
    ) {
        self.id = id
        self.bankName = bankName
        self.accountHolder = accountHolder
        self.balance = balance
        self.accountNumber = accountNumber
    }

    var formattedBalance: String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = "."
        formatter.decimalSeparator = ","
        formatter.minimumFractionDigits = 0
        formatter.maximumFractionDigits = 0
        return "Rp \(formatter.string(from: NSNumber(value: balance)) ?? "0")"
    }
}
