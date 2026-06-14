//
//  Budget.swift
//  CuanTrack
//

import Foundation

struct BudgetCategory: Identifiable, Codable {
    let id: UUID
    var category: TransactionCategory
    var monthlyLimit: Double
    var month: Int
    var year: Int
    var spent: Double = 0

    init(
        id: UUID = UUID(),
        category: TransactionCategory,
        monthlyLimit: Double = 0,
        month: Int? = nil,
        year: Int? = nil
    ) {
        self.id = id
        self.category = category
        self.monthlyLimit = monthlyLimit
        let now = Date()
        let calendar = Calendar.current
        self.month = month ?? calendar.component(.month, from: now)
        self.year = year ?? calendar.component(.year, from: now)
    }

    var formattedLimit: String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = "."
        formatter.locale = Locale(identifier: "id_ID")
        return "Rp \(formatter.string(from: NSNumber(value: monthlyLimit)) ?? "0")"
    }

    var percentSpent: Double {
        guard monthlyLimit > 0 else { return 0 }
        return min(spent / monthlyLimit, 1.0)
    }
}


enum BudgetHealth {
    case safe
    case warning
    case critical

    var label: String {
        switch self {
        case .safe: "Aman"
        case .warning: "Hampir habis"
        case .critical: "Melebihi"
        }
    }

    static func from(percent: Double) -> BudgetHealth {
        if percent >= 1.0 { return .critical }
        if percent >= 0.75 { return .warning }
        return .safe
    }
}
