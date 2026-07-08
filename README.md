<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>README - Sistem Informasi Rental Mobil</title>
</head>
<body>

    <h1>Sistem Informasi Rental Mobil Berbasis Java Swing &amp; MySQL</h1>

    <p>Sistem Informasi Rental Mobil adalah aplikasi desktop yang dirancang untuk mengelola operasional bisnis penyewaan mobil secara efisien dan terintegrasi. Aplikasi ini mempermudah admin dalam mengelola data master (pelanggan, mobil, supir) serta menangani kalkulasi otomatis pada transaksi penyewaan secara <em>real-time</em>.</p>

    <p>Projek ini disusun oleh tim mahasiswa <strong>Teknik Informatika, Universitas Islam Balitar (UNISBA) Blitar</strong>:</p>
    <ul>
        <li>Rendra Adnan Farid (25104410036)</li>
        <li>Devika Novalina (25104410038)</li>
        <li>Muhammad Fachri Fairuz (25104410043)</li>
        <li>Febrinda Eka Prasetyo (25104410046)</li>
        <li>Unggul Wahyudiningrat Eka Atmaja (25104410077)</li>
    </ul>

    <hr>

    <h2>🚀 Fitur Utama Aplikasi</h2>
    <ul>
        <li><strong>Antarmuka MDI (Multiple Document Interface):</strong> Menggunakan <code>JDesktopPane</code> yang memungkinkan banyak sub-jendela (<em>internal frame</em>) terbuka bersamaan dalam satu layar utama tanpa menumpuk berantakan.</li>
        <li><strong>Manajemen Data Master (CRUD):</strong> Pengelolaan data Pelanggan, Mobil, dan Supir yang dinamis (Simpan, Ubah, Hapus).</li>
        <li><strong>Otomatisasi ID (Primary Key):</strong> Sistem secara cerdas membuat kode unik otomatis seperti <code>PLG0001</code>, <code>SPR0001</code>, dan <code>TRX0001</code> berdasarkan urutan data terakhir di database.</li>
        <li><strong>Pencarian Real-Time (Live Search):</strong> Filter data pada tabel secara instan langsung saat admin mengetikkan kata kunci di kolom pencarian.</li>
        <li><strong>Kalkulasi Transaksi Otomatis:</strong> Menghitung total biaya sewa secara otomatis di balik layar berdasarkan kombinasi tarif mobil, tarif supir, dan durasi sewa yang diinputkan.</li>
        <li><strong>Keamanan Kueri Database:</strong> Menggunakan <code>PreparedStatement</code> untuk mencegah celah keamanan SQL Injection.</li>
    </ul>

    <hr>

    <h2>📊 Analisis &amp; Penjelasan ERD (Entity Relationship Diagram)</h2>
    <p>Sistem ini didukung oleh basis data relasional bernama <code>rental_mobil_db</code> yang terdiri dari 4 tabel utama yang saling terhubung:</p>

    <h3>1. Tabel Master</h3>
    <ul>
        <li><strong><code>pelanggan</code></strong>: Menyimpan identitas penyewa. Primary key: <code>id_pelanggan</code>. Atribut lainnya meliputi nama, nomor KTP, nomor HP, dan alamat.</li>
        <li><strong><code>mobil</code></strong>: Menyimpan armada yang tersedia. Primary key: <code>no_plat</code> (Nomor Plat). Atribut lainnya meliputi merk, jenis, tahun pembuatan, dan harga sewa per hari.</li>
        <li><strong><code>supir</code></strong>: Menyimpan data pengemudi. Primary key: <code>id_supir</code>. Atribut lainnya meliputi nama supir, nomor HP, dan tarif jasa supir per hari.</li>
    </ul>

    <h3>2. Tabel Transaksi</h3>
    <ul>
        <li><strong><code>sewa</code></strong>: Menjadi tabel utama jembatan transaksi yang menghubungkan ketiga data master di atas. Primary key: <code>id_sewa</code>.
            <ul>
                <li>Mengandung Foreign Key yang merujuk ke tabel master: <code>id_pelanggan</code>, <code>no_plat</code>, dan <code>id_supir</code>.</li>
                <li>Atribut kalkulatif &amp; operasional: <code>tgl_sewa</code>, <code>lama_sewa</code> (dalam hari), dan <code>total_biaya</code>.</li>
            </ul>
        </li>
    </ul>

    <h3>🔄 Logika Relasi Antartabel</h3>
    <ul>
        <li><strong>Pelanggan ke Sewa (<em>One-to-Many</em>):</strong> Satu pelanggan dapat melakukan transaksi penyewaan mobil lebih dari satu kali di waktu yang berbeda.</li>
        <li><strong>Mobil ke Sewa (<em>One-to-Many</em>):</strong> Satu mobil dapat disewa berulang kali melalui berbagai transaksi yang berbeda (selama masa sewa tidak bertabrakan).</li>
        <li><strong>Supir ke Sewa (<em>One-to-Many</em>):</strong> Jasa seorang supir dapat digunakan di beberapa transaksi penyewaan yang berbeda.</li>
    </ul>

    <hr>

    <h2>📂 Struktur Kode Program Java</h2>
    <p>Kode sumber aplikasi diatur ke dalam struktur paket (<em>package</em>) yang rapi untuk memisahkan logika koneksi dan antarmuka pengguna:</p>

