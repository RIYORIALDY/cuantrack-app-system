import Foundation
import SwiftUI

@MainActor
@Observable
final class FinanceStore {
    var accounts: [Account] = FinanceStore.defaultAccounts
    var transactions: [Transaction] = FinanceStore.defaultTransactions
    var showToast: Bool = false
    var toastMessage: String = ""

    var totalBalance: Double {
        accounts.reduce(0) { $0 + $1.balance }
    }

    var monthlyIncome: Double {
        transactions
            .filter { $0.type == .income }
            .reduce(0) { $0 + $1.amount }
    }

    var monthlyExpense: Double {
        transactions
            .filter { $0.type == .expense }
            .reduce(0) { $0 + $1.amount }
    }

    var recentTransactions: [Transaction] {
        Array(transactions.prefix(5))
    }

    var transactionsGroupedByDate: [(String, [Transaction])] {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        formatter.locale = Locale(identifier: "id_ID")

        let calendar = Calendar.current
        let today = calendar.startOfDay(for: Date())
        let yesterday = calendar.date(byAdding: .day, value: -1, to: today)!

        let grouped = Dictionary(grouping: transactions) { transaction -> String in
            let txDay = calendar.startOfDay(for: transaction.date)
            if txDay == today {
                return "Hari ini"
            } else if txDay == yesterday {
                return "Kemarin"
            } else {
                return formatter.string(from: transaction.date)
            }
        }

        return grouped.sorted { lhs, rhs in
            let lhsDate = lhs.value.first?.date ?? Date.distantPast
            let rhsDate = rhs.value.first?.date ?? Date.distantPast
            return lhsDate > rhsDate
        }
    }

    func addTransaction(
        type: TransactionType,
        category: TransactionCategory,
        amount: Double,
        accountName: String,
        date: Date,
        note: String,
        isRecurring: Bool
    ) {
        let transaction = Transaction(
            id: UUID(),
            type: type,
            category: category,
            amount: amount,
            accountName: accountName,
            date: date,
            note: note,
            isRecurring: isRecurring
        )
        transactions.insert(transaction, at: 0)

        if let index = accounts.firstIndex(where: { $0.bankName == accountName }) {
            switch type {
            case .income:
                accounts[index].balance += amount
            case .expense:
                accounts[index].balance -= amount
            case .transfer:
                break
            }
        }

        showToastMessage("Transaksi tersimpan")
    }

    func deleteTransaction(_ transaction: Transaction) {
        guard let index = transactions.firstIndex(where: { $0.id == transaction.id }) else { return }
        let tx = transactions[index]

        if let accIndex = accounts.firstIndex(where: { $0.bankName == tx.accountName }) {
            switch tx.type {
            case .income:
                accounts[accIndex].balance -= tx.amount
            case .expense:
                accounts[accIndex].balance += tx.amount
            case .transfer:
                break
            }
        }

        transactions.remove(at: index)
    }

    private func showToastMessage(_ message: String) {
        toastMessage = message
        withAnimation(.spring(response: 0.4, dampingFraction: 0.7)) {
            showToast = true
        }
        Task {
            try? await Task.sleep(for: .seconds(2))
            withAnimation(.easeOut(duration: 0.3)) {
                showToast = false
            }
        }
    }

    static let defaultAccounts: [Account] = [
        Account(bankName: "BCA", accountHolder: "Riyo", balance: 3_250_000, accountNumber: "•••• 1234"),
        Account(bankName: "BNI", accountHolder: "Riyo", balance: 1_100_000, accountNumber: "•••• 5678"),
        Account(bankName: "Mandiri", accountHolder: "Riyo", balance: 8_100_000, accountNumber: "•••• 9012"),
    ]

    static let defaultTransactions: [Transaction] = [
        Transaction(
            id: UUID(),
            type: .expense, category: .food,
            amount: 45_000, accountName: "BCA",
            date: Calendar.current.date(byAdding: .hour, value: -5, to: Date()) ?? Date(),
            note: "Makan siang"
        ),
        Transaction(
            id: UUID(),
            type: .expense, category: .transport,
            amount: 25_000, accountName: "BCA",
            date: Calendar.current.date(byAdding: .hour, value: -8, to: Date()) ?? Date(),
            note: "Gojek"
        ),
        Transaction(
            id: UUID(),
            type: .income, category: .other,
            amount: 5_000_000, accountName: "Mandiri",
            date: Calendar.current.date(byAdding: .day, value: -1, to: Date()) ?? Date(),
            note: "Gaji bulanan"
        ),
        Transaction(
            id: UUID(),
            type: .expense, category: .bills,
            amount: 350_000, accountName: "BNI",
            date: Calendar.current.date(byAdding: .day, value: -1, to: Date()) ?? Date(),
            note: "Listrik"
        ),
        Transaction(
            id: UUID(),
            type: .expense, category: .shopping,
            amount: 100_000, accountName: "Mandiri",
            date: Calendar.current.date(byAdding: .day, value: -2, to: Date()) ?? Date(),
            note: "Top up e-wallet"
        ),
    ]
}
