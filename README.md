# CuanTrack — Aplikasi Pencatat Keuangan Harian

![Platform](https://img.shields.io/badge/Platform-iOS%20%7C%20Android-blue)
![Swift](https://img.shields.io/badge/Swift-5.9-orange)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple)
![Backend](https://img.shields.io/badge/Backend-Supabase-green)
![Status](https://img.shields.io/badge/Status-Academic%20Project-lightgrey)

**CuanTrack** adalah aplikasi *daily cashflow tracking* yang memungkinkan pengguna mencatat pemasukan dan pengeluaran dari berbagai rekening bank dalam satu tempat. Dibangun sebagai proyek UTS mata kuliah **Mobile Computing**.

---

## Screenshots

| Login | Dashboard | Tambah Transaksi | Budget | Daftar Transaksi |
|:---:|:---:|:---:|:---:|:---:|
| Selamat Datang | Ringkasan Saldo | Input Pengeluaran/Pemasukan | Pantau Anggaran | Riwayat Transaksi |

---

## Fitur Utama

- **Login** — Autentikasi sederhana dengan email/nomor HP dan password
- **Dashboard** — Ringkasan saldo total, pemasukan & pengeluaran bulan ini, daftar rekening bank
- **Tambah Transaksi** — Catat pengeluaran, pemasukan, atau transfer antar rekening dengan kategori, nominal, tanggal, dan catatan
- **Budget** — Tetapkan anggaran per kategori dan pantau progres pengeluaran
- **Riwayat Transaksi** — Daftar transaksi lengkap yang bisa difilter dan dicari
- **Multi Rekening** — Kelola beberapa rekening bank dalam satu aplikasi (BCA, BNI, Mandiri, dll.)

---

## Desain & UI/UX

Dirancang dengan prinsip **minimal modern fintech**:

- **Warna Primer:** `#2563EB` (Biru)
- **Latar:** `#F8FAFC` (Putih kebiruan)
- **Income:** `#16A34A` (Hijau)
- **Expense:** `#DC2626` (Merah)
- **Tipografi:** Inter (24/18/16/14/12pt)
- **Spacing:** Sistem 8pt (8, 12, 16, 20, 24)
- **Komponen:** Kartu melengkung (radius 12–16), bayangan halus, bottom navigation, FAB

---

## Tech Stack

| Platform | Teknologi |
|----------|-----------|
| **iOS** | Swift 5.9, SwiftUI, MVVM |
| **Android** | Kotlin 2.0, Jetpack Compose, Material 3 |
| **Backend** | Supabase (PostgreSQL + REST API) |

---

## Struktur Proyek

```
cuantrack/
├── ios-cuantrack/          # Aplikasi iOS (SwiftUI)
│   └── CuanTrack/
│       ├── Models/         # Data models (Transaction, Account, Budget, Category)
│       ├── Views/          # Screens & reusable components
│       │   ├── LoginView.swift
│       │   ├── HomeView.swift
│       │   ├── AddTransactionView.swift
│       │   ├── TransactionListView.swift
│       │   ├── BudgetView.swift
│       │   ├── ProfileView.swift
│       │   └── Components/ # Buttons, Cards, Chips, Fields, Toast
│       └── ContentView.swift
├── android-cuantrack/      # Aplikasi Android (Kotlin/Compose)
│   └── app/src/main/java/com/rork/cuantrackandroid/
│       ├── models/         # Data models
│       ├── data/           # ViewModel & state management
│       └── ui/
│           ├── screens/    # 6 screens
│           ├── components/ # Reusable composables
│           ├── navigation/ # NavHost & bottom bar
│           └── theme/      # Color, typography, theme
├── backend/                # Supabase backend
│   └── types.ts            # Database schema (accounts, transactions)
└── rork.json               # Project configuration
```

---

## Cara Menjalankan

### iOS (Xcode diperlukan di macOS)

```bash
cd ios-cuantrack
open CuanTrack.xcodeproj
# Pilih simulator → Run (Cmd+R)
```

### Android (Android Studio atau VS Code + Android SDK)

```bash
cd android-cuantrack
./gradlew assembleDebug
# Atau buka folder ini di Android Studio → Run
```

### Backend (Supabase)

Backend sudah terkonfigurasi di Supabase. Skema database meliputi:
- **accounts** — Data rekening bank
- **transactions** — Riwayat transaksi (type, amount, category, date, account, note)

---

## Screens (5 Layar Utama)

| # | Screen | Deskripsi |
|---|--------|-----------|
| 1 | **Login** | Halaman masuk — email/no HP, password, tombol Masuk, link lupa password & daftar |
| 2 | **Home / Dashboard** | Ringkasan saldo, rekening, transaksi terbaru, bottom nav + FAB |
| 3 | **Tambah Transaksi** | Form input pengeluaran/pemasukan/transfer dengan kategori chip, nominal, tanggal |
| 4 | **Daftar Transaksi** | List transaksi dikelompokkan per tanggal, search bar, filter |
| 5 | **Profil / Settings** | Info pengguna, pengaturan tema, mata uang, kategori, logout |

---

## Status Proyek

Proyek ini dibuat untuk keperluan akademik (UTS Mobile Computing). Desain UI/UX mengikuti standar fintech modern dengan komponen yang siap dikonversi ke framework lain (Flutter, React Native, dll).

---

## Lisensi

Proyek akademik — bebas digunakan untuk pembelajaran.
