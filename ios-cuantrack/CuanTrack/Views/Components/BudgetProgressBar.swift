//
//  BudgetProgressBar.swift
//  CuanTrack
//

import SwiftUI

struct BudgetProgressBar: View {
    let spent: Double
    let limit: Double
    let accentColor: Color

    private var progress: Double {
        guard limit > 0 else { return 0 }
        return min(spent / limit, 1.0)
    }

    private var healthColor: Color {
        if progress >= 1.0 { return Color(red: 0.86, green: 0.15, blue: 0.15) }
        if progress >= 0.75 { return Color(red: 0.96, green: 0.62, blue: 0.04) }
        return accentColor
    }

    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .leading) {
                RoundedRectangle(cornerRadius: 4)
                    .fill(Color(red: 0.89, green: 0.91, blue: 0.94))
                    .frame(height: 8)

                RoundedRectangle(cornerRadius: 4)
                    .fill(healthColor)
                    .frame(width: max(8, geometry.size.width * CGFloat(progress)), height: 8)
                    .animation(.easeInOut(duration: 0.6), value: progress)
            }
        }
        .frame(height: 8)
    }
}

private struct BudgetProgressBar_Previews: PreviewProvider {
    static var previews: some View {
        VStack(spacing: 20) {
            BudgetProgressBar(spent: 500_000, limit: 1_000_000, accentColor: .blue)
            BudgetProgressBar(spent: 800_000, limit: 1_000_000, accentColor: .orange)
            BudgetProgressBar(spent: 1_200_000, limit: 1_000_000, accentColor: .red)
        }
        .padding()
    }
}
