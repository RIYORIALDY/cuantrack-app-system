//
//  Transaction.swift
//  CuanTrack
//

import Foundation

enum TransactionType: String, Codable, CaseIterable {
    case expense = "Pengeluaran"
    case income = "Pemasukan"
    case transfer = "Transfer"
}

enum TransactionCategory: String, Codable, CaseIterable {
    case food = "Makanan"
    case transport = "Transport"
    case shopping = "Belanja"
    case bills = "Tagihan"
    case entertainment = "Hiburan"
    case other = "Lainnya"

    var icon: String {
        switch self {
        case .food: "fork.knife"
        case .transport: "car.fill"
        case .shopping: "bag.fill"
        case .bills: "doc.text.fill"
        case .entertainment: "tv.fill"
        case .other: "ellipsis.circle.fill"
        }
    }
}

struct Transaction: Identifiable, Codable {
    let id: UUID
    var type: TransactionType
    var category: TransactionCategory
    var amount: Double
    var accountName: String
    var date: Date
    var note: String
    var isRecurring: Bool

    init(
        id: UUID = UUID(),
        type: TransactionType = .expense,
        category: TransactionCategory = .other,
        amount: Double = 0,
        accountName: String = "",
        date: Date = Date(),
        note: String = "",
        isRecurring: Bool = false
    ) {
        self.id = id
        self.type = type
        self.category = category
        self.amount = amount
        self.accountName = accountName
        self.date = date
        self.note = note
        self.isRecurring = isRecurring
    }
}
