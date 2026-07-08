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

public class FormSupir extends JInternalFrame {
    private JTextField txtIdSupir, txtNamaSupir, txtHpSupir, txtTarif;
    private JButton btnSimpan, btnUbah, btnHapus; 
    private JTable tabelSupir;
    private DefaultTableModel modelTabel;

    // TAMBAHAN: Komponen baru untuk Fitur Pencarian Supir
    private JTextField txtCari;
    private JButton btnCari;

    // =======================================================================
    // CONSTRUCTOR: Pengaturan Tampilan Awal Form Master Supir dengan Background Gambar
    // =======================================================================
    public FormSupir() {
        super("Data Master Supir", true, true, true, true);
        setSize(700, 560); // Ditambah tingginya dari 520 ke 560 agar Search Bar muat dengan pas

        // 1. MEMBUAT PANEL UTAMA DENGAN BACKGROUND GAMBAR KUSTOM
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL imgURL = getClass().getResource("/img/backgroundformsupir.png"); 
                
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

        // 2. MEMBUAT PANEL INPUT DATA (Semi-Transparan)
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 8, 8));
        panelInput.setOpaque(false); 
        
        TitledBorder borderInput = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Input Data Supir ");
        borderInput.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderInput.setTitleColor(Color.WHITE);
        panelInput.setBorder(BorderFactory.createCompoundBorder(borderInput, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblId = new JLabel("ID Supir:"); lblId.setForeground(Color.WHITE); lblId.setFont(labelFont);
        JLabel lblNama = new JLabel("Nama Supir:"); lblNama.setForeground(Color.WHITE); lblNama.setFont(labelFont);
        JLabel lblHp = new JLabel("No. HP Supir:"); lblHp.setForeground(Color.WHITE); lblHp.setFont(labelFont);
        JLabel lblTarif = new JLabel("Tarif Supir / Hari:"); lblTarif.setForeground(Color.WHITE); lblTarif.setFont(labelFont);

        txtIdSupir = new JTextField(); txtIdSupir.setFont(globalFont);
        txtIdSupir.setEditable(false); 
        txtIdSupir.setBackground(new Color(240, 240, 240)); 
        
        txtNamaSupir = new JTextField(); txtNamaSupir.setFont(globalFont);
        txtHpSupir = new JTextField(); txtHpSupir.setFont(globalFont);
        txtTarif = new JTextField(); txtTarif.setFont(globalFont);

        panelInput.add(lblId); panelInput.add(txtIdSupir);
        panelInput.add(lblNama); panelInput.add(txtNamaSupir);
        panelInput.add(lblHp); panelInput.add(txtHpSupir);
        panelInput.add(lblTarif); panelInput.add(txtTarif);
        
        mainPanel.add(panelInput, BorderLayout.NORTH);

        // 3. MEMBUAT PANEL TENGAH (Pencarian + JScrollPane Tabel)
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setOpaque(false);

        // TAMBAHAN: Sub-Panel untuk bar pencarian supir
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCari.setOpaque(false);

        JLabel lblCari = new JLabel("Cari Supir (ID / Nama): ");
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
        String[] kolom = {"ID Supir", "Nama Supir", "No HP", "Tarif/Hari"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelSupir = new JTable(modelTabel);
        tabelSupir.setFont(globalFont);
        tabelSupir.setRowHeight(25); 
        tabelSupir.setSelectionBackground(new Color(52, 152, 219)); 
        tabelSupir.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabelSupir.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80)); 
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabelSupir);
        TitledBorder borderTabel = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Daftar Supir Aktif ");
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

        btnSimpan = createStyledButton("Simpan", new Color(46, 125, 50)); 
        btnUbah = createStyledButton("Ubah", new Color(230, 126, 34));     
        btnHapus = createStyledButton("Hapus", new Color(192, 41, 43));    

        panelTombol.add(btnSimpan); 
        panelTombol.add(btnUbah);  
        panelTombol.add(btnHapus);
        
        mainPanel.add(panelTombol, BorderLayout.SOUTH);

        // Memuat data awal
        loadDataToTable(""); // Diubah mengirimkan parameter String kosong
        idOtomatis();

        // =======================================================================
        // ACTION LISTENERS
        // =======================================================================

        // TAMBAHAN: Listener untuk tombol Cari Supir
        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText().trim();
            loadDataToTable(keyword);
        });

        // Mouse Listener tabel
        tabelSupir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int barisTerpilih = tabelSupir.getSelectedRow();
                if (barisTerpilih != -1) {
                    txtIdSupir.setText(modelTabel.getValueAt(barisTerpilih, 0).toString());
                    txtNamaSupir.setText(modelTabel.getValueAt(barisTerpilih, 1).toString());
                    
                    Object hpObj = modelTabel.getValueAt(barisTerpilih, 2);
                    txtHpSupir.setText(hpObj != null ? hpObj.toString() : "");
                    
                    Object tarifObj = modelTabel.getValueAt(barisTerpilih, 3);
                    txtTarif.setText(tarifObj != null ? tarifObj.toString() : "");
                    
                    txtIdSupir.setEditable(false);
                }
            }
        });

        // Action Listener Simpan
        btnSimpan.addActionListener(e -> {
            String id = txtIdSupir.getText(); 
            String nama = txtNamaSupir.getText();
            String hp = txtHpSupir.getText(); 
            String tarif = txtTarif.getText();

            if(id.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Nama Supir wajib diisi!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "INSERT INTO supir VALUES (?, ?, ?, ?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, id); 
                p.setString(2, nama); 
                p.setString(3, hp); 
                p.setString(4, tarif);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Supir Berhasil Disimpan!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Simpan: " + ex.getMessage());
            }
        });

        // Action Listener Ubah
        btnUbah.addActionListener(e -> {
            String id = txtIdSupir.getText(); 
            String nama = txtNamaSupir.getText();
            String hp = txtHpSupir.getText(); 
            String tarif = txtTarif.getText();

            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan pilih data supir yang ingin diubah pada tabel terlebih dahulu!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "UPDATE supir SET nama_supir = ?, no_hp = ?, tarif_supir = ? WHERE id_supir = ?";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, nama); 
                p.setString(2, hp); 
                p.setString(3, tarif);
                p.setString(4, id);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Supir Berhasil Diperbarui!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Mengubah Data: " + ex.getMessage());
            }
        });

        // Action Listener Hapus
        btnHapus.addActionListener(e -> {
            int barisTerpilih = tabelSupir.getSelectedRow();
            if (barisTerpilih == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris data supir yang akan dihapus!");
                return;
            }
            
            String idHapus = modelTabel.getValueAt(barisTerpilih, 0).toString();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus supir ID " + idHapus + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection c = Koneksi.getKoneksi();
                    PreparedStatement p = c.prepareStatement("DELETE FROM supir WHERE id_supir = ?");
                    p.setString(1, idHapus); 
                    
                    p.executeUpdate();
                    p.close();
                    
                    bersihForm();
                    loadDataToTable("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal Hapus: " + ex.getMessage());
                }
            }
        });
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

    // MODIFIKASI: Ditambahkan parameter keyword untuk filter data menggunakan LIKE
    private void loadDataToTable(String keyword) {
        modelTabel.setRowCount(0);
        
        String sql;
        if (keyword.isEmpty()) {
            sql = "SELECT * FROM supir";
        } else {
            sql = "SELECT * FROM supir WHERE id_supir LIKE ? OR nama_supir LIKE ?";
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
                    r.getString("id_supir"), 
                    r.getString("nama_supir"), 
                    r.getString("no_hp"), 
                    r.getInt("tarif_supir")
                };
                modelTabel.addRow(o);
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal load tabel supir: " + e.getMessage());
        }
    }

    private void bersihForm() {
        txtNamaSupir.setText("");
        txtHpSupir.setText("");
        txtTarif.setText("");
        txtCari.setText(""); // TAMBAHAN: Membersihkan search bar otomatis setelah eksekusi form
        idOtomatis(); 
    }

    private void idOtomatis() {
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT id_supir FROM supir ORDER BY id_supir DESC LIMIT 1");
            ResultSet r = p.executeQuery();
            
            if (r.next()) {
                String idTerakhir = r.getString("id_supir");
                String angkaSaja = idTerakhir.replaceAll("[^0-9]", ""); 
                
                int nomor = 1;
                if (!angkaSaja.isEmpty()) {
                    nomor = Integer.parseInt(angkaSaja) + 1;
                }
                
                String nomorFormat = String.format("%04d", nomor);
                txtIdSupir.setText("SPR" + nomorFormat);
            } else {
                txtIdSupir.setText("SPR0001");
            }
            r.close(); p.close();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Gagal membuat ID otomatis supir: " + e.getMessage());
            txtIdSupir.setText("SPR0001"); 
        }
    }
}