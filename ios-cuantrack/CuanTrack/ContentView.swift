//
//  ContentView.swift
//  CuanTrack
//

import SwiftUI

struct ContentView: View {
    @State private var isAuthenticated: Bool = false
    @State private var store: FinanceStore = FinanceStore()

    var body: some View {
        if isAuthenticated {
            MainTabView(store: store)
        } else {
            LoginView {
                withAnimation(.easeInOut(duration: 0.4)) {
                    isAuthenticated = true
                }
            }
        }
    }
}

enum AppTab: String, CaseIterable {
    case home = "Beranda"
    case transactions = "Transaksi"
    case budget = "Budget"
    case profile = "Profil"

    var icon: String {
        switch self {
        case .home: "house.fill"
        case .transactions: "list.bullet.rectangle"
        case .budget: "chart.pie.fill"
        case .profile: "person.fill"
        }
    }
}

struct MainTabView: View {
    @Bindable var store: FinanceStore
    @State private var selectedTab: AppTab = .home
    @State private var showAddTransaction: Bool = false

    var body: some View {
        ZStack(alignment: .bottom) {
            TabView(selection: $selectedTab) {
                NavigationStack {
                    HomeView(
                        store: store,
                        onAddTransaction: { showAddTransaction = true },
                        onNavigateToTransactions: { selectedTab = .transactions },
                        onNavigateToProfile: { selectedTab = .profile }
                    )
                    .toolbar(.hidden, for: .navigationBar)
                }
                .tag(AppTab.home)

                NavigationStack {
                    TransactionListView(store: store)
                        .navigationTitle("Transaksi")
                        .navigationBarTitleDisplayMode(.large)
                }
                .tag(AppTab.transactions)

                NavigationStack {
                    BudgetView(store: store)
                        .navigationTitle("Budget")
                        .navigationBarTitleDisplayMode(.large)
                }
                .tag(AppTab.budget)

                NavigationStack {
                    ProfileView()
                        .navigationTitle("Profil")
                        .navigationBarTitleDisplayMode(.large)
                }
                .tag(AppTab.profile)
            }

            customTabBar
        }
        .sheet(isPresented: $showAddTransaction) {
            NavigationStack {
                AddTransactionView(store: store)
            }
        }
    }

    private var customTabBar: some View {
        HStack(spacing: 0) {
            ForEach(AppTab.allCases, id: \.self) { tab in
                Button {
                    withAnimation(.easeInOut(duration: 0.2)) {
                        selectedTab = tab
                    }
                } label: {
                    VStack(spacing: 4) {
                        Image(systemName: tab.icon)
                            .font(.system(size: 20, weight: .medium))

                        Text(tab.rawValue)
                            .font(.system(size: 11, weight: .medium))
                    }
                    .foregroundStyle(
                        selectedTab == tab
                            ? Color(red: 0.15, green: 0.39, blue: 0.92)
                            : Color(red: 0.39, green: 0.45, blue: 0.55)
                    )
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                }
                .buttonStyle(.plain)
            }
        }
        .padding(.bottom, 20)
        .padding(.horizontal, 8)
        .background {
            UnevenRoundedRectangle(
                cornerRadii: RectangleCornerRadii(topLeading: 20, topTrailing: 20)
            )
            .fill(.white)
            .shadow(color: .black.opacity(0.06), radius: 8, y: -2)
        }
    }
}



#Preview {
    ContentView()
}
