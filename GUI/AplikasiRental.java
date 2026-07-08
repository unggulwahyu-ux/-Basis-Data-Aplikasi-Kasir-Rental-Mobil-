package GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AplikasiRental extends JFrame {
    private JPanel panelWelcome;
    private JLabel lblJudulApp;
    private JButton btnMulai;
    private JMenuBar menuBarUtama;
    private JMenu menuMaster, menuTransaksi, menuAkun;
    private JMenuItem itemPelanggan, itemMobil, itemSupir, itemSewa, itemKeluar, itemKembalikeAwal, itemPetunjuk;
    private JDesktopPane desktopPane;

    // =======================================================================
    // CONSTRUCTOR: Pengaturan Awal Frame Utama Aplikasi
    // =======================================================================
    public AplikasiRental() {
        setTitle("RENTAL MOBIL APP");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Membuat window muncul di tengah layar
        initHalamanWelcome();        // Menampilkan halaman sambutan pertama kali
    }

    // =======================================================================
    // METHOD: Inisialisasi Tampilan Halaman Welcome (Layar Pembuka)
    // =======================================================================
    private void initHalamanWelcome() {
        setJMenuBar(null); // Sembunyikan menu bar saat di halaman welcome
        
        // Membuat panel welcome dengan background gambar kustom
        panelWelcome = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageURL = getClass().getResource("/img/gambarmenuawal.png");
                if (imageURL != null) {
                    ImageIcon icon = new ImageIcon(imageURL);
                    Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(44, 62, 80)); // Warna alternatif jika gambar gagal dimuat
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();

        // Label 1: Judul Utama Aplikasi
        lblJudulApp = new JLabel("SISTEM INFORMASI RENTAL MOBIL KELOMPOK 1");
        lblJudulApp.setFont(new Font("Arial", Font.BOLD, 35));
        lblJudulApp.setForeground(Color.WHITE);
        lblJudulApp.setUI(new javax.swing.plaf.basic.BasicLabelUI() {});
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 200, 0);
        panelWelcome.add(lblJudulApp, gbc);
        
        // Label 2: Informasi Anggota Tim Kelompok
        lblJudulApp = new JLabel("<html><div style = 'text-align: center;'>"
                + "Dibuat Oleh :<br>"
                + "Rendra & Unggul : BACK END <br>"
                + "Febri & Devika : DATABASE <br>"
                + "Fachri : APP DESIGN <br> <HTML>");
        lblJudulApp.setFont(new Font("Arial", Font.BOLD, 25));
        lblJudulApp.setForeground(Color.WHITE);
        lblJudulApp.setUI(new javax.swing.plaf.basic.BasicLabelUI() {});
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(100, 0, 30, 0);
        panelWelcome.add(lblJudulApp, gbc);
        
        // Tombol untuk masuk ke aplikasi utama
        btnMulai = new JButton("MULAI APLIKASI");
        btnMulai.setFont(new Font("Arial", Font.BOLD, 16));
        btnMulai.setPreferredSize(new Dimension(200, 50));
        btnMulai.setBackground(new Color(46, 204, 113)); 
        btnMulai.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panelWelcome.add(btnMulai, gbc);

        setContentPane(panelWelcome);
  
        // Aksi ketika tombol "MULAI APLIKASI" diklik
        btnMulai.addActionListener(e -> {
            initMenuUtama();      // Pindah ke halaman menu utama
            revalidate();         // Segarkan susunan komponen layout
            repaint();            // Gambar ulang tampilan layar
        });
    }
    
    // =======================================================================
    // METHOD: Inisialisasi Tampilan Dashboard / Menu Utama Aplikasi
    // =======================================================================
    private void initMenuUtama() {
        // Membuat wadah internal frame (JDesktopPane) dengan background gambar mobil
        desktopPane = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageURL = getClass().getResource("/img/gambarmenu2.png");
                if (imageURL != null) {
                    ImageIcon icon = new ImageIcon(imageURL);
                    Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(desktopPane);

        // Membuat container Menu Bar di bagian atas frame
        menuBarUtama = new JMenuBar();

        // Menu 1: Grup Data Master (Input data awal)
        menuMaster = new JMenu("Data Master");
        itemPelanggan = new JMenuItem("Pelanggan");
        itemMobil = new JMenuItem("Mobil");
        itemSupir = new JMenuItem("Supir");
        menuMaster.add(itemPelanggan);
        menuMaster.add(itemMobil);
        menuMaster.add(itemSupir);

        // Menu 2: Grup Transaksi (Proses sewa)
        menuTransaksi = new JMenu("Transaksi");
        itemSewa = new JMenuItem("Sewa Mobil");
        menuTransaksi.add(itemSewa);

        // Menu 3: Grup Sistem (Pengaturan & Navigasi Aplikasi)
        menuAkun = new JMenu("Sistem");
        itemPetunjuk = new JMenuItem("Petunjuk Penggunaan");
        itemKembalikeAwal = new JMenuItem("Kembali ke Tampilan Awal");
        itemKeluar = new JMenuItem("Keluar");
        menuAkun.add(itemPetunjuk);
        menuAkun.add(itemKembalikeAwal);
        menuAkun.add(new JSeparator()); // Garis pembatas horizontal agar rapi
        menuAkun.add(itemKeluar);

        // Pasang semua menu ke menu bar, lalu pasang menu bar ke frame
        menuBarUtama.add(menuMaster);
        menuBarUtama.add(menuTransaksi);
        menuBarUtama.add(menuAkun);
        setJMenuBar(menuBarUtama); 
        
        // Listener untuk membuka Form Transaksi Sewa
        itemSewa.addActionListener(e -> {
        // Validasi: Cek apakah FormSewa sudah terbuka di dalam desktopPane
        boolean sudahTerbuka = false;
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
        if (frame instanceof FormSewa) {
            sudahTerbuka = true;
            try {
                frame.setSelected(true); // Fokuskan ke frame yang sudah ada
                if (frame.isIcon()) frame.setIcon(false); // Maksimalkan jika sedang di-minimize
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
            break;
        }
    }
    
    // Jika belum terbuka, barulah buat objek form yang baru
    if (!sudahTerbuka) {
        FormSewa form = new FormSewa();
        desktopPane.add(form);
        form.setVisible(true);
    }
});

        // =======================================================================
        // NOTIFIKASI: Memunculkan Pop-up Panduan saat Masuk Menu Utama
        // =======================================================================
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                this, 
                "Selamat datang di Aplikasi Rental Mobil! :D\n" +
                "Silakan baca 'Petunjuk Penggunaan' di menu Sistem sebelum memulai.", 
                "Petunjuk Penggunaan", 
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // =======================================================================
        // ACTION LISTENERS: Mengatur Aksi Klik pada Setiap Item Menu Sistem
        // =======================================================================
        
        // Keluar total dari aplikasi
        itemKeluar.addActionListener(e -> System.exit(0));
        
        // Kembali ke Welcome Screen awal
        itemKembalikeAwal.addActionListener(e -> {
            initHalamanWelcome(); 
            revalidate();
            repaint();
        });
        
        // Membuka jendela petunjuk penggunaan di dalam desktopPane
        itemPetunjuk.addActionListener(e -> {
            FormPetunjuk form = new FormPetunjuk();
            desktopPane.add(form);
            form.setVisible(true);
        });
        
        // Membuka jendela pengelolaan data Pelanggan
        itemPelanggan.addActionListener(e -> {
            FormPelanggan form = new FormPelanggan();
            desktopPane.add(form);
            form.setVisible(true);
        });
        
        // Membuka jendela pengelolaan data Mobil
        itemMobil.addActionListener(e -> {
            FormMobil form = new FormMobil();
            desktopPane.add(form);
            form.setVisible(true);
        });
        
        // Membuka jendela pengelolaan data Supir
        itemSupir.addActionListener(e -> {
            FormSupir form = new FormSupir();
            desktopPane.add(form);
            form.setVisible(true);
        });
        
        // Membuka jendela proses Transaksi Sewa (Duplikasi aksi diamankan)
        itemSewa.addActionListener(e -> {
            FormSewa form = new FormSewa();
            desktopPane.add(form);
            form.setVisible(true);
        });
    }

    // =======================================================================
    // MAIN METHOD: Fungsi Utama untuk Menjalankan Program (Entry Point)
    // =======================================================================
    public static void main(String[] args) {
        // Menjalankan UI Swing di Thread yang aman (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new AplikasiRental().setVisible(true);
        });
    }
}