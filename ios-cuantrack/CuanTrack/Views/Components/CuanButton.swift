//
//  CuanButton.swift
//  CuanTrack
//

import SwiftUI

enum CuanButtonStyle {
    case primary
    case secondary
    case disabled
}

struct CuanButton: View {
    let title: String
    let style: CuanButtonStyle
    let action: () -> Void

    private var foregroundColor: Color {
        switch style {
        case .primary: .white
        case .secondary: Color(red: 0.15, green: 0.39, blue: 0.92)
        case .disabled: Color(red: 0.39, green: 0.45, blue: 0.55)
        }
    }

    private var backgroundColor: Color {
        switch style {
        case .primary: Color(red: 0.15, green: 0.39, blue: 0.92)
        case .secondary: Color(red: 0.15, green: 0.39, blue: 0.92).opacity(0.08)
        case .disabled: Color(red: 0.89, green: 0.91, blue: 0.94)
        }
    }

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.system(size: 16, weight: .semibold))
                .foregroundStyle(foregroundColor)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(backgroundColor)
                .clipShape(.rect(cornerRadius: 14))
        }
        .disabled(style == .disabled)
    }
}

#Preview {
    VStack(spacing: 16) {
        CuanButton(title: "Masuk", style: .primary) {}
        CuanButton(title: "Daftar", style: .secondary) {}
        CuanButton(title: "Disabled", style: .disabled) {}
    }
    .padding(24)
}
