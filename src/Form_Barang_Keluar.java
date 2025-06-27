/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.*;
import DatabaseConnection.DatabaseConnection;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JFrame;
/**
 *
 * @author USER
 */
public class Form_Barang_Keluar extends javax.swing.JFrame {

    /**
     * Creates new form Form_Barang_Keluar
     */
    public Form_Barang_Keluar() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tampilkanDataKeluar();
        loadTotalData();
        setupComboBoxListener();
        setupKeluarFieldListener();
        jTable1.setDefaultEditor(Object.class, null);
        
        jTable1.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                Object value = jTable1.getValueAt(selectedRow, 0);
            }
        }
        });
        
        //kode untuk combo box
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
        new String[] { "Pilihan 1", "Pilihan 2", "Pilihan 3" }
        ));

        // Contoh 2: Mengisi ComboBox dari Database (MySQL)
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_pabrik_helm", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT kode_barang FROM data_barang");

            // Buat model kosong terlebih dahulu
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Tambahkan data dari ResultSet ke model
            while (rs.next()) {
                model.addElement(rs.getString("kode_barang"));
            }

            // Set model ke jComboBox1
            jComboBox1.setModel(model);
            jComboBox1.setSelectedIndex(-1);

            // Tutup koneksi
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            }
    }
    
    private void setupComboBoxListener() {
    jComboBox1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedKodeBarang = (String) jComboBox1.getSelectedItem();
            if (selectedKodeBarang != null && !selectedKodeBarang.isEmpty()) {
                isiFieldOtomatis(selectedKodeBarang);
            }
        }
    });
    }
    

    // Method untuk mengisi field otomatis
    private void isiFieldOtomatis(String kodeBarang) {
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT nama_barang, kategori, satuan, harga, stok FROM data_barang WHERE kode_barang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeBarang);
            ResultSet rs = stmt.executeQuery();
        
            if (rs.next()) {
                // Isi field dengan data dari database
                jTextField3.setText(rs.getString("nama_barang"));
                jTextField4.setText(rs.getString("kategori"));
                jTextField5.setText(rs.getString("satuan"));
                jTextField7.setText(rs.getString("harga"));
                jTextField6.setText(rs.getString("stok"));
            
                // Reset field keluar dan perhitungan
                jTextField15.setText("");
                jTextField14.setText("");
                jTextField16.setText("");
            } else {
                // Kosongkan field jika tidak ditemukan
            
                JOptionPane.showMessageDialog(this, "Data barang tidak ditemukan.");
            }
        
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error mengambil data barang: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void tampilkanDataKeluar(){
        try{
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT kode_transaksi, tanggal, kode_barang, nama_barang, kategori, stok, satuan, harga, total_keluar, total_harga, total_stok FROM barang_keluar";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kode Transaksi");
            model.addColumn("Tanggal");
            model.addColumn("Kode Barang");
            model.addColumn("Nama Barang");
            model.addColumn("Kategori");
            model.addColumn("Stok");
            model.addColumn("Satuan");
            model.addColumn("Harga");
            model.addColumn("Total Keluar");
            model.addColumn("Total Harga");
            model.addColumn("Total Stok");
            
            while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_transaksi"),
                rs.getString("tanggal"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("kategori"),
                rs.getInt("stok"),
                rs.getString("satuan"),
                rs.getDouble("harga"),
                rs.getInt("total_keluar"),
                rs.getDouble("total_harga"),
                rs.getInt("total_stok"),
                });
            }
            jTable1.setModel(model);
            jTable1.clearSelection();
            jTextField9.setText("");
            jTextField10.setText("");
            jTextField11.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private int TotalData(){
        int total = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.connect();
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) AS total FROM barang_keluar";
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    
    private void loadTotalData(){
        int total = TotalData();
        jTextField8.setText(String.valueOf(total));
    }
    
    private void bersihkan(){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jComboBox1.setSelectedIndex(-1);
        
        jTable1.clearSelection();
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
    }
    
    private String generateKodeTransaksi() {
    try {
        Connection conn = DatabaseConnection.connect();
        // Cek jumlah data yang ada
        String countSql = "SELECT COUNT(*) AS total FROM barang_keluar";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(countSql);
        
        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }
        
        // Jika tidak ada data, mulai dari KLR001
        if (total == 0) {
            return "KLR001";
        } else {
            // Ambil kode terakhir dari database
            String lastCodeSql = "SELECT kode_transaksi FROM barang_keluar ORDER BY kode_transaksi DESC LIMIT 1";
            ResultSet lastCodeRs = stmt.executeQuery(lastCodeSql);
            
            if (lastCodeRs.next()) {
                String lastCode = lastCodeRs.getString("kode_transaksi");
                // Ekstrak angka dari kode terakhir
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                // Format dengan leading zeros
                return String.format("KLR%03d", lastNumber + 1);
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error generate kode transaksi: " + e.getMessage());
        e.printStackTrace();
    }
    
    return "KLR001"; // fallback
    }
    
    private void updateStokBarangKeluar(String kodeBarang, int total_keluar) {
    try (Connection conn = DatabaseConnection.connect()) {
        // Ambil stok saat ini dari data_barang
        String sqlSelect = "SELECT stok FROM data_barang WHERE kode_barang = ?";
        PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
        stmtSelect.setString(1, kodeBarang);
        ResultSet rs = stmtSelect.executeQuery();
        
        int stok = 0;
        if (rs.next()) {
            stok = rs.getInt("stok");
        }
        
        // Validasi stok cukup
        if (total_keluar > stok) {
            JOptionPane.showMessageDialog(this, "Stok tidak mencukupi! Stok tersedia: " + stok);
            return;
        }
        
        // Hitung total stok baru
        int total_stok = stok - total_keluar;
        
        // Update stok di data_barang
        String sqlUpdate = "UPDATE data_barang SET stok = ? WHERE kode_barang = ?";
        PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
        stmtUpdate.setInt(1, total_stok);
        stmtUpdate.setString(2, kodeBarang);
        stmtUpdate.executeUpdate();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error update stok: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private void calculateTotalStok() {
    if (!jTextField6.getText().isEmpty() && !jTextField15.getText().isEmpty()) {
        try {
            // Ambil nilai stok dan keluar
            int stok = jTextField6.getText().isEmpty() ? 0 : Integer.parseInt(jTextField6.getText());
            int keluar = jTextField15.getText().isEmpty() ? 0 : Integer.parseInt(jTextField15.getText());
        
            // Hitung total stok
            int totalStok = stok - keluar;
        
            // Update field total stok
            jTextField14.setText(String.valueOf(totalStok));
        
            // Hitung total harga jika harga tersedia
            if (!jTextField7.getText().isEmpty()) {
                double harga = Double.parseDouble(jTextField7.getText());
                double totalHarga = harga * keluar;
                jTextField16.setText(String.valueOf(totalHarga));
            }
        } catch (NumberFormatException e) {
            // Tangani jika input bukan angka
            jTextField14.setText("Error");
            jTextField16.setText("Error");
        } 
    }else {
        jTextField14.setText("");
        jTextField16.setText("");
    }
    }
    
    private void setupKeluarFieldListener() {
    jTextField15.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            calculateTotalStok();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            calculateTotalStok();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            calculateTotalStok();
        }
    });
}
    
    private void exportToExcel(JTable table, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < table.getColumnCount(); i++) {
                bw.write(table.getColumnName(i) + "\t");
            }
            bw.newLine();

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Object value = table.getValueAt(i, j);
                    bw.write((value == null ? "" : value.toString()) + "\t");
                }
                bw.newLine();
            }

            bw.close();
            fw.close();

            JOptionPane.showMessageDialog(null, "Data berhasil diekspor ke Excel.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ekspor: " + e.getMessage());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1275, 537));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("DATA BARANG KELUAR");

        jSeparator2.setForeground(new java.awt.Color(51, 51, 51));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Barang Keluar"));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel2.setText("Kode Transaksi");

        jLabel3.setText("Tanggal Keluar");

        jLabel4.setText("Kode Barang");

        jLabel5.setText("Nama Barang");

        jLabel6.setText("Kategori");

        jLabel7.setText("Satuan");

        jLabel8.setText("Harga");

        jTextField1.setEditable(false);

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });

        jTextField3.setEditable(false);

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jLabel9.setText("Stok Barang");

        jTextField6.setEditable(false);
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jTextField7.setEditable(false);

        jLabel13.setText("Keluar");

        jLabel14.setText("Total Stok");

        jTextField14.setEditable(false);

        jLabel15.setText("Total Harga");

        jTextField16.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField7)
                            .addComponent(jTextField5)
                            .addComponent(jTextField4)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addGap(111, 111, 111))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)))
        );

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Total Barang");

        jTextField9.setEditable(false);

        jTextField10.setEditable(false);

        jTextField11.setEditable(false);

        jLabel11.setText("Cari Barang");

        jButton2.setBackground(new java.awt.Color(0, 51, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel12.setText("Tanggal Awal : Tgl/BlnThn");

        jLabel16.setText("Tanggal Akhir : Tgl/Bln/Thn");

        jButton3.setBackground(new java.awt.Color(0, 51, 255));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 255));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Edit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Delete");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 51, 255));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Add Data");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("New");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 51, 51));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Dashboard");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2))
                                    .addComponent(jLabel11)))
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3))))))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String kode_transaksi = jTextField1.getText().trim();
        String tanggal = jTextField2.getText().trim();
        String kode_barang = (String) jComboBox1.getSelectedItem();
        String nama_barang = jTextField3.getText().trim();
        String kategori = jTextField4.getText().trim();
        String satuan = jTextField5.getText().trim();
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        int stok = Integer.parseInt(jTextField6.getText().trim());
        int total_keluar = Integer.parseInt(jTextField15.getText().trim());
        int total_stok = Integer.parseInt(jTextField14.getText().trim());
        Double total_harga = Double.parseDouble(jTextField16.getText().trim());
        
        if (kode_transaksi.isEmpty() || tanggal.isEmpty() || kode_barang.isEmpty() || nama_barang.isEmpty() || kategori.isEmpty() || satuan.isEmpty() || harga == 0 || stok == 0 || total_keluar == 0){
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }
        
        if (!tanggal.matches("\\d{2}/\\d{2}/\\d{4}")) {
        JOptionPane.showMessageDialog(this, "Format tanggal harus DD/MM/YYYY");
        return;
        }
        
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO barang_keluar (kode_transaksi, tanggal, kode_barang, nama_barang, kategori, stok, satuan, harga, total_keluar, total_harga, total_stok) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kode_transaksi);
            stmt.setString(2, tanggal);
            stmt.setString(3, kode_barang);
            stmt.setString(4, nama_barang);
            stmt.setString(5, kategori);
            stmt.setInt(6, stok);
            stmt.setString(7, satuan);
            stmt.setDouble(8,harga );
            stmt.setInt(9, total_keluar);
            stmt.setDouble(10, total_harga);
            stmt.setInt(11,  total_stok);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data Barang Keluar berhasil disimpan.");
            bersihkan();
            tampilkanDataKeluar();
            updateStokBarangKeluar(kode_barang, total_keluar);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        loadTotalData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
        "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String kode_transaksi = jTable1.getValueAt(selectedRow, 0).toString(); // ambil ID di kolom 0
            String kode_barang = jTable1.getValueAt(selectedRow, 2).toString();
