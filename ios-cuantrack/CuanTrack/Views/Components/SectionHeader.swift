//
//  SectionHeader.swift
//  CuanTrack
//

import SwiftUI

struct SectionHeader: View {
    let title: String
    var actionLabel: String? = nil
    var action: (() -> Void)? = nil

    var body: some View {
        HStack(spacing: 8) {
            Text(title)
                .font(.system(size: 18, weight: .bold))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            Spacer()

            if let label = actionLabel, let action = action {
                Button(action: action) {
                    Text(label)
                        .font(.system(size: 14, weight: .medium))
                        .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
                }
            }
        }
    }
}

#Preview {
    SectionHeader(title: "Rekening", actionLabel: "Lihat semua") {}
        .padding(24)
}
