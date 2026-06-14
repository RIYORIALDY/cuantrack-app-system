//
//  TransactionListView.swift
//  CuanTrack
//

import SwiftUI

struct TransactionListView: View {
    @Bindable var store: FinanceStore
    @State private var searchText: String = ""
    @State private var selectedFilter: String = "Semua"

    private let filters = ["Semua", "Minggu ini", "Bulan ini", "Tahun ini"]

    var body: some View {
        VStack(spacing: 0) {
            searchAndFilterSection

            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    ForEach(store.transactionsGroupedByDate, id: \.0) { group in
                        dateGroup(date: group.0, transactions: group.1)
                    }
                }
                .padding(.horizontal, 16)
                .padding(.bottom, 100)
            }
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
    }

    private var searchAndFilterSection: some View {
        VStack(spacing: 12) {
            HStack(spacing: 10) {
                Image(systemName: "magnifyingglass")
                    .font(.system(size: 16))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                TextField("Cari transaksi...", text: $searchText)
                    .font(.system(size: 16))
            }
            .padding(.horizontal, 16)
            .frame(height: 44)
            .background(.white)
            .clipShape(.rect(cornerRadius: 12))

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(filters, id: \.self) { filter in
                        Button {
                            selectedFilter = filter
                        } label: {
                            Text(filter)
                                .font(.system(size: 14, weight: .medium))
                                .foregroundStyle(
                                    selectedFilter == filter
                                        ? .white
                                        : Color(red: 0.39, green: 0.45, blue: 0.55)
                                )
                                .padding(.horizontal, 16)
                                .padding(.vertical, 8)
                                .background(
                                    selectedFilter == filter
                                        ? Color(red: 0.15, green: 0.39, blue: 0.92)
                                        : Color(red: 0.95, green: 0.96, blue: 0.97)
                                )
                                .clipShape(.capsule)
                        }
                        .buttonStyle(.plain)
                    }
                }
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
    }

    private func dateGroup(date: String, transactions: [Transaction]) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(date)
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

            VStack(spacing: 6) {
                ForEach(transactions) { tx in
                    TransactionRow(
                        category: tx.category,
                        title: tx.note.isEmpty ? tx.category.rawValue : tx.note,
                        amount: tx.amount,
                        type: tx.type
                    )
                }
            }
        }
    }
}

#Preview {
    TransactionListView(store: FinanceStore())
}
