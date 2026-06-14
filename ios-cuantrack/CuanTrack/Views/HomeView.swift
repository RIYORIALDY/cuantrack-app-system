//
//  HomeView.swift
//  CuanTrack
//

import SwiftUI

struct HomeView: View {
    @Bindable var store: FinanceStore
    let onAddTransaction: () -> Void
    let onNavigateToTransactions: () -> Void
    var onNavigateToProfile: (() -> Void)? = nil

    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            ScrollView {
                VStack(alignment: .leading, spacing: 24) {
                    headerSection
                    summarySection
                    accountsSection
                    recentTransactionsSection
                }
                .padding(.horizontal, 16)
                .padding(.bottom, 100)
            }

            fabButton
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
    }

    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Halo, Riyo")
                        .font(.system(size: 24, weight: .bold))
                        .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                    Text("Ringkasan hari ini")
                        .font(.system(size: 14))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                }

                Spacer()

                Button {
                    onNavigateToProfile?()
                } label: {
                    Circle()
                        .fill(Color(red: 0.95, green: 0.96, blue: 0.97))
                        .frame(width: 44, height: 44)
                        .overlay {
                            Image(systemName: "bell")
                                .font(.system(size: 18))
                                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                        }
                }
            }
        }
        .padding(.top, 8)
    }

    private var summarySection: some View {
        SummaryCard(
            totalBalance: store.totalBalance,
            monthlyExpense: store.monthlyExpense,
            monthlyIncome: store.monthlyIncome
        )
    }

    private var accountsSection: some View {
        VStack(spacing: 12) {
            SectionHeader(title: "Rekening", actionLabel: "Lihat semua") {}

            VStack(spacing: 10) {
                ForEach(store.accounts) { account in
                    AccountCard(
                        bankName: account.bankName,
                        balance: account.formattedBalance,
                        accentColor: bankColor(account.bankName)
                    )
                }
            }
        }
    }

    private var recentTransactionsSection: some View {
        VStack(spacing: 12) {
            SectionHeader(title: "Transaksi Terbaru", actionLabel: "Lihat semua") {
                onNavigateToTransactions()
            }

            VStack(spacing: 8) {
                ForEach(store.recentTransactions) { transaction in
                    TransactionRow(
                        category: transaction.category,
                        title: transaction.note.isEmpty ? transaction.category.rawValue : transaction.note,
                        amount: transaction.amount,
                        type: transaction.type
                    )
                }
            }
        }
    }

    private var fabButton: some View {
        Button(action: onAddTransaction) {
            Image(systemName: "plus")
                .font(.system(size: 22, weight: .semibold))
                .foregroundStyle(.white)
                .frame(width: 56, height: 56)
                .background(Color(red: 0.15, green: 0.39, blue: 0.92))
                .clipShape(.circle)
                .shadow(color: Color(red: 0.15, green: 0.39, blue: 0.92).opacity(0.35), radius: 12, y: 6)
        }
        .padding(.trailing, 20)
        .padding(.bottom, 20)
    }

    private func bankColor(_ name: String) -> Color {
        switch name {
        case "BCA": Color(red: 0.15, green: 0.39, blue: 0.92)
        case "BNI": Color(red: 0.10, green: 0.62, blue: 0.52)
        case "Mandiri": Color(red: 0.17, green: 0.46, blue: 0.82)
        default: Color(red: 0.39, green: 0.45, blue: 0.55)
        }
    }
}

#Preview {
    HomeView(
        store: FinanceStore(),
        onAddTransaction: {},
        onNavigateToTransactions: {}
    )
}
