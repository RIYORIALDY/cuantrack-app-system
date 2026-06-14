//
//  ProfileView.swift
//  CuanTrack
//

import SwiftUI

struct ProfileView: View {
    @State private var isDarkMode: Bool = false
    @State private var selectedCurrency: String = "IDR"

    private let currencies = ["IDR", "USD", "SGD", "MYR"]

    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                profileHeader

                VStack(spacing: 2) {
                    settingsRow(icon: "paintpalette", title: "Tampilan", trailing: {
                        HStack(spacing: 8) {
                            Text(isDarkMode ? "Gelap" : "Terang")
                                .font(.system(size: 14))
                                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                            Toggle("", isOn: $isDarkMode)
                                .labelsHidden()
                                .tint(Color(red: 0.15, green: 0.39, blue: 0.92))
                        }
                    })

                    Divider()
                        .padding(.leading, 52)

                    settingsRow(icon: "dollarsign.circle", title: "Mata uang", trailing: {
                        Menu {
                            ForEach(currencies, id: \.self) { currency in
                                Button(currency) { selectedCurrency = currency }
                            }
                        } label: {
                            HStack(spacing: 4) {
                                Text(selectedCurrency)
                                    .font(.system(size: 14))
                                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                                Image(systemName: "chevron.down")
                                    .font(.system(size: 12, weight: .semibold))
                                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                            }
                        }
                    })

                    Divider()
                        .padding(.leading, 52)

                    settingsRow(icon: "square.grid.2x2", title: "Kelola kategori", trailing: {
                        Image(systemName: "chevron.right")
                            .font(.system(size: 14, weight: .semibold))
                            .foregroundStyle(Color(red: 0.89, green: 0.91, blue: 0.94))
                    })

                    Divider()
                        .padding(.leading, 52)

                    settingsRow(icon: "lock.shield", title: "Keamanan", subtitle: "Ubah kata sandi", trailing: {
                        Image(systemName: "chevron.right")
                            .font(.system(size: 14, weight: .semibold))
                            .foregroundStyle(Color(red: 0.89, green: 0.91, blue: 0.94))
                    })
                }
                .padding(16)
                .background(.white)
                .clipShape(.rect(cornerRadius: 16))

                logoutButton
            }
            .padding(.horizontal, 16)
            .padding(.bottom, 100)
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
    }

    private var profileHeader: some View {
        VStack(spacing: 12) {
            Circle()
                .fill(Color(red: 0.15, green: 0.39, blue: 0.92).opacity(0.12))
                .frame(width: 72, height: 72)
                .overlay {
                    Text("R")
                        .font(.system(size: 32, weight: .bold))
                        .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
                }

            VStack(spacing: 4) {
                Text("Riyo Pratama")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                Text("riyo@email.com")
                    .font(.system(size: 14))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
            }
        }
        .padding(.top, 8)
    }

    private func settingsRow<Content: View>(
        icon: String,
        title: String,
        subtitle: String? = nil,
        @ViewBuilder trailing: () -> Content
    ) -> some View {
        HStack(spacing: 12) {
            Image(systemName: icon)
                .font(.system(size: 18))
                .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
                .frame(width: 24)

            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .font(.system(size: 16))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                if let subtitle = subtitle {
                    Text(subtitle)
                        .font(.system(size: 12))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                }
            }

            Spacer()

            trailing()
        }
        .frame(height: 52)
    }

    private var logoutButton: some View {
        Button {} label: {
            HStack(spacing: 8) {
                Image(systemName: "rectangle.portrait.and.arrow.right")
                    .font(.system(size: 16, weight: .medium))

                Text("Keluar")
                    .font(.system(size: 16, weight: .semibold))
            }
            .foregroundStyle(Color(red: 0.86, green: 0.15, blue: 0.15))
            .frame(maxWidth: .infinity)
            .frame(height: 52)
            .background(.white)
            .clipShape(.rect(cornerRadius: 16))
        }
    }
}

#Preview {
    ProfileView()
}
