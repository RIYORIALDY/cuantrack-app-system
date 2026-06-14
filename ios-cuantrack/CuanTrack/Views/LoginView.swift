//
//  LoginView.swift
//  CuanTrack
//

import SwiftUI

struct LoginView: View {
    @State private var email: String = ""
    @State private var password: String = ""
    @FocusState private var focusedField: Field?
    @State private var isLoading: Bool = false

    private let onLogin: () -> Void

    enum Field {
        case email, password
    }

    init(onLogin: @escaping () -> Void) {
        self.onLogin = onLogin
    }

    private var isFormValid: Bool {
        !email.trimmingCharacters(in: .whitespaces).isEmpty &&
        !password.trimmingCharacters(in: .whitespaces).isEmpty
    }

    var body: some View {
        VStack(spacing: 0) {
            VStack(alignment: .leading, spacing: 24) {
                Spacer().frame(height: 16)

                wordmarkSection
                welcomeSection
                formSection
                forgotPasswordLink
                loginButton
            }
            .padding(.horizontal, 24)

            Spacer()

            registerFooter
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
        .onTapGesture {
            focusedField = nil
        }
    }

    private var wordmarkSection: some View {
        HStack(spacing: 10) {
            ZStack {
                RoundedRectangle(cornerRadius: 14)
                    .fill(Color(red: 0.15, green: 0.39, blue: 0.92))
                    .frame(width: 48, height: 48)

                Image(systemName: "chart.line.uptrend.xyaxis")
                    .font(.system(size: 22, weight: .medium))
                    .foregroundStyle(.white)
            }

            VStack(alignment: .leading, spacing: 0) {
                Text("Cuan")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

                Text("Track")
                    .font(.system(size: 22, weight: .light))
                    .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
            }
        }
    }

    private var welcomeSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Selamat datang")
                .font(.system(size: 24, weight: .bold))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))

            Text("Catat pengeluaran dan pantau saldo semua rekening.")
                .font(.system(size: 14))
                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                .lineSpacing(4)
        }
    }

    private var formSection: some View {
        VStack(spacing: 12) {
            CuanTextField(
                placeholder: "Email / No HP",
                text: $email,
                state: focusedField == .email ? .focused : .default,
                keyboardType: .emailAddress,
                leadingIcon: "envelope"
            )
            .focused($focusedField, equals: .email)

            CuanTextField(
                placeholder: "Kata sandi",
                text: $password,
                state: focusedField == .password ? .focused : .default,
                isSecure: true,
                leadingIcon: "lock"
            )
            .focused($focusedField, equals: .password)
        }
    }

    private var forgotPasswordLink: some View {
        HStack {
            Spacer()

            Button("Lupa password?") {}
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
        }
    }

    private var loginButton: some View {
        VStack(spacing: 16) {
            CuanButton(
                title: isLoading ? "Memuat..." : "Masuk",
                style: isFormValid ? .primary : .disabled
            ) {
                isLoading = true
                focusedField = nil
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                    isLoading = false
                    onLogin()
                }
            }

            HStack(spacing: 4) {
                Text("Belum punya akun?")
                    .font(.system(size: 14))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

                Button("Daftar") {}
                    .font(.system(size: 14, weight: .semibold))
                    .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
            }
        }
    }

    private var registerFooter: some View {
        HStack(spacing: 4) {
            Text("Belum punya akun?")
                .font(.system(size: 14))
                .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))

            Button("Daftar") {}
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(Color(red: 0.15, green: 0.39, blue: 0.92))
        }
        .padding(.bottom, 40)
        .opacity(0)
    }
}

#Preview {
    LoginView(onLogin: {})
}
