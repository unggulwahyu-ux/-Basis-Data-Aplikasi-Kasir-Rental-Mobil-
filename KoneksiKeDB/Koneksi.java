package KoneksiKeDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    // Variabel statis untuk menyimpan satu objek koneksi (Pattern Singleton)
    private static Connection koneksi;

    // =======================================================================
    // METHOD: Mengambil atau Membuka Jalur Koneksi ke Database MySQL
    // =======================================================================
    public static Connection getKoneksi() {
        // Cek apakah koneksi belum pernah dibuat atau masih kosong
        if (koneksi == null) {
            try {
                // Konfigurasi alamat database, username, dan password XAMPP MySQL
                String url = "jdbc:mysql://localhost:3306/rental_mobil_db";
                String user = "root";
                String password = ""; // Kosongkan jika menggunakan bawaan XAMPP
                
                // Mendaftarkan Driver JDBC MySQL (menggunakan versi connector msql-cj terbaru)
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                
                // Membuat sambutan/jalur koneksi ke database berdasarkan konfigurasi di atas
                koneksi = DriverManager.getConnection(url, user, password);
                
                // Pesan sukses jika Java berhasil mengetuk pintu database
                System.out.println("Koneksi ke Database Berhasil!");
            } catch (SQLException e) {
                // Pesan error jika database belum dinyalakan atau nama database salah
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        // Kembalikan objek koneksi yang sudah berhasil dibuat
        return koneksi;
    }
}