<pre><code>src/
│
├── KoneksiKeDB/
│   └── Koneksi.java            # Implementasi Singleton Pattern untuk jalur koneksi MySQL via JDBC Driver
│
└── GUI/
    ├── AplikasiRental.java     # Frame utama (MDI) / Layar pembuka (Welcome Screen) aplikasi
    ├── FormPelanggan.java      # JInternalFrame untuk manajemen data pelanggan + fitur Live Search
    ├── FormMobil.java          # JInternalFrame untuk manajemen data armada mobil + fitur Live Search
    ├── FormSupir.java          # JInternalFrame untuk manajemen data supir &amp; tarif + fitur Live Search
    ├── FormSewa.java           # JInternalFrame pusat transaksi sewa dengan logika hitung otomatis
    └── FormPetunjuk.java       # JInternalFrame panduan operasional sistem bagi admin/pengguna baru
</code></pre>

    <hr>

    <h2>🛠️ Panduan Instalasi &amp; Menjalankan Projek</h2>

    <h3>Prasyarat Sistem</h3>
    <ul>
        <li><strong>Java Development Kit (JDK):</strong> Versi 8 atau yang lebih baru.</li>
        <li><strong>XAMPP / MySQL Server:</strong> Untuk mengaktifkan server database lokal.</li>
        <li><strong>Library Eksternal:</strong> MySQL JDBC Connector (<code>mysql-connector-j</code>).</li>
    </ul>

    <h3>Langkah-langkah Run Projek</h3>
    <ol>
        <li>Kloning Repositori Ini:
<pre><code>git clone https://github.com/username-kamu/nama-repositori.git</code></pre>
        </li>
        <li>Siapkan Database:
            <ul>
                <li>Aktifkan module <strong>Apache</strong> dan <strong>MySQL</strong> pada XAMPP Control Panel.</li>
                <li>Buka browser dan masuk ke <code>http://localhost/phpmyadmin/</code>.</li>
                <li>Buat database baru dengan nama <code>rental_mobil_db</code>.</li>
                <li>Import file SQL (jika ada) atau buat struktur tabel sesuai deskripsi ERD di atas.</li>
            </ul>
        </li>
        <li>Konfigurasi Koneksi Java:
            <ul>
                <li>Pastikan konfigurasi di <code>Koneksi.java</code> sudah sesuai dengan setelan database lokal kamu (secara <em>default</em> menggunakan user: <code>root</code> dan password kosong <code>""</code>).</li>
            </ul>
        </li>
        <li>Tambahkan Driver JDBC:
            <ul>
                <li>Masukkan file <code>.jar</code> dari <strong>MySQL JDBC Connector</strong> ke dalam <em>Libraries/Dependencies</em> projek di IDE kamu (NetBeans/Eclipse/IntelliJ/VS Code).</li>
            </ul>
        </li>
        <li>Jalankan Aplikasi:
            <ul>
                <li>Buka file <code>AplikasiRental.java</code>, klik kanan lalu pilih <strong>Run File</strong> (atau klik tombol Run pada IDE).</li>
            </ul>
        </li>
    </ol>

</body>
</html>
