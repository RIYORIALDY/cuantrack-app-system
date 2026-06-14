//
//  CategoryChip.swift
//  CuanTrack
//

import SwiftUI

struct CategoryChip: View {
    let category: TransactionCategory
    let isSelected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 6) {
                Image(systemName: category.icon)
                    .font(.system(size: 13))

                Text(category.rawValue)
                    .font(.system(size: 14, weight: .medium))
            }
            .padding(.horizontal, 14)
            .padding(.vertical, 10)
            .background(isSelected ? category.color : Color(red: 0.95, green: 0.96, blue: 0.97))
            .foregroundStyle(isSelected ? .white : Color(red: 0.39, green: 0.45, blue: 0.55))
            .clipShape(.capsule)
        }
        .buttonStyle(.plain)
    }
}

#Preview {
    HStack(spacing: 8) {
        CategoryChip(category: .food, isSelected: true) {}
        CategoryChip(category: .transport, isSelected: false) {}
        CategoryChip(category: .shopping, isSelected: false) {}
    }
    .padding(24)
}
