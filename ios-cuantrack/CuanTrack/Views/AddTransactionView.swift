//
//  AddTransactionView.swift
//  CuanTrack
//

import SwiftUI

struct AddTransactionView: View {
    @Bindable var store: FinanceStore
    @State private var selectedType: TransactionType = .expense
    @State private var selectedCategory: TransactionCategory = .food
    @State private var selectedAccount: String = "BCA"
    @State private var nominalText: String = ""
    @State private var note: String = ""
    @State private var date: Date = Date()
    @State private var isRecurring: Bool = false

    @Environment(\.dismiss) private var dismiss

    var body: some View {
        ZStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 24) {
                    segmentedControl
                    accountSelector
                    categorySelector
                    nominalInput
                    datePicker
                    noteInput
                    recurringToggle
                    saveButton
                }
                .padding(20)
            }

            if store.showToast {
                VStack {
                    Spacer().frame(height: 60)
                    ToastView(message: store.toastMessage, icon: "checkmark.circle.fill")
                    Spacer()
                }
                .transition(.move(edge: .top).combined(with: .opacity))
                .animation(.spring(response: 0.4, dampingFraction: 0.7), value: store.showToast)
            }
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
        .navigationTitle("Tambah Transaksi")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Button {
                    dismiss()
                } label: {
                    Image(systemName: "chevron.left")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
                }
            }
        }
    }

    private var accountNames: [String] {
        store.accounts.map(\.bankName)
    }

    private var segmentedControl: some View {
        HStack(spacing: 0) {
            ForEach(TransactionType.allCases, id: \.self) { type in
                Button {
                    withAnimation(.easeInOut(duration: 0.25)) {
                        selectedType = type
                    }
                } label: {
                    Text(type.rawValue)
                        .font(.system(size: 14, weight: .medium))
                        .foregroundStyle(selectedType == type ? .white : Color(red: 0.39, green: 0.45, blue: 0.55))
                        .frame(maxWidth: .infinity)
                        .frame(height: 40)
                        .background(
                            selectedType == type
                                ? segmentColor(type)
                                : Color.clear
                        )
                        .clipShape(.rect(cornerRadius: 10))
                }
                .buttonStyle(.plain)
            }
        }
        .padding(4)
        .background(Color(red: 0.95, green: 0.96, blue: 0.97))
        .clipShape(.rect(cornerRadius: 12))
    }

    private func segmentColor(_ type: TransactionType) -> Color {
        switch type {
        case .expense: Color(red: 0.86, green: 0.15, blue: 0.15)
        case .income: Color(red: 0.09, green: 0.64, blue: 0.29)
        case .transfer: Color(red: 0.15, green: 0.39, blue: 0.92)
        }
    }

    private var accountSelector: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Rekening")
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            Menu {
                ForEach(accountNames, id: \.self) { account in
                    Button(account) { selectedAccount = account }
                }
            } label: {
                HStack {
                    Text(selectedAccount)
                        .font(.system(size: 16))
                        .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                    Spacer()

                    Image(systemName: "chevron.down")
                        .font(.system(size: 14, weight: .semibold))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                }
                .padding(.horizontal, 16)
                .frame(height: 52)
                .background(.white)
                .clipShape(.rect(cornerRadius: 14))
                .overlay(
                    RoundedRectangle(cornerRadius: 14)
                        .stroke(Color(red: 0.89, green: 0.91, blue: 0.94), lineWidth: 1)
                )
            }
        }
    }

    private var categorySelector: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Kategori")
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            LazyVGrid(
                columns: [
                    GridItem(.flexible(), spacing: 8),
                    GridItem(.flexible(), spacing: 8),
                    GridItem(.flexible(), spacing: 8),
                ],
                spacing: 8
            ) {
                ForEach(TransactionCategory.allCases, id: \.self) { category in
                    CategoryChip(
                        category: category,
                        isSelected: selectedCategory == category
                    ) {
                        selectedCategory = category
                    }
                }
            }
        }
    }

    private var nominalInput: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Nominal")
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            HStack(spacing: 4) {
                Text("Rp")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                TextField("0", text: $nominalText)
                    .font(.system(size: 28, weight: .bold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
                    .keyboardType(.numberPad)
            }
            .padding(.horizontal, 16)
            .frame(height: 64)
            .background(.white)
            .clipShape(.rect(cornerRadius: 14))
            .overlay(
                RoundedRectangle(cornerRadius: 14)
                    .stroke(Color(red: 0.89, green: 0.91, blue: 0.94), lineWidth: 1)
            )
        }
    }

    private var datePicker: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Tanggal")
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            DatePicker("", selection: $date, displayedComponents: .date)
                .datePickerStyle(.compact)
                .labelsHidden()
                .padding(.horizontal, 16)
                .frame(height: 52)
                .background(.white)
                .clipShape(.rect(cornerRadius: 14))
                .overlay(
                    RoundedRectangle(cornerRadius: 14)
                        .stroke(Color(red: 0.89, green: 0.91, blue: 0.94), lineWidth: 1)
                )
        }
    }

    private var noteInput: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Catatan")
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            TextField("Tambahkan catatan...", text: $note, axis: .vertical)
                .font(.system(size: 16))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
                .padding(16)
                .frame(minHeight: 52)
                .background(.white)
                .clipShape(.rect(cornerRadius: 14))
                .overlay(
                    RoundedRectangle(cornerRadius: 14)
                        .stroke(Color(red: 0.89, green: 0.91, blue: 0.94), lineWidth: 1)
                )
        }
    }

    private var recurringToggle: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text("Tandai sebagai berulang")
                    .font(.system(size: 16, weight: .medium))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                Text("Transaksi akan otomatis tercatat setiap bulan")
                    .font(.system(size: 12))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
            }

            Spacer()

            Toggle("", isOn: $isRecurring)
                .labelsHidden()
                .tint(Color(red: 0.15, green: 0.39, blue: 0.92))
        }
        .padding(16)
        .background(.white)
        .clipShape(.rect(cornerRadius: 14))
    }

    private var saveButton: some View {
        let amount = Double(nominalText) ?? 0
        let canSave = amount > 0

        return CuanButton(
            title: "Simpan",
            style: canSave ? .primary : .disabled
        ) {
            guard let amount = Double(nominalText), amount > 0 else { return }
            store.addTransaction(
                type: selectedType,
                category: selectedCategory,
                amount: amount,
                accountName: selectedAccount,
                date: date,
                note: note.isEmpty ? selectedCategory.rawValue : note,
                isRecurring: isRecurring
            )
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.8) {
                dismiss()
            }
        }
        .padding(.top, 8)
        .padding(.bottom, 32)
    }
}

#Preview {
    NavigationStack {
        AddTransactionView(store: FinanceStore())
    }
}
