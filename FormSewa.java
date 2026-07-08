package GUI;

import KoneksiKeDB.Koneksi;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormSewa extends JInternalFrame {
    private JTextField txtIdSewa, txtTglSewa, txtLamaSewa, txtTotal;
    private JComboBox<String> cbIdPelanggan, cbNoPlat, cbIdSupir; 
    private JButton btnSimpan, btnUbah, btnHapus; 
    private JTable tabelSewa;
    private DefaultTableModel modelTabel;

    // TAMBAHAN: Komponen baru untuk Fitur Pencarian Transaksi
    private JTextField txtCari;
    private JButton btnCari;

    // =======================================================================
    // CONSTRUCTOR: Pengaturan Tampilan Awal Form Transaksi Sewa Mobil
    // =======================================================================
    public FormSewa() {
        super("Transaksi & Riwayat Rental Mobil", true, true, true, true);
        setSize(800, 640);

        // 1. MEMBUAT PANEL UTAMA DENGAN BACKGROUND GAMBAR KUSTOM
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL imgURL = getClass().getResource("/img/backgroundformsewa.png"); 
                
                if (imgURL != null) {
                    ImageIcon imageIcon = new ImageIcon(imgURL);
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(41, 128, 185)); 
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        setContentPane(mainPanel);

        // Font Kustom
        Font labelFont = new Font("SansSerif", Font.BOLD, 12);
        Font globalFont = new Font("SansSerif", Font.PLAIN, 12);

        // 2. MEMBUAT PANEL INPUT DATA TRANSAKSI (Semi-Transparan)
        JPanel panelInput = new JPanel(new GridLayout(7, 2, 8, 8));
        panelInput.setOpaque(false); 
        
        TitledBorder borderInput = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Input Transaksi Baru ");
        borderInput.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderInput.setTitleColor(Color.WHITE);
        panelInput.setBorder(BorderFactory.createCompoundBorder(borderInput, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblIdSewa = new JLabel("Kode Transaksi Sewa:"); lblIdSewa.setForeground(Color.WHITE); lblIdSewa.setFont(labelFont);
        JLabel lblIdPel = new JLabel("Pilih Pelanggan:"); lblIdPel.setForeground(Color.WHITE); lblIdPel.setFont(labelFont);
        JLabel lblNoPlat = new JLabel("Pilih Mobil (No. Plat):"); lblNoPlat.setForeground(Color.WHITE); lblNoPlat.setFont(labelFont);
        JLabel lblIdSupir = new JLabel("Pilih Supir (Kosongkan jika tanpa supir):"); lblIdSupir.setForeground(Color.WHITE); lblIdSupir.setFont(labelFont);
        JLabel lblTgl = new JLabel("Tanggal Sewa (YYYY-MM-DD):"); lblTgl.setForeground(Color.WHITE); lblTgl.setFont(labelFont);
        JLabel lblLama = new JLabel("Lama Sewa (Hari):"); lblLama.setForeground(Color.WHITE); lblLama.setFont(labelFont);
        JLabel lblTotal = new JLabel("Total Biaya (Rp):"); lblTotal.setForeground(Color.WHITE); lblTotal.setFont(labelFont);

        txtIdSewa = new JTextField(); txtIdSewa.setFont(globalFont);
        txtTglSewa = new JTextField(); txtTglSewa.setFont(globalFont);
        txtLamaSewa = new JTextField(); txtLamaSewa.setFont(globalFont);
        txtTotal = new JTextField(); txtTotal.setFont(globalFont);
        txtTotal.setEditable(false); // Total tidak bisa diubah manual
        txtTotal.setBackground(new Color(240, 240, 240));

        cbIdPelanggan = new JComboBox<>(); cbIdPelanggan.setFont(globalFont);
        cbNoPlat = new JComboBox<>(); cbNoPlat.setFont(globalFont);
        cbIdSupir = new JComboBox<>(); cbIdSupir.setFont(globalFont);

        panelInput.add(lblIdSewa); panelInput.add(txtIdSewa);
        panelInput.add(lblIdPel); panelInput.add(cbIdPelanggan);
        panelInput.add(lblNoPlat); panelInput.add(cbNoPlat);
        panelInput.add(lblIdSupir); panelInput.add(cbIdSupir);
        panelInput.add(lblTgl); panelInput.add(txtTglSewa);
        panelInput.add(lblLama); panelInput.add(txtLamaSewa);
        panelInput.add(lblTotal); panelInput.add(txtTotal);
        
        mainPanel.add(panelInput, BorderLayout.NORTH);

        // 3. MEMBUAT PANEL TENGAH (Pencarian + JScrollPane Log Tabel)
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setOpaque(false);

        // TAMBAHAN: Sub-Panel untuk bar pencarian riwayat transaksi sewa
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCari.setOpaque(false);

        JLabel lblCari = new JLabel("Cari Transaksi (Kode/ID Pelanggan): ");
        lblCari.setForeground(Color.WHITE);
        lblCari.setFont(labelFont);

        txtCari = new JTextField(20);
        txtCari.setFont(globalFont);

        btnCari = createStyledButton("Cari", new Color(52, 152, 219));

        panelCari.add(lblCari);
        panelCari.add(txtCari);
        panelCari.add(btnCari);

        panelTengah.add(panelCari, BorderLayout.NORTH);

        // Bagian Konstruksi Tabel
        String[] kolom = {"Kode", "ID Pelanggan", "No Plat", "ID Supir", "Tgl Sewa", "Lama (Hari)", "Total"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelSewa = new JTable(modelTabel);
        tabelSewa.setFont(globalFont);
        tabelSewa.setRowHeight(25);
        tabelSewa.setSelectionBackground(new Color(52, 152, 219));
        tabelSewa.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabelSewa.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabelSewa);
        TitledBorder borderTabel = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Log / Jurnal Transaksi Penyewaan ");
        borderTabel.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderTabel.setTitleColor(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(borderTabel, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        panelTengah.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelTengah, BorderLayout.CENTER);

        // 4. MEMBUAT PANEL TOMBOL AKSI (Bagian Bawah)
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelTombol.setOpaque(false);

        btnSimpan = createStyledButton("Simpan & Rekam Transaksi", new Color(46, 125, 50)); 
        btnUbah = createStyledButton("Ubah Transaksi", new Color(230, 126, 34));           
        btnHapus = createStyledButton("Hapus Transaksi", new Color(192, 41, 43));          

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        
        mainPanel.add(panelTombol, BorderLayout.SOUTH);

        // Memuat semua data awal
        tampilPilihanPelanggan();
        tampilPilihanMobil();
        tampilPilihanSupir();
        loadDataToTable("");
        idOtomatis();

        // =======================================================================
        // ACTION LISTENERS
        // =======================================================================

        // TAMBAHAN: Listener untuk menghitung total secara otomatis
        // Ketika pilihan mobil berubah
        cbNoPlat.addActionListener(e -> hitungTotalOtomatis());
        
        // Ketika pilihan supir berubah
        cbIdSupir.addActionListener(e -> hitungTotalOtomatis());
        
        // Ketika lama sewa berubah
        txtLamaSewa.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungTotalOtomatis();
            }
        });

        // TAMBAHAN: Listener untuk tombol Cari Transaksi
        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText().trim();
            loadDataToTable(keyword);
        });

        // Mouse Listener tabel
        tabelSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int barisTerpilih = tabelSewa.getSelectedRow();
                if (barisTerpilih != -1) {
                    txtIdSewa.setText(modelTabel.getValueAt(barisTerpilih, 0).toString());
                    
                    String idPelTabel = modelTabel.getValueAt(barisTerpilih, 1).toString();
                    for (int i = 0; i < cbIdPelanggan.getItemCount(); i++) {
                        if (cbIdPelanggan.getItemAt(i).startsWith(idPelTabel)) {
                            cbIdPelanggan.setSelectedIndex(i);
                            break;
                        }
                    }

                    String noPlatTabel = modelTabel.getValueAt(barisTerpilih, 2).toString().trim();
                    for (int i = 0; i < cbNoPlat.getItemCount(); i++) {
                        if (cbNoPlat.getItemAt(i).startsWith(noPlatTabel)) {
                            cbNoPlat.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    Object supirObj = modelTabel.getValueAt(barisTerpilih, 3);
                    if(supirObj != null && !supirObj.toString().isEmpty()) {
                        String idSupirTabel = supirObj.toString();
                        for (int i = 0; i < cbIdSupir.getItemCount(); i++) {
                            if (cbIdSupir.getItemAt(i).startsWith(idSupirTabel)) {
                                cbIdSupir.setSelectedIndex(i);
                                break;
                            }
                        }
                    } else {
                        cbIdSupir.setSelectedIndex(0); 
                    }
                    
                    txtTglSewa.setText(modelTabel.getValueAt(barisTerpilih, 4).toString());
                    txtLamaSewa.setText(modelTabel.getValueAt(barisTerpilih, 5).toString());
                    txtTotal.setText(modelTabel.getValueAt(barisTerpilih, 6).toString());
                    
                    txtIdSewa.setEditable(false);
                }
            }
        });

        // Action Listener Simpan
        btnSimpan.addActionListener(e -> {
            String idSewa = txtIdSewa.getText(); 
            String idPel = cbIdPelanggan.getSelectedItem().toString().split(" - ")[0].trim(); 
            String nopol = cbNoPlat.getSelectedItem().toString().split(" - ")[0].trim();
            String idSup = cbIdSupir.getSelectedItem().toString().split(" - ")[0].trim(); 
            String tgl = txtTglSewa.getText(); 
            String lama = txtLamaSewa.getText(); 
            String total = txtTotal.getText();

            if(cbIdPelanggan.getSelectedIndex() == 0 || cbNoPlat.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Silakan pilih Pelanggan dan Mobil terlebih dahulu!");
                return;
            }
            
            if(tgl.isEmpty() || lama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal dan Lama Sewa wajib diisi!");
                return;
            }
            
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "INSERT INTO sewa VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, idSewa); 
                p.setString(2, idPel); 
                p.setString(3, nopol);
                
                if (cbIdSupir.getSelectedIndex() == 0) p.setNull(4, java.sql.Types.VARCHAR);
                else p.setString(4, idSup);
                
                p.setString(5, tgl); 
                p.setString(6, lama); 
                p.setString(7, total);

                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Transaksi Penyewaan Berhasil Direkam!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Simpan Transaksi: " + ex.getMessage());
            }
        });

        // Action Listener Ubah
        btnUbah.addActionListener(e -> {
            String idSewa = txtIdSewa.getText(); 
            String idPel = cbIdPelanggan.getSelectedItem().toString().split(" - ")[0].trim();
            String nopol = cbNoPlat.getSelectedItem().toString().split(" - ")[0].trim();
            String idSup = cbIdSupir.getSelectedItem().toString().split(" - ")[0].trim(); 
            String tgl = txtTglSewa.getText(); 
            String lama = txtLamaSewa.getText(); 
            String total = txtTotal.getText();

            if(idSewa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih data transaksi sewa di tabel yang ingin diubah!");
                return;
            }
            
            if(tgl.isEmpty() || lama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal dan Lama Sewa wajib diisi!");
                return;
            }
            
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "UPDATE sewa SET id_pelanggan = ?, no_plat = ?, id_supir = ?, tgl_sewa = ?, lama_sewa = ?, total_biaya = ? WHERE id_sewa = ?";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, idPel);
                p.setString(2, nopol);
                
                if (cbIdSupir.getSelectedIndex() == 0) p.setNull(3, java.sql.Types.VARCHAR);
                else p.setString(3, idSup);
                
                p.setString(4, tgl);
                p.setString(5, lama);
                p.setString(6, total);
                p.setString(7, idSewa);

                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Transaksi Berhasil Diperbarui!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Mengubah Transaksi: " + ex.getMessage());
            }
        });

        // Action Listener Hapus
        btnHapus.addActionListener(e -> {
            int barisTerpilih = tabelSewa.getSelectedRow();
            if (barisTerpilih == -1) {
                JOptionPane.showMessageDialog(this, "Pilih salah satu baris transaksi pada tabel yang ingin dihapus!");
                return;
            }
            
            String idHapus = modelTabel.getValueAt(barisTerpilih, 0).toString();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus riwayat Transaksi Kode: " + idHapus + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection c = Koneksi.getKoneksi();
                    PreparedStatement p = c.prepareStatement("DELETE FROM sewa WHERE id_sewa = ?");
                    p.setString(1, idHapus);
                    p.executeUpdate();
                    p.close();
                    
                    JOptionPane.showMessageDialog(this, "Transaksi Berhasil Dihapus!");
                    bersihForm();
                    loadDataToTable("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal Hapus Transaksi: " + ex.getMessage());
                }
            }
        });
    }

    // =======================================================================
    // METHOD: Menghitung Total Biaya Secara Otomatis
    // =======================================================================
    private void hitungTotalOtomatis() {
        try {
            // Ambil nilai lama sewa
            String lamaText = txtLamaSewa.getText().trim();
            if (lamaText.isEmpty()) {
                txtTotal.setText("");
                return;
            }
            
            int lama = Integer.parseInt(lamaText);
            if (lama <= 0) {
                txtTotal.setText("");
                return;
            }
            
            // Ambil harga sewa mobil dari database berdasarkan no_plat yang dipilih
            String selectedMobil = cbNoPlat.getSelectedItem().toString();
            if (selectedMobil.equals("- Pilih Mobil -")) {
                txtTotal.setText("");
                return;
            }
            
            String noPlat = selectedMobil.split(" - ")[0].trim();
            
            Connection c = Koneksi.getKoneksi();
            
            // Ambil harga sewa mobil
            int hargaMobil = 0;
            PreparedStatement pMobil = c.prepareStatement("SELECT harga_sewa FROM mobil WHERE no_plat = ?");
            pMobil.setString(1, noPlat);
            ResultSet rMobil = pMobil.executeQuery();
            if (rMobil.next()) {
                hargaMobil = rMobil.getInt("harga_sewa");
            }
            rMobil.close();
            pMobil.close();
            
            // Ambil tarif supir jika dipilih
            int tarifSupir = 0;
            String selectedSupir = cbIdSupir.getSelectedItem().toString();
            if (!selectedSupir.equals("- Tanpa Supir -")) {
                String idSupir = selectedSupir.split(" - ")[0].trim();
                PreparedStatement pSupir = c.prepareStatement("SELECT tarif_supir FROM supir WHERE id_supir = ?");
                pSupir.setString(1, idSupir);
                ResultSet rSupir = pSupir.executeQuery();
                if (rSupir.next()) {
                    tarifSupir = rSupir.getInt("tarif_supir");
                }
                rSupir.close();
                pSupir.close();
            }
            
            // Hitung total biaya
            int total = (hargaMobil + tarifSupir) * lama;
            txtTotal.setText(String.valueOf(total));
            
        } catch (NumberFormatException ex) {
            // Jika lama sewa bukan angka, kosongkan total
            txtTotal.setText("");
        } catch (SQLException ex) {
            System.out.println("Gagal menghitung total: " + ex.getMessage());
            txtTotal.setText("");
        }
    }

    private void tampilPilihanPelanggan() {
        cbIdPelanggan.removeAllItems();
        cbIdPelanggan.addItem("- Pilih Pelanggan -");
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT id_pelanggan, nama_pelanggan FROM pelanggan");
            ResultSet r = p.executeQuery();
            while(r.next()) {
                cbIdPelanggan.addItem(r.getString("id_pelanggan") + " - " + r.getString("nama_pelanggan"));
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal memuat list pelanggan: " + e.getMessage());
        }
    }

    private void tampilPilihanMobil() {
        cbNoPlat.removeAllItems();
        cbNoPlat.addItem("- Pilih Mobil -");
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT no_plat, merk FROM mobil"); 
            ResultSet r = p.executeQuery();
            while(r.next()) {
                cbNoPlat.addItem(r.getString("no_plat") + " - " + r.getString("merk"));
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal memuat list mobil: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memuat list mobil: " + e.getMessage());
        }
    }

    private void tampilPilihanSupir() {
        cbIdSupir.removeAllItems();
        cbIdSupir.addItem("- Tanpa Supir -");
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT id_supir, nama_supir FROM supir");
            ResultSet r = p.executeQuery();
            while(r.next()) {
                cbIdSupir.addItem(r.getString("id_supir") + " - " + r.getString("nama_supir"));
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal memuat list supir: " + e.getMessage());
        }
    }

    // MODIFIKASI: Ditambahkan parameter keyword untuk filter data menggunakan LIKE
    private void loadDataToTable(String keyword) {
        modelTabel.setRowCount(0);
        
        String sql;
        if (keyword.isEmpty()) {
            sql = "SELECT * FROM sewa";
        } else {
            sql = "SELECT * FROM sewa WHERE id_sewa LIKE ? OR id_pelanggan LIKE ?";
        }

        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement(sql);
            
            if (!keyword.isEmpty()) {
                p.setString(1, "%" + keyword + "%");
                p.setString(2, "%" + keyword + "%");
            }

            ResultSet r = p.executeQuery();
            while (r.next()) {
                Object[] o = {
                    r.getString("id_sewa"), 
                    r.getString("id_pelanggan"), 
                    r.getString("no_plat"), 
                    r.getString("id_supir"), 
                    r.getDate("tgl_sewa"), 
                    r.getInt("lama_sewa"), 
                    r.getInt("total_biaya")
                };
                modelTabel.addRow(o);
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal load tabel sewa: " + e.getMessage());
        }
    }

    private void idOtomatis() {
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT id_sewa FROM sewa ORDER BY id_sewa DESC LIMIT 1");
            ResultSet r = p.executeQuery();
            if (r.next()) {
                String idTerakhir = r.getString("id_sewa");
                String angkaSaja = idTerakhir.replaceAll("[^0-9]", "");
                int nomor = 1;
                if (!angkaSaja.isEmpty()) {
                    nomor = Integer.parseInt(angkaSaja) + 1;
                }
                txtIdSewa.setText("TRX" + String.format("%04d", nomor));
            } else {
                txtIdSewa.setText("TRX0001");
            }
            txtIdSewa.setEditable(false);
            txtIdSewa.setBackground(new Color(240, 240, 240));
            r.close(); p.close();
        } catch (Exception e) {
            txtIdSewa.setText("TRX0001");
        }
    }

    private void bersihForm() {
        idOtomatis();
        cbIdPelanggan.setSelectedIndex(0);
        cbNoPlat.setSelectedIndex(0);
        cbIdSupir.setSelectedIndex(0);
        txtTglSewa.setText("");
        txtLamaSewa.setText("");
        txtTotal.setText("");
        txtCari.setText("");
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}