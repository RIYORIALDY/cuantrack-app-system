//
//  CuanTextField.swift
//  CuanTrack
//

import SwiftUI

enum CuanTextFieldState {
    case `default`
    case focused
    case error
}

struct CuanTextField: View {
    let placeholder: String
    @Binding var text: String
    var state: CuanTextFieldState = .default
    var isSecure: Bool = false
    var keyboardType: UIKeyboardType = .default
    var leadingIcon: String? = nil

    @State private var isPasswordVisible: Bool = false
    @FocusState private var isFocused: Bool

    private var borderColor: Color {
        switch state {
        case .default: Color(red: 0.89, green: 0.91, blue: 0.94)
        case .focused: Color(red: 0.15, green: 0.39, blue: 0.92)
        case .error: Color(red: 0.86, green: 0.15, blue: 0.15)
        }
    }

    var body: some View {
        HStack(spacing: 10) {
            if let icon = leadingIcon {
                Image(systemName: icon)
                    .font(.system(size: 16))
                    .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
            }

            if isSecure && !isPasswordVisible {
                SecureField(placeholder, text: $text)
                    .focused($isFocused)
            } else {
                TextField(placeholder, text: $text)
                    .focused($isFocused)
                    .keyboardType(keyboardType)
            }

            if isSecure {
                Button {
                    isPasswordVisible.toggle()
                } label: {
                    Image(systemName: isPasswordVisible ? "eye.slash" : "eye")
                        .font(.system(size: 16))
                        .foregroundStyle(Color(red: 0.39, green: 0.45, blue: 0.55))
                }
            }
        }
        .font(.system(size: 16))
        .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
        .padding(.horizontal, 16)
        .frame(height: 52)
        .background(Color.white)
        .clipShape(.rect(cornerRadius: 14))
        .overlay(
            RoundedRectangle(cornerRadius: 14)
                .stroke(borderColor, lineWidth: isFocused ? 2 : 1)
        )
        .animation(.easeInOut(duration: 0.2), value: isFocused)
        .animation(.easeInOut(duration: 0.2), value: state)
    }
}

#Preview {
    VStack(spacing: 16) {
        CuanTextField(placeholder: "Email / No HP", text: .constant(""), leadingIcon: "envelope")
        CuanTextField(placeholder: "Password", text: .constant(""), isSecure: true, leadingIcon: "lock")
        CuanTextField(placeholder: "Error state", text: .constant("wrong"), state: .error, leadingIcon: "envelope")
    }
    .padding(24)
    .background(Color(red: 0.97, green: 0.98, blue: 0.99))
}
