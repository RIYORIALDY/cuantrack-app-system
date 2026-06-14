//
//  Category.swift
//  CuanTrack
//

import SwiftUI

extension TransactionCategory {
    var color: Color {
        switch self {
        case .food: Color(red: 0.96, green: 0.53, blue: 0.22)
        case .transport: Color(red: 0.23, green: 0.60, blue: 0.85)
        case .shopping: Color(red: 0.67, green: 0.33, blue: 0.85)
        case .bills: Color(red: 0.87, green: 0.33, blue: 0.33)
        case .entertainment: Color(red: 0.94, green: 0.20, blue: 0.55)
        case .other: Color(red: 0.39, green: 0.45, blue: 0.55)
        }
    }
}