int         total_keluar = Integer.parseInt(jTable1.getValueAt(selectedRow, 8).toString());
            
            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM barang_keluar WHERE kode_transaksi = ?")) {
                stmt.setString(1, kode_transaksi);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                tampilkanDataKeluar(); // refresh tabel
                updateStokBarangKeluar(kode_barang, - total_keluar);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        loadTotalData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit.");
            return;
        }

        String kode_transaksi = jTextField1.getText().trim();
        String tanggal = jTextField2.getText().trim();
        String kode_barang = (String) jComboBox1.getSelectedItem();
        String nama_barang = jTextField3.getText().trim();
        String kategori = jTextField4.getText().trim();
        String satuan = jTextField5.getText().trim();
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        int stok = Integer.parseInt(jTextField6.getText().trim());
        int total_keluar = Integer.parseInt(jTextField15.getText().trim());
        int total_stok = Integer.parseInt(jTextField14.getText().trim());
        Double total_harga = Double.parseDouble(jTextField16.getText().trim());
    
        // Set data ke form (sesuaikan nama field dengan form Anda)
        jTextField1.setText(kode_transaksi);
        jTextField2.setText(tanggal);
        jComboBox1.setSelectedItem(kode_barang);
        jTextField3.setText(nama_barang);
        jTextField4.setText(kategori);
        jTextField5.setText(satuan);
        jTextField7.setText(String.valueOf(harga));
        jTextField6.setText(String.valueOf(stok));
        jTextField14.setText(String.valueOf(total_stok));
        jTextField15.setText(String.valueOf(total_keluar));
        jTextField16.setText(String.valueOf(total_harga));
        
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "UPDATE barang_keluar SET tanggal=?, kode_barang=?, nama_barang=?, kategori=?, satuan=?, harga=?, stok=?, total_keluar=?, total_harga=?, total_stok=? WHERE kode_transaksi=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, tanggal);
            stmt.setString(2, kode_barang);
            stmt.setString(3, nama_barang);
            stmt.setString(4, kategori);
            stmt.setString(5, satuan);
            stmt.setDouble(6, harga);
            stmt.setInt(7, stok);
            stmt.setInt(8, total_keluar);
            stmt.setDouble(9, total_harga);
            stmt.setInt(10, total_stok);
            stmt.setString(11, kode_transaksi);
            stmt.executeUpdate();
                   
                   
            bersihkan();
            tampilkanDataKeluar();
            updateStokBarangKeluar(kode_barang, total_keluar);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilkanDataKeluar();
        bersihkan();
        jTextField12.setText("");
        jTextField13.setText("");
        jTextField17.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String keyword = jTextField12.getText().trim();

        try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT * FROM barang_keluar WHERE kode_transaksi LIKE ? OR tanggal LIKE ? OR kode_barang LIKE ? OR nama_barang LIKE ? OR kategori LIKE ? OR stok LIKE ? OR satuan LIKE ? OR harga LIKE ? OR total_keluar LIKE ? OR total_stok LIKE ? OR total_harga LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ps.setString(4, "%" + keyword + "%");
        ps.setString(5, "%" + keyword + "%");
        ps.setString(6, "%" + keyword + "%");
        ps.setString(7, "%" + keyword + "%");
        ps.setString(8, "%" + keyword + "%");
        ps.setString(9, "%" + keyword + "%");
        ps.setString(10, "%" + keyword + "%");
        ps.setString(11, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Satuan");
        model.addColumn("Harga");
        model.addColumn("Total Barang Keluar");
        model.addColumn("Total Harga");
        model.addColumn("Total Stok");
        
        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getString("kode_transaksi"),
                rs.getString("tanggal"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("kategori"),
                rs.getInt("stok"),
                rs.getString("satuan"),
                rs.getDouble("harga"),
                rs.getInt("total_keluar"),
                rs.getDouble("total_harga"),
                rs.getInt("total_stok"),
                });
        }

        jTable1.setModel(model);
        jTable1.clearSelection();
        bersihkan();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Generate kode transaksi baru
        String newKode = generateKodeTransaksi();
        bersihkan();
        jTextField1.setText(newKode);
        
    
        // Set fokus ke field tanggal
        jTextField2.requestFocus();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        File fileToSave = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan sebagai");
        chooser.setSelectedFile(new File("data_barang_keluar.xls"));

        int userSelection = chooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = chooser.getSelectedFile();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                java.util.Date tglAwal = sdf.parse(jTextField13.getText());
                java.util.Date tglAkhir = sdf.parse(jTextField17.getText());

                // Filter dan export...
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                DefaultTableModel filteredModel = new DefaultTableModel();

                for (int i = 0; i < model.getColumnCount(); i++) {
                    filteredModel.addColumn(model.getColumnName(i));
                }

                for (int i = 0; i < model.getRowCount(); i++) {
                    String tglStr = model.getValueAt(i, 1).toString();
                    java.util.Date tglRow = sdf.parse(tglStr);

                    if ((tglRow.equals(tglAwal) || tglRow.after(tglAwal))
                            && (tglRow.equals(tglAkhir) || tglRow.before(tglAkhir))) {

                        Vector row = new Vector();
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            row.add(model.getValueAt(i, j));
                        }
                        filteredModel.addRow(row);
                    }
                }

                JTable tableFiltered = new JTable(filteredModel);
                exportToExcel(tableFiltered, fileToSave); // sekarang fileToSave dikenal

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah!");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 1){
            int row = jTable1.getSelectedRow();
            int col = 0;
    
            if (row >= 0) {
            Object kode_transaksi = jTable1.getValueAt(row, 0);
            Object nama_barang = jTable1.getValueAt(row, 3);
            Object total_keluar = jTable1.getValueAt(row, 8);
            jTextField9.setText(kode_transaksi.toString());
            jTextField10.setText(nama_barang.toString());
            jTextField11.setText(total_keluar.toString());
            }
        }
        
        if (evt.getClickCount() == 2){
            int row =jTable1.getSelectedRow();
            
            
            String kode_transaksi = jTable1.getValueAt(row, 0).toString();
            String tanggal = jTable1.getValueAt(row, 1).toString();
            String kode_barang = jTable1.getValueAt(row, 2).toString();
            String nama_barang = jTable1.getValueAt(row, 3).toString();
            String kategori = jTable1.getValueAt(row, 4).toString();
            String stok = jTable1.getValueAt(row, 5).toString();
            String satuan = jTable1.getValueAt(row, 6).toString();
            String harga = jTable1.getValueAt(row, 7).toString();
            String total_keluar = jTable1.getValueAt(row, 8).toString();
            String total_harga = jTable1.getValueAt(row, 9).toString();
            String total_stok = jTable1.getValueAt(row, 10).toString();
            
            // Set data ke form (sesuaikan nama field dengan form Anda)
            jTextField1.setText(kode_transaksi);
            jTextField2.setText(tanggal);
            jComboBox1.setSelectedItem(kode_barang);
            jTextField3.setText(nama_barang);
            jTextField4.setText(kategori);
            jTextField5.setText(satuan);
            jTextField7.setText(harga);
            jTextField6.setText(stok);
            jTextField14.setText(total_stok);
            jTextField15.setText(total_keluar);
            jTextField16.setText(total_harga);
            }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        new Dashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Barang_Keluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
