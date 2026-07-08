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

public class FormMobil extends JInternalFrame {
    private JTextField txtNoPlat, txtMerk, txtJenis, txtTahun, txtHarga;
    private JButton btnSimpan, btnUbah, btnHapus; 
    private JTable tabelMobil;
    private DefaultTableModel modelTabel;
    
    // TAMBAHAN: Komponen untuk Fitur Cari
    private JTextField txtCari;
    private JButton btnCari;

    // =======================================================================
    // CONSTRUCTOR: Pengaturan Tampilan Awal Form Master Mobil dengan Background Gambar
    // =======================================================================
    public FormMobil() {
        super("Data Master Mobil", true, true, true, true);
        setSize(700, 560); // Ditambah tingginya sedikit (dari 520 ke 560) agar komponen tidak berdesakan

        // 1. MEMBUAT PANEL UTAMA DENGAN BACKGROUND GAMBAR KUSTOM
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL imgURL = getClass().getResource("/img/backgroundformmobil.png"); 
                
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

        Font labelFont = new Font("SansSerif", Font.BOLD, 12);
        Font globalFont = new Font("SansSerif", Font.PLAIN, 12);

        // 2. MEMBUAT PANEL INPUT DATA (Semi-Transparan)
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 8, 8));
        panelInput.setOpaque(false); 
        
        TitledBorder borderInput = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Input Data Armada ");
        borderInput.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderInput.setTitleColor(Color.WHITE);
        panelInput.setBorder(BorderFactory.createCompoundBorder(borderInput, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblNoPlat = new JLabel("No. Plat Mobil:"); lblNoPlat.setForeground(Color.WHITE); lblNoPlat.setFont(labelFont);
        JLabel lblMerk = new JLabel("Merk/Type:"); lblMerk.setForeground(Color.WHITE); lblMerk.setFont(labelFont);
        JLabel lblJenis = new JLabel("Jenis Mobil:"); lblJenis.setForeground(Color.WHITE); lblJenis.setFont(labelFont);
        JLabel lblTahun = new JLabel("Tahun Pembuatan:"); lblTahun.setForeground(Color.WHITE); lblTahun.setFont(labelFont);
        JLabel lblHarga = new JLabel("Harga Sewa / Hari:"); lblHarga.setForeground(Color.WHITE); lblHarga.setFont(labelFont);

        txtNoPlat = new JTextField(); txtNoPlat.setFont(globalFont);
        txtMerk = new JTextField(); txtMerk.setFont(globalFont);
        txtJenis = new JTextField(); txtJenis.setFont(globalFont);
        txtTahun = new JTextField(); txtTahun.setFont(globalFont);
        txtHarga = new JTextField(); txtHarga.setFont(globalFont);

        panelInput.add(lblNoPlat); panelInput.add(txtNoPlat);
        panelInput.add(lblMerk); panelInput.add(txtMerk);
        panelInput.add(lblJenis); panelInput.add(txtJenis);
        panelInput.add(lblTahun); panelInput.add(txtTahun);
        panelInput.add(lblHarga); panelInput.add(txtHarga);
        
        mainPanel.add(panelInput, BorderLayout.NORTH);

        // 3. MEMBUAT PANEL TENGAH (Pencarian + JScrollPane Tabel)
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setOpaque(false);

        // TAMBAHAN: Sub-Panel untuk search bar ditaruh tepat di atas tabel
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCari.setOpaque(false);
        
        JLabel lblCari = new JLabel("Cari Mobil (Plat/Merk): ");
        lblCari.setForeground(Color.WHITE);
        lblCari.setFont(labelFont);
        
        txtCari = new JTextField(18);
        txtCari.setFont(globalFont);
        
        btnCari = createStyledButton("Cari", new Color(52, 152, 219));
        
        panelCari.add(lblCari);
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        
        panelTengah.add(panelCari, BorderLayout.NORTH); // Taruh search bar di atas tabel

        // Bagian Tabel (Kode asli kamu dimasukkan ke panelTengah)
        String[] kolom = {"No Plat", "Merk/Type", "Jenis", "Tahun", "Harga/Hari"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelMobil = new JTable(modelTabel);
        tabelMobil.setFont(globalFont);
        tabelMobil.setRowHeight(25); 
        tabelMobil.setSelectionBackground(new Color(52, 152, 219)); 
        tabelMobil.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabelMobil.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80)); 
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabelMobil);
        TitledBorder borderTabel = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true), " Daftar Armada Mobil ");
        borderTabel.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        borderTabel.setTitleColor(Color.WHITE);
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

        // Load data awal (Kosong artinya memuat seluruh armada)
        loadDataToTable("");

        // =======================================================================
        // ACTION LISTENERS
        // =======================================================================
        
        // TAMBAHAN: Listener untuk tombol cari
        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText().trim();
            loadDataToTable(keyword);
        });

        tabelMobil.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int barisTerpilih = tabelMobil.getSelectedRow();
                if (barisTerpilih != -1) {
                    txtNoPlat.setText(modelTabel.getValueAt(barisTerpilih, 0).toString());
                    txtMerk.setText(modelTabel.getValueAt(barisTerpilih, 1).toString());
                    txtJenis.setText(modelTabel.getValueAt(barisTerpilih, 2).toString());
                    txtTahun.setText(modelTabel.getValueAt(barisTerpilih, 3).toString());
                    txtHarga.setText(modelTabel.getValueAt(barisTerpilih, 4).toString());
                    txtNoPlat.setEditable(false);
                }
            }
        });

        btnSimpan.addActionListener(e -> {
            String nopol = txtNoPlat.getText(); 
            String merk = txtMerk.getText();
            String jenis = txtJenis.getText(); 
            String tahun = txtTahun.getText(); 
            String harga = txtHarga.getText();

            if(nopol.isEmpty() || merk.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Plat dan Merk tidak boleh kosong!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "INSERT INTO mobil VALUES (?, ?, ?, ?, ?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, nopol); 
                p.setString(2, merk); 
                p.setString(3, jenis); 
                p.setString(4, tahun); 
                p.setString(5, harga);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Mobil Berhasil Disimpan!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Simpan: " + ex.getMessage());
            }
        });

        btnUbah.addActionListener(e -> {
            String nopol = txtNoPlat.getText(); 
            String merk = txtMerk.getText();
            String jenis = txtJenis.getText(); 
            String tahun = txtTahun.getText(); 
            String harga = txtHarga.getText();

            if(nopol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silakan pilih data mobil yang ingin diubah pada tabel terlebih dahulu!");
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                String sql = "UPDATE mobil SET merk = ?, jenis = ?, tahun = ?, harga_sewa = ? WHERE no_plat = ?";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, merk); 
                p.setString(2, jenis); 
                p.setString(3, tahun); 
                p.setString(4, harga);
                p.setString(5, nopol);
                
                p.executeUpdate();
                p.close();
                
                JOptionPane.showMessageDialog(this, "Data Mobil Berhasil Diperbarui!");
                bersihForm();
                loadDataToTable("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal Mengubah Data: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            int barisTerpilih = tabelMobil.getSelectedRow();
            if (barisTerpilih == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris pada tabel armada yang akan dihapus!");
                return;
            }
            
            String nopolHapus = modelTabel.getValueAt(barisTerpilih, 0).toString();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus mobil plat " + nopolHapus + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection c = Koneksi.getKoneksi();
                    PreparedStatement p = c.prepareStatement("DELETE FROM mobil WHERE no_plat = ?");
                    p.setString(1, nopolHapus); 
                    
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

    // MODIFIKASI: Method load data sekarang menerima parameter String keyword
    private void loadDataToTable(String keyword) {
        modelTabel.setRowCount(0);
        
        String sql;
        // Jika keyword kosong tampilkan semua, jika ada isinya cari pakai WHERE & LIKE
        if (keyword.isEmpty()) {
            sql = "SELECT * FROM mobil";
        } else {
            sql = "SELECT * FROM mobil WHERE no_plat LIKE ? OR merk LIKE ?";
        }

        try {
            Connection c = Koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement(sql);
            
            // Set parameter LIKE jika user melakukan pencarian
            if (!keyword.isEmpty()) {
                p.setString(1, "%" + keyword + "%");
                p.setString(2, "%" + keyword + "%");
            }
            
            ResultSet r = p.executeQuery();
            
            while (r.next()) {
                Object[] o = {
                    r.getString("no_plat"), 
                    r.getString("merk"), 
                    r.getString("jenis"), 
                    r.getString("tahun"), 
                    r.getInt("harga_sewa")
                };
                modelTabel.addRow(o);
            }
            r.close(); p.close();
        } catch (SQLException e) {
            System.out.println("Gagal load tabel mobil: " + e.getMessage());
        }
    }

    private void bersihForm() {
        txtNoPlat.setText("");
        txtMerk.setText("");
        txtJenis.setText("");
        txtTahun.setText("");
        txtHarga.setText("");
        txtCari.setText(""); // Tambahan: Ikut membersihkan kolom cari
        txtNoPlat.setEditable(true);
    }
}