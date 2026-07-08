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

public class FormPelanggan extends JInternalFrame {
    private JTextField txtId, txtNama, txtKtp, txtHp, txtAlamat;
    private JButton btnSimpan, btnUbah, btnHapus; 
    private JTable tabelPelanggan;
    private DefaultTableModel modelTabel;

    // TAMBAHAN: Komponen untuk Fitur Cari Data
    private JTextField txtCari;
    private JButton btnCari;

    // =======================================================================
    // CONSTRUCTOR: Pengaturan Tampilan Awal Form Master Pelanggan dengan Background Gambar
    // =======================================================================
    public FormPelanggan() {
        super("Data Master Pelanggan", true, true, true, true);
        setSize(700, 560); // Ukuran tinggi ditambah sedikit dari 520 ke 560 agar search bar muat

        // 1. MEMBUAT PANEL UTAMA DENGAN BACKGROUND GAMBAR KUSTOM
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL imgURL = getClass().getResource("/img/backgroundformpelanggan.jpg"); 
                
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
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 8, 8));
        panelInput.setOpaque(false); 
        
        TitledBorder borderInput = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Input Data Pelanggan ");
        borderInput.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderInput.setTitleColor(Color.BLACK);
        panelInput.setBorder(BorderFactory.createCompoundBorder(borderInput, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblId = new JLabel("ID Pelanggan:"); lblId.setForeground(Color.BLACK); lblId.setFont(labelFont);
        JLabel lblNama = new JLabel("Nama Lengkap:"); lblNama.setForeground(Color.BLACK); lblNama.setFont(labelFont);
        JLabel lblKtp = new JLabel("No. KTP:"); lblKtp.setForeground(Color.BLACK); lblKtp.setFont(labelFont);
        JLabel lblHp = new JLabel("No. HP:"); lblHp.setForeground(Color.BLACK); lblHp.setFont(labelFont);
        JLabel lblAlamat = new JLabel("Alamat:"); lblAlamat.setForeground(Color.BLACK); lblAlamat.setFont(labelFont);

        txtId = new JTextField(); txtId.setFont(globalFont);
        txtId.setEditable(false); 
        txtId.setBackground(new Color(240, 240, 240)); 
        
        txtNama = new JTextField(); txtNama.setFont(globalFont);
        txtKtp = new JTextField(); txtKtp.setFont(globalFont);
        txtHp = new JTextField(); txtHp.setFont(globalFont);
        txtAlamat = new JTextField(); txtAlamat.setFont(globalFont);

        panelInput.add(lblId); panelInput.add(txtId);
        panelInput.add(lblNama); panelInput.add(txtNama);
        panelInput.add(lblKtp); panelInput.add(txtKtp);
        panelInput.add(lblHp); panelInput.add(txtHp);
        panelInput.add(lblAlamat); panelInput.add(txtAlamat);
        
        mainPanel.add(panelInput, BorderLayout.NORTH);

        // 3. MEMBUAT PANEL TENGAH (Pencarian + JScrollPane Tabel)
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setOpaque(false);

        // TAMBAHAN: Sub-Panel untuk Search Bar ditaruh tepat di atas tabel
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCari.setOpaque(false);

        JLabel lblCari = new JLabel("Cari Pelanggan (Nama/Alamat): ");
        lblCari.setForeground(Color.BLACK); // Menyesuaikan dengan tema teks hitammu
        lblCari.setFont(labelFont);

        txtCari = new JTextField(18);
        txtCari.setFont(globalFont);

        btnCari = createStyledButton("Cari", new Color(52, 152, 219));

        panelCari.add(lblCari);
        panelCari.add(txtCari);
        panelCari.add(btnCari);

        panelTengah.add(panelCari, BorderLayout.NORTH); // Taruh search bar di utara tabel

        // Bagian Tabel
        String[] kolom = {"ID", "Nama", "No. KTP", "No. HP", "Alamat"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelPelanggan = new JTable(modelTabel);
        tabelPelanggan.setFont(globalFont);
        tabelPelanggan.setRowHeight(25); 
        tabelPelanggan.setSelectionBackground(new Color(52, 152, 219)); 
        tabelPelanggan.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabelPelanggan.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80)); 
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabelPelanggan);
        TitledBorder borderTabel = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Daftar Pelanggan Terdaftar ");
        borderTabel.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderTabel.setTitleColor(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(borderTabel, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        
        panelTengah.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelTengah, BorderLayout.CENTER); // Memasukkan panel gabungan ke layout utama

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

        // Memuat data awal & ID Otomatis
        loadDataToTable(""); // Diubah mengirimkan String kosong untuk load awal
        idOtomatis();

        // =======================================================================
        // ACTION LISTENERS
        // =======================================================================

        // TAMBAHAN: Listener untuk tombol cari
        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText().trim();
            loadDataToTable(keyword);
        });

        // Mouse Listener tabel
        tabelPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int barisTerpilih = tabelPelanggan.getSelectedRow();
                if (barisTerpilih != -1) {
                    txtId.setText(modelTabel.getValueAt(barisTerpilih, 0).toString());
                    txtNama.setText(modelTabel.getValueAt(barisTerpilih, 1).toString());
                    txtKtp.setText(modelTabel.getValueAt(barisTerpilih, 2).toString());
                    txtHp.setText(modelTabel.getValueAt(barisTerpilih, 3).toString());
                    txtAlamat.setText(modelTabel.getValueAt(barisTerpilih, 4).toString());
                    txtId.setEditable(false);
                }
            }
        });

        // Action Listener Simpan
        btnSimpan.addActionListener(e -> {
            String id = txtId.getText(); 
            String nama = txtNama.getText();
            String ktp = txtKtp.getText(); 
            String hp = txtHp.getText(); 
            String alamat = txtAlamat.getText();

            if(id.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Nama tidak boleh kosong!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "INSERT INTO pelanggan VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, id); 
                p.setString(2, nama); 
                p.setString(3, ktp); 
                p.setString(4, hp); 
                p.setString(5, alamat);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Pelanggan Berhasil Disimpan!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Simpan: " + ex.getMessage());
            }
        });

        // Action Listener Ubah
        btnUbah.addActionListener(e -> {
            String id = txtId.getText(); 
            String nama = txtNama.getText();
            String ktp = txtKtp.getText(); 
            String hp = txtHp.getText(); 
            String alamat = txtAlamat.getText();

            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan pilih data pelanggan yang ingin diubah pada tabel terlebih dahulu!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "UPDATE pelanggan SET nama_pelanggan = ?, no_ktp = ?, no_hp = ?, alamat = ? WHERE id_pelanggan = ?";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, nama); 
                p.setString(2, ktp); 
                p.setString(3, hp); 
                p.setString(4, alamat);
                p.setString(5, id);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Pelanggan Berhasil Diperbarui!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Mengubah Data: " + ex.getMessage());
            }
        });

        // Action Listener Hapus
        btnHapus.addActionListener(e -> {
            int barisTerpilih = tabelPelanggan.getSelectedRow();
            if (barisTerpilih == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris data pada tabel yang ingin dihapus!");
                return;
            }
            
            String idHapus = modelTabel.getValueAt(barisTerpilih, 0).toString();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data ID " + idHapus + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection c = Koneksi.getKoneksi();
                    PreparedStatement p = c.prepareStatement("DELETE FROM pelanggan WHERE id_pelanggan = ?");
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

    // MODIFIKASI: Ditambahkan parameter String keyword untuk query SELECT + LIKE
    private void loadDataToTable(String keyword) {
        modelTabel.setRowCount(0);
        
        String sql;
        if (keyword.isEmpty()) {
            sql = "SELECT * FROM pelanggan";
        } else {
            // Menggunakan nama_pelanggan sesuai struktur query UPDATE bawaanmu
            sql = "SELECT * FROM pelanggan WHERE nama_pelanggan LIKE ? OR alamat LIKE ?";
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
                    r.getString("id_pelanggan"), 
                    r.getString("nama_pelanggan"), 
                    r.getString("no_ktp"), 
                    r.getString("no_hp"), 
                    r.getString("alamat")
                };
                modelTabel.addRow(o);
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal load tabel pelanggan: " + e.getMessage());
        }
    }

    private void bersihForm() {
        txtNama.setText("");
        txtKtp.setText("");
        txtHp.setText("");
        txtAlamat.setText("");
        txtCari.setText(""); // TAMBAHAN: Kolom pencarian ikut bersih setelah aksi CRUD selesai
        idOtomatis(); 
    }

    private void idOtomatis() {
        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement("SELECT id_pelanggan FROM pelanggan ORDER BY id_pelanggan DESC LIMIT 1");
            ResultSet r = p.executeQuery();
            
            if (r.next()) {
                String idTerakhir = r.getString("id_pelanggan"); 
                String angkaSaja = idTerakhir.replaceAll("[^0-9]", ""); 
                
                int nomor = 1; 
                if (!angkaSaja.isEmpty()) {
                    nomor = Integer.parseInt(angkaSaja) + 1;
                }
                
                String nomorFormat = String.format("%04d", nomor);
                txtId.setText("PLG" + nomorFormat);
            } else {
                txtId.setText("PLG0001");
            }
            r.close(); p.close();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Gagal membuat ID otomatis: " + e.getMessage());
            txtId.setText("PLG0001"); 
        }
    }
}