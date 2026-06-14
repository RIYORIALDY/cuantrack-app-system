//
//  ToastView.swift
//  CuanTrack
//

import SwiftUI

struct ToastView: View {
    let message: String
    let icon: String

    var body: some View {
        HStack(spacing: 10) {
            Image(systemName: icon)
                .font(.system(size: 16, weight: .semibold))
                .foregroundStyle(Color(red: 0.09, green: 0.64, blue: 0.29))

            Text(message)
                .font(.system(size: 14, weight: .medium))
                .foregroundStyle(Color(red: 0.06, green: 0.09, blue: 0.16))
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 14)
        .background(.white)
        .clipShape(.capsule)
        .shadow(color: .black.opacity(0.1), radius: 10, y: 4)
    }
}

struct ToastModifier: ViewModifier {
    @Binding var isPresented: Bool
    let message: String
    let icon: String

    func body(content: Content) -> some View {
        ZStack(alignment: .top) {
            content

            if isPresented {
                ToastView(message: message, icon: icon)
                    .padding(.top, 60)
                    .transition(.move(edge: .top).combined(with: .opacity))
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                            withAnimation(.easeOut(duration: 0.3)) {
                                isPresented = false
                            }
                        }
                    }
            }
        }
        .animation(.spring(response: 0.4, dampingFraction: 0.7), value: isPresented)
    }
}

extension View {
    func toast(isPresented: Binding<Bool>, message: String, icon: String = "checkmark.circle.fill") -> some View {
        modifier(ToastModifier(isPresented: isPresented, message: message, icon: icon))
    }
}

#Preview {
    ToastView(message: "Transaksi tersimpan", icon: "checkmark.circle.fill")
        .padding(24)
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
}
