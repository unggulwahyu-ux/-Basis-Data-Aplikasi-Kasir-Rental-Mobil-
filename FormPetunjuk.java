package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormPetunjuk extends JInternalFrame {
    
    // =======================================================================
    // CONSTRUCTOR: Pengaturan Tampilan Awal Jendela Petunjuk Penggunaan
    // =======================================================================
    public FormPetunjuk() {
        // Mengatur judul jendela serta mengaktifkan fitur ubah ukuran, tutup, perbesar, dan perkecil
        super("Petunjuk Operasional Sistem", true, true, true, true);
        setSize(600, 580); // Ukuran disesuaikan agar tata letak teks panduan lebih lega dan rapi
        setLayout(new BorderLayout());

        // 1. Panel Utama sebagai Kontainer Semua Kotak Petunjuk
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Warna latar abu-abu terang yang nyaman di mata
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15); // Jarak antar kotak petunjuk
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Pembuatan Garis Pembatas Kotak
        Border lineBorder = BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true);

        // --- KOTAK 1: LANGKAH AWAL ---
        JPanel kotak1 = new JPanel(new BorderLayout());
        kotak1.setBackground(Color.WHITE);
        kotak1.setBorder(BorderFactory.createTitledBorder(lineBorder, " 1. LANGKAH AWAL: ISI DATA UTAMA ", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12), new Color(0, 102, 204)));
        
        JLabel lblLangkah1 = new JLabel("<html>Sebelum membuat nota sewa, pastikan data pendukung sudah dimasukkan melalui menu <b>\"Data Master\"</b> di bagian atas aplikasi:<br>"
                + "• <b>Pelanggan</b>: Untuk mendaftarkan nama dan data diri penyewa baru.<br>"
                + "• <b>Mobil</b>: Untuk memasukkan armada baru, cukup ketik Nomor Plat kendaraan (maksimal 20 karakter termasuk spasi) beserta Tarif Sewa per harinya.<br>"
                + "• <b>Supir</b>: Untuk memasukkan daftar supir yang tersedia (jika penyewa ingin menggunakan jasa supir).</html>");
        lblLangkah1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLangkah1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kotak1.add(lblLangkah1, BorderLayout.CENTER);
        
        gbc.gridy = 0;
        mainPanel.add(kotak1, gbc);

        // --- KOTAK 2: TRANSAKSI & CARA UBAH DATA ---
        JPanel kotak2 = new JPanel(new BorderLayout());
        kotak2.setBackground(Color.WHITE);
        kotak2.setBorder(BorderFactory.createTitledBorder(lineBorder, " 2. LANGKAH TRANSAKSI & CARA MEMPERBAIKI DATA ", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12), new Color(0, 102, 204)));
        
        JLabel lblLangkah2 = new JLabel("<html><b>A. Membuat Nota Baru:</b><br>"
                + "• Buka menu <b>Transaksi -> Sewa Mobil</b>.<br>"
                + "• Nomor Nota Transaksi akan dibuat otomatis oleh sistem secara berurutan, jadi Anda tidak perlu mengetiknya sendiri.<br>"
                + "• Pada pilihan Pelanggan dan Mobil, Anda cukup <b>mengklik tombol panah bawah lalu memilih nama atau nomor plat</b> yang sudah terdaftar. Sistem akan langsung membaca data pilihan Anda dengan benar.<br><br>"
                + "<b>B. Cara Mengubah Data yang Salah:</b><br>"
                + "1. Klik terlebih dahulu <b>satu kali</b> pada baris data di tabel bawah yang ingin Anda perbaiki.<br>"
                + "2. Pilihan nama pelanggan, nomor plat mobil, dan isian lainnya akan <b>otomatis muncul kembali</b> di kotak-kotak atas sesuai baris yang Anda klik.<br>"
                + "3. Silakan ganti pilihan atau ketikan Anda pada bagian yang salah (misalnya salah mengisi Lama Sewa, tanggal, atau salah memilih mobil).<br>"
                + "4. Jika sudah selesai memperbaiki, klik tombol <b>\"Ubah Transaksi\"</b> untuk menyimpan hasil perbaikan tersebut.<br>"
                + "<i>*Catatan: Nomor Nota Transaksi dikunci dan tidak bisa diganti saat Anda memperbaiki data.</i></html>");
        lblLangkah2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLangkah2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kotak2.add(lblLangkah2, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        mainPanel.add(kotak2, gbc);

        // --- KOTAK 3: MENU SISTEM ---
        JPanel kotak3 = new JPanel(new BorderLayout());
        kotak3.setBackground(Color.WHITE);
        kotak3.setBorder(BorderFactory.createTitledBorder(lineBorder, " 3. TOMBOL NAVIGASI LAINNYA ", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12), new Color(0, 102, 204)));
        
        JLabel lblLangkah3 = new JLabel("<html>• <b>'Kembali ke Tampilan Awal'</b>: Klik ini jika ingin menutup jendela kerja yang sedang dibuka dan kembali ke layar utama pembuka.<br>"
                + "• <b>'Keluar'</b>: Klik untuk menutup total seluruh aplikasi rental mobil ini.</html>");
        lblLangkah3.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLangkah3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kotak3.add(lblLangkah3, BorderLayout.CENTER);
        
        gbc.gridy = 2;
        mainPanel.add(kotak3, gbc);

        // --- FOOTER NOTE ---
        JLabel lblFooter = new JLabel("Selamat bekerja! Jika ada kesulitan, silakan hubungi tim IT.", SwingConstants.CENTER);
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblFooter.setForeground(Color.GRAY);
        gbc.gridy = 3;
        gbc.insets = new Insets(15, 15, 15, 15);
        mainPanel.add(lblFooter, gbc);

        // Membungkus mainPanel ke dalam JScrollPane agar aman jika resolusi layar monitor kecil
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null); // Menghapus border ganda pada scrollpane
        
        // Memasang scroll pane ke bagian tengah container
        add(scrollPane, BorderLayout.CENTER);
    }
}