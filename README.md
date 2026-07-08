# 🚗 Sistem Informasi Rental Mobil
### **Java Swing MDI Application with MySQL Database Integration**

Sistem Informasi Rental Mobil adalah aplikasi desktop berbasis **MDI (Multiple Document Interface)** yang dirancang untuk mengotomatisasi dan mengintegrasikan seluruh manajemen operasional bisnis penyewaan mobil. Sistem ini mempermudah admin dalam menangani data master secara dinamis serta melakukan kalkulasi biaya sewa secara otomatis dan *real-time*.

---

## 👥 Anggota Tim Pengembang & Pembagian Tugas
Projek ini disusun oleh mahasiswa **Teknik Informatika, Universitas Islam Balitar (UNISBA) Blitar**:

* 👤 **Rendra Adnan Farid** (25104410036) — *Back-end Logic*
* 👤 **Devika Novalina** (25104410038) — *Koneksi antara MySQL & NetBeans*
* 👤 **Muhammad Fachri Fairuz** (25104410043) — *Desain App, Navigasi User*
* 👤 **Febrinda Eka Prasetyo** (25104410046) — *MySQL Database*
* 👤 **Unggul Wahyudiningrat Eka Atmaja** (25104410077) — *Desain App, Navigasi User*

---

## 🚀 Fitur Utama Sistem

* 💻 **Modern MDI Interface:** Menggunakan `JDesktopPane` dan `JInternalFrame` sehingga banyak jendela pengelolaan data dapat dibuka bersamaan secara rapi dalam satu ruang kerja utama.
* ⚡ **Live Search Engine:** Filter pencarian data pada komponen tabel (`JTable`) berjalan secara *real-time* langsung saat admin mengetikkan karakter kata kunci.
* 🔢 **Smart ID Generator:** Otomatisasi pembuatan kode unik entitas (seperti `PLG0001`, `SPR0001`, `TRX0001`) berdasarkan baris data terakhir di dalam database.
* 🧮 **Automated Transaction Logic:** Kalkulasi biaya sewa otomatis yang menggabungkan parameter tarif armada, durasi (hari), dan jasa supir sebelum data disimpan.
* 🛡️ **SQL Injection Prevention:** Pengamanan kueri database menggunakan arsitektur `PreparedStatement`.

---

## 📊 Analisis Database & Entity Relationship Diagram (ERD)

Aplikasi ini didukung oleh basis data relasional bernama `rental_mobil_db` yang terdiri atas 4 tabel utama:

### 📐 Struktur Relasi Tabel

| Nama Tabel | Jenis Tabel | Primary Key | Foreign Key | Atribut Utama |
| :--- | :--- | :--- | :--- | :--- |
| **`pelanggan`** | Master | `id_pelanggan` | - | nama, no_ktp, no_hp, alamat |
| **`mobil`** | Master | `no_plat` | - | merk, jenis, tahun, harga_sewa |
| **`supir`** | Master | `id_supir` | - | nama_supir, no_hp, tarif_supir |
| **`sewa`** | Transaksi | `id_sewa` | `id_pelanggan`, `no_plat`, `id_supir` | tgl_sewa, lama_sewa, total_biaya |

### 🔄 Alur Logika Bisnis (Kardinalitas Relasi)
* **Pelanggan ➔ Sewa (*One-to-Many*):** Seorang pelanggan terdaftar dapat melakukan transaksi rental mobil berkali-kali pada waktu yang berbeda.
* **Mobil ➔ Sewa (*One-to-Many*):** Satu unit mobil dapat disewa dalam banyak transaksi transaksi berbeda secara bergantian.
* **Supir ➔ Sewa (*One-to-Many*):** Seorang pengemudi/supir dapat melayani berbagai transaksi penyewaan yang masuk.

### 🖼️ Preview ER Diagram
Berikut adalah visualisasi skema database yang digunakan pada sistem ini:

<p align="center">
  <img src="./ER-Diagram-Rental-Mobil.png" alt="ER Diagram Rental Mobil" width="85%">
</p>

---

## 📂 Tata Letak Kode Sumber (Directory Structure)

Struktur projek diatur menggunakan prinsip enkapsulasi paket (*package*) untuk memisahkan komponen GUI, logika koneksi data, serta aset multimedia (gambar latar belakang kustom):

```text
src/
│
├── KoneksiKeDB/
│   └── Koneksi.java                # Implementasi Singleton Pattern untuk jalur JDBC Driver MySQL
│
├── GUI/
│   ├── AplikasiRental.java         # Main Frame MDI / Welcome Screen pembuka aplikasi
│   ├── FormPelanggan.java          # Pengelolaan master data pelanggan & pencarian instan
│   ├── FormMobil.java              # Pengelolaan master data armada & tarif mobil
│   ├── FormSupir.java              # Pengelolaan master data supir & biaya jasa pengemudi
│   ├── FormSewa.java               # Form transaksi utama dengan engine kalkulasi otomatis
│   └── FormPetunjuk.java           # Dokumentasi panduan teknis operasional untuk pengguna akhir
│
└── img/                            # Aset Gambar Kustom untuk UI & Background Form
    ├── gambarmenuawal.jpg          # Latar belakang halaman Welcome utama
    ├── gambarmenu2.jpg             # Latar belakang menu navigasi utama
    ├── backgroundformpelanggan.jpg # Latar belakang form master pelanggan
    ├── backgroundformmobil.jpg     # Latar belakang form master mobil
    ├── backgroundformsupir.jpg     # Latar belakang form master supir
    └── backgroundformsewa.jpg      # Latar belakang form transaksi penyewaan
