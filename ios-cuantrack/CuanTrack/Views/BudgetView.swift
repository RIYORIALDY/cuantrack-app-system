//
//  BudgetView.swift
//  CuanTrack
//

import SwiftUI

struct BudgetView: View {
    @Bindable var store: FinanceStore
    @State private var editingBudget: TransactionCategory?
    @State private var editAmount: String = ""

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                monthlySummaryCard
                categoryBudgetsSection
            }
            .padding(.horizontal, 16)
            .padding(.top, 8)
            .padding(.bottom, 32)
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
        .sheet(item: $editingBudget) { category in
            budgetEditSheet(for: category)
        }
    }

    private var monthlySummaryCard: some View {
        VStack(spacing: 16) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Budget Bulan Ini")
                        .font(.system(size: 14))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                    Text(store.totalBudgeted, format: .currency(code: "IDR"))
                        .font(.system(size: 24, weight: .bold))
                        .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
                }

                Spacer()

                BudgetRing(
                    progress: store.budgetPercentUsed,
                    lineWidth: 5,
                    size: 56,
                    color: budgetRingColor
                )
            }

            Divider().overlay(Color(red: 0.89, green: 0.91, blue: 0.94))

            HStack(spacing: 0) {
                statItem(
                    title: "Terpakai",
                    value: formattedAmount(store.totalSpentThisMonth),
                    color: Color(red: 0.86, green: 0.15, blue: 0.15)
                )

                Rectangle()
                    .fill(Color(red: 0.89, green: 0.91, blue: 0.94))
                    .frame(width: 1, height: 36)

                statItem(
                    title: "Tersisa",
                    value: formattedAmount(store.remainingBudget),
                    color: Color(red: 0.09, green: 0.64, blue: 0.29)
                )
            }
        }
        .padding(20)
        .background(.white)
        .clipShape(.rect(cornerRadius: 16))
        .shadow(color: .black.opacity(0.04), radius: 8, y: 2)
    }

    private var budgetRingColor: Color {
        let p = store.budgetPercentUsed
        if p >= 1.0 { return Color(red: 0.86, green: 0.15, blue: 0.15) }
        if p >= 0.75 { return Color(red: 0.96, green: 0.62, blue: 0.04) }
        return Color(red: 0.15, green: 0.39, blue: 0.92)
    }

    private var categoryBudgetsSection: some View {
        VStack(spacing: 12) {
            SectionHeader(title: "Budget per Kategori", actionLabel: nil) {}

            VStack(spacing: 10) {
                ForEach(TransactionCategory.allCases, id: \.self) { category in
                    let budget = store.budgetForCategory(category)
                    let spent = store.spentForCategory(category)
                    let limit = budget.monthlyLimit
                    let percent = limit > 0 ? spent / limit : 0.0

                    budgetCategoryCard(
                        category: category,
                        budget: budget,
                        spent: spent,
                        percent: percent
                    )
                }
            }
        }
    }

    private func budgetCategoryCard(
        category: TransactionCategory,
        budget: BudgetCategory,
        spent: Double,
        percent: Double
    ) -> some View {
        Button {
            editAmount = budget.monthlyLimit > 0
                ? String(format: "%.0f", budget.monthlyLimit)
                : ""
            editingBudget = category
        } label: {
            VStack(spacing: 12) {
                HStack {
                    Image(systemName: category.icon)
                        .font(.system(size: 16))
                        .foregroundStyle(.white)
                        .frame(width: 36, height: 36)
                        .background(category.color)
                        .clipShape(.rect(cornerRadius: 10))

                    VStack(alignment: .leading, spacing: 2) {
                        Text(category.rawValue)
                            .font(.system(size: 16, weight: .medium))
                            .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                        Text(budget.monthlyLimit > 0
                            ? "\(formattedAmount(spent)) / \(budget.formattedLimit)"
                            : "Belum diatur")
                            .font(.system(size: 12))
                            .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                    }

                    Spacer()

                    if budget.monthlyLimit > 0 {
                        budgetHealthBadge(percent: percent)
                    }

                    Image(systemName: "chevron.right")
                        .font(.system(size: 12, weight: .semibold))
                        .foregroundStyle(Color(red: 0.78, green: 0.81, blue: 0.86))
                }

                if budget.monthlyLimit > 0 {
                    BudgetProgressBar(
                        spent: spent,
                        limit: budget.monthlyLimit,
                        accentColor: category.color
                    )
                }
            }
            .padding(16)
            .background(.white)
            .clipShape(.rect(cornerRadius: 14))
            .shadow(color: .black.opacity(0.03), radius: 4, y: 1)
        }
        .buttonStyle(.plain)
    }

    private func budgetHealthBadge(percent: Double) -> some View {
        let health = BudgetHealth.from(percent: percent)

        return Text(health.label)
            .font(.system(size: 11, weight: .semibold))
            .foregroundStyle(badgeColor(health))
            .padding(.horizontal, 10)
            .padding(.vertical, 4)
            .background(badgeColor(health).opacity(0.12))
            .clipShape(.capsule)
    }

    private func badgeColor(_ health: BudgetHealth) -> Color {
        switch health {
        case .safe: Color(red: 0.09, green: 0.64, blue: 0.29)
        case .warning: Color(red: 0.96, green: 0.62, blue: 0.04)
        case .critical: Color(red: 0.86, green: 0.15, blue: 0.15)
        }
    }

    private func statItem(title: String, value: String, color: Color) -> some View {
        VStack(spacing: 2) {
            Text(value)
                .font(.system(size: 18, weight: .bold))
                .foregroundStyle(color)

            Text(title)
                .font(.system(size: 12))
                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
        }
        .frame(maxWidth: .infinity)
    }

    private func formattedAmount(_ amount: Double) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = "."
        formatter.locale = Locale(identifier: "id_ID")

        let absAmount = abs(amount)
        let formatted = formatter.string(from: NSNumber(value: absAmount)) ?? "0"
        if amount < 0 {
            return "-Rp \(formatted)"
        }
        return "Rp \(formatted)"
    }

    private func budgetEditSheet(for category: TransactionCategory) -> some View {
        NavigationStack {
            VStack(spacing: 24) {
                Image(systemName: category.icon)
                    .font(.system(size: 28))
                    .foregroundStyle(.white)
                    .frame(width: 60, height: 60)
                    .background(category.color)
                    .clipShape(.rect(cornerRadius: 16))

                Text("Budget \(category.rawValue)")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                VStack(alignment: .leading, spacing: 8) {
                    Text("Batas Bulanan")
                        .font(.system(size: 14, weight: .medium))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                    HStack(spacing: 4) {
                        Text("Rp")
                            .font(.system(size: 22, weight: .bold))
                            .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                        TextField("0", text: $editAmount)
                            .font(.system(size: 22, weight: .bold))
                            .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
                            .keyboardType(.numberPad)
                    }
                    .padding(.horizontal, 16)
                    .frame(height: 56)
                    .background(Color(red: 0.97, green: 0.98, blue: 0.99))
                    .clipShape(.rect(cornerRadius: 14))
                }

                Spacer()

                CuanButton(title: "Simpan Budget", style: .primary) {
                    let limit = Double(editAmount) ?? 0
                    store.setBudget(category: category, limit: limit)
                    editingBudget = nil
                }
            }
            .padding(24)
            .navigationTitle("Atur Budget")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Batal") { editingBudget = nil }
                        .font(.system(size: 16, weight: .medium))
                }
            }
        }
        .presentationDetents([.height(360)])
        .presentationDragIndicator(.visible)
    }
}

private struct BudgetRing: View {
    let progress: Double
    let lineWidth: CGFloat
    let size: CGFloat
    let color: Color

    var body: some View {
        ZStack {
            Circle()
                .stroke(Color(red: 0.89, green: 0.91, blue: 0.94), lineWidth: lineWidth)

            Circle()
                .trim(from: 0, to: CGFloat(progress))
                .stroke(color, style: StrokeStyle(lineWidth: lineWidth, lineCap: .round))
                .rotationEffect(.degrees(-90))
                .animation(.easeInOut(duration: 0.8), value: progress)

            Text("\(Int(progress * 100))%")
                .font(.system(size: 13, weight: .bold))
                .foregroundStyle(color)
        }
        .frame(width: size, height: size)
    }
}

extension TransactionCategory: @retroactive Identifiable {
    public var id: String { rawValue }
}

#Preview {
    BudgetView(store: FinanceStore())
}